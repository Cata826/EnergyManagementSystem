package ro.tuc.ds2020.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.config.RabbitMQConfig;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDTOsend;

//
//@Service
//public class DeviceEventProducer {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    public DeviceEventProducer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void sendDeviceCreationEvent(DeviceDTO deviceDTO) {
//        rabbitTemplate.convertAndSend(RabbitMQConfig.DEVICE_EXCHANGE, RabbitMQConfig.DEVICE_ROUTING_KEY, deviceDTO);
//    }
//    public void sendDeviceUpdateEvent(DeviceDTO deviceDTO) {
//        rabbitTemplate.convertAndSend(RabbitMQConfig.DEVICE_EXCHANGE, RabbitMQConfig.DEVICE_ROUTING_KEY, deviceDTO);
//    }
//
//    public void sendDeviceDeletionEvent(DeviceDTO deviceDTO) {
//        rabbitTemplate.convertAndSend(RabbitMQConfig.DEVICE_EXCHANGE, RabbitMQConfig.DEVICE_ROUTING_KEY, deviceDTO);
//    }
//}
@Service
public class DeviceEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DeviceEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDeviceEvent(DeviceDTOsend deviceDTOsend, String actionType) {
        deviceDTOsend.setActionType(actionType); // Setăm tipul acțiunii
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEVICE_EXCHANGE, RabbitMQConfig.DEVICE_ROUTING_KEY, deviceDTOsend);
    }
}
