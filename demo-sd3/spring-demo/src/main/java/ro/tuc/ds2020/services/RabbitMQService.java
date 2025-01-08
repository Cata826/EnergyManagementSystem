
package ro.tuc.ds2020.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.BrokerDetailsDTO;

import java.sql.Timestamp;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMQService {

    private static final String SENSOR_DATA_QUEUE = "sensor_data_queue";
    private static final String DEVICE_QUEUE = "device_queue";
    private BrokerService brokerService;
    private Connection connection;
    private DeviceService deviceService;

    @Autowired
    public RabbitMQService(BrokerService brokerService,DeviceService deviceService) {
        this.deviceService = deviceService;
        this.brokerService = brokerService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void consumeMessagesOnStartup() {
        try {
            consumeSensorDataMessages();
            consumeDeviceMessages();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void consumeSensorDataMessages() throws IOException, TimeoutException {
        setupConsumer(SENSOR_DATA_QUEUE, "sensor_data");
    }

    public void consumeDeviceMessages() throws IOException, TimeoutException {
        setupConsumer(DEVICE_QUEUE, "device");
    }

    private void setupConsumer(String queueName, String queueType) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("spring-demo_rabbitmq_1");
        factory.setHost("rabbitmq");
//        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received message from " + queueName + ": " + message);

            if ("sensor_data".equals(queueType)) {
                handleSensorDataMessage(message);
            }
            else if ("device".equals(queueType)) {
                handleDeviceMessage(message);
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private void handleSensorDataMessage(String message) {
        System.out.println("Processing sensor data message: " + message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(message, Map.class);

            String deviceIdStr = (String) map.get("device_id");
            Long timestampLong = (Long) map.get("timestamp");
            String measurementValueStr = (String) map.get("measurement_value");

            UUID deviceId = UUID.fromString(deviceIdStr);
            Timestamp timestamp = new Timestamp(timestampLong);
            BigDecimal measurementValue = new BigDecimal(measurementValueStr);

            boolean exists = brokerService.checkIfExists(deviceId, timestamp);

            if (!exists) {
                BrokerDetailsDTO brokerDetailsDTO = new BrokerDetailsDTO(timestamp, deviceId, measurementValue);

                brokerService.insertBroker(brokerDetailsDTO);

                System.out.println("Processed sensor data message and inserted into DB: " + brokerDetailsDTO);
            } else {
                System.out.println("Duplicate data detected, skipping insert: " + deviceId + " at " + timestamp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleDeviceMessage(String message) {
        System.out.println("Processing message from device_queue: " + message);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(message, Map.class);

            UUID id = UUID.fromString((String) map.get("id"));
            String description = (String) map.get("description");
            String address = (String) map.get("address");
            BigDecimal mhec = new BigDecimal(map.get("mhec").toString());
            String actionType = (String) map.get("actionType");
            Map<String, Object> personReferenceMap = (Map<String, Object>) map.get("personReference");
            UUID personId = personReferenceMap != null && personReferenceMap.get("personId") != null
                    ? UUID.fromString((String) personReferenceMap.get("personId"))
                    : null;

            System.out.println("Extracted Device Data:");
            System.out.println("ID: " + id);
            System.out.println("Description: " + description);
            System.out.println("Address: " + address);
            System.out.println("MHEC: " + mhec);
            System.out.println("Person ID: " + (personId != null ? personId : "null"));
            System.out.println(actionType);
            double mmhec = Double.parseDouble(mhec.toString());
            if(actionType.equals("CREATE"))
            {
                deviceService.insertDevice(id,address,description,mmhec,personId);
            }
            else if(actionType.equals("UPDATE"))
            {
                deviceService.updateDevice(id,address,description,mmhec,personId);
            }
            else if(actionType.equals("DELETE")) {
                deviceService.deleteDevice(id);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to process message: " + message);
        }
    }
}
