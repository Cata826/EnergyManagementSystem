package ro.tuc.ds2020.services;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ConsumerDTO;
import ro.tuc.ds2020.dtos.ConsumerDetailsDTO;
import ro.tuc.ds2020.dtos.builders.ConsumerBuilder;
import ro.tuc.ds2020.entities.Consumer;
import ro.tuc.ds2020.repositories.ConsumerRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);
    private final ConsumerRepository consumerRepository;
    private final RestTemplate restTemplate;
    private static final String DEVICE_SERVICE_URL = "http://devices.localhost/device";
//    private static final String DEVICE_SERVICE_URL = "http://tomcat-db-api2:8081/device";
//    private static final String DEVICE_SERVICE_URL = "http://localhost:8081/device";
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public ConsumerService(ConsumerRepository consumerRepository,RestTemplate restTemplate,SimpMessagingTemplate messagingTemplate) {
        this.consumerRepository = consumerRepository;
        this.restTemplate = restTemplate;
        this.messagingTemplate=messagingTemplate;
    }
    public BigDecimal getMhecFromDeviceService(UUID deviceId) {
        String url = DEVICE_SERVICE_URL + "/" + deviceId + "/mhec";
        Double mhec = restTemplate.getForObject(url, Double.class);

        return mhec != null ? BigDecimal.valueOf(mhec) : null;
    }

    public List<UUID> getDeviceIdsByPersonId(UUID personId) {
//        String deviceServiceUrl = "http://tomcat-db-api2:8081/device/person/" + personId;
//        String deviceServiceUrl = "http://devices.localhost/device/person/" + personId;
        String deviceServiceUrl = "http://localhost:8081/device/person/" + personId;
        List<Map<String, Object>> devices = restTemplate.getForObject(deviceServiceUrl, List.class);
        return devices.stream()
                .map(deviceMap -> UUID.fromString(deviceMap.get("id").toString()))
                .collect(Collectors.toList());
    }
    public UUID getPersonIdFromDeviceService(UUID deviceId)
    {
        String url = DEVICE_SERVICE_URL + "/" + deviceId + "/personId";
        UUID personId =restTemplate.getForObject(url, UUID.class);

        return personId;
    }
    public BigDecimal checkMhecAgainstMeasurement(UUID deviceId) {
        BigDecimal mhec = getMhecFromDeviceService(deviceId);
        UUID personId = getPersonIdFromDeviceService(deviceId);
        if (mhec == null) {
            LOGGER.warn("MHEC for device {} could not be fetched", deviceId);
            return null;
        }
        BigDecimal latestMeasurementValue = consumerRepository.findLatestMeasurementValueByDeviceId(deviceId);
        if (latestMeasurementValue == null) {
            LOGGER.warn("Latest measurement for device {} could not be found", deviceId);
            return null;
        }
        LOGGER.info("Device {} - MHEC: {}, Measurement Value: {}", deviceId, mhec, latestMeasurementValue);

        BigDecimal higherValue = mhec.compareTo(latestMeasurementValue) > 0 ? mhec : latestMeasurementValue;

        if (latestMeasurementValue.compareTo(mhec) > 0) {
            sendWebSocketNotification(personId,deviceId, latestMeasurementValue, mhec);
        }

        return higherValue;
    }

    private void sendWebSocketNotification(UUID personId, UUID deviceId, BigDecimal latestMeasurementValue, BigDecimal mhec) {
        JSONObject notification = new JSONObject();
        notification.put("message", String.format("Device %s: Latest measurement value (%s) is higher than MHEC value (%s)",
                deviceId, latestMeasurementValue, mhec));
        String destination = "/topic/notifications/" + personId.toString();
        messagingTemplate.convertAndSend(destination, notification.toJSONString());
    }

    public void checkAndUpdateConsumer(UUID deviceId, BigDecimal measurementValue) {
        Optional<Consumer> consumerOptional = consumerRepository.findByDeviceId(deviceId);

        if (consumerOptional.isPresent()) {
            Consumer existingConsumer = consumerOptional.get();
            existingConsumer.setMeasurementValue(
                    existingConsumer.getMeasurementValue().add(measurementValue)
            );
            consumerRepository.save(existingConsumer);
            LOGGER.info("Updated Consumer with deviceId {} by adding measurement value {}",
                    deviceId, measurementValue);
        } else {
            // Device ID does not exist - create a new Consumer record
            Consumer newConsumer = new Consumer(deviceId, measurementValue);
            consumerRepository.save(newConsumer);
            LOGGER.info("Created new Consumer with deviceId {} and measurement value {}",
                    deviceId, measurementValue);
        }
    }
    public List<ConsumerDTO> findAllConsumers() {
        List<Consumer> consumerList = consumerRepository.findAll();
        return consumerList.stream()
                .map(ConsumerBuilder::toConsumerDTO)
                .collect(Collectors.toList());
    }
    public ConsumerDetailsDTO findConsumerById(UUID id) {
        Optional<Consumer> consumerOptional = consumerRepository.findById(id);
        if (!consumerOptional.isPresent()) {
            LOGGER.error("Consumer with id {} was not found in db", id);
            throw new ResourceNotFoundException(Consumer.class.getSimpleName() + " with id: " + id);
        }
        return ConsumerBuilder.toConsumerDetailsDTO(consumerOptional.get());
    }
    public UUID insertConsumer(ConsumerDetailsDTO consumerDTO) {
        Consumer consumer = ConsumerBuilder.toEntity(consumerDTO);
        consumer = consumerRepository.save(consumer);
        LOGGER.debug("Consumer with id {} was inserted in db", consumer.getId());
        return consumer.getId();
    }
    public ConsumerDetailsDTO updateConsumer(UUID id, ConsumerDetailsDTO consumerDTO) {
        Optional<Consumer> consumerOptional = consumerRepository.findById(id);
        if (!consumerOptional.isPresent()) {
            LOGGER.error("Consumer with id {} was not found in db", id);
            throw new ResourceNotFoundException(Consumer.class.getSimpleName() + " with id: " + id);
        }

        Consumer consumerToUpdate = consumerOptional.get();
        consumerToUpdate.setDeviceId(consumerDTO.getDeviceId());
        consumerToUpdate.setMeasurementValue(consumerDTO.getMeasurementValue());

        consumerRepository.save(consumerToUpdate);
        LOGGER.debug("Consumer with id {} was updated in db", consumerToUpdate.getId());
        return ConsumerBuilder.toConsumerDetailsDTO(consumerToUpdate);
    }

    public void deleteConsumer(UUID id) {
        Optional<Consumer> consumerOptional = consumerRepository.findById(id);
        if (!consumerOptional.isPresent()) {
            LOGGER.error("Consumer with id {} was not found in db", id);
            throw new ResourceNotFoundException(Consumer.class.getSimpleName() + " with id: " + id);
        }

        consumerRepository.delete(consumerOptional.get());
        LOGGER.debug("Consumer with id {} was deleted from db", id);
    }
}
