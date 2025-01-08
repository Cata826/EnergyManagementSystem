package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.ConsumerDTO;
import ro.tuc.ds2020.dtos.ConsumerDetailsDTO;
import ro.tuc.ds2020.entities.Consumer;

public class ConsumerBuilder {

    private ConsumerBuilder() {
    }

    public static ConsumerDTO toConsumerDTO(Consumer consumer) {
        return new ConsumerDTO(
                consumer.getId(),
                consumer.getDeviceId(),
                consumer.getMeasurementValue()
        );
    }

    public static ConsumerDetailsDTO toConsumerDetailsDTO(Consumer consumer) {
        return new ConsumerDetailsDTO(
                consumer.getId(),
                consumer.getDeviceId(),
                consumer.getMeasurementValue()
        );
    }

    public static Consumer toEntity(ConsumerDetailsDTO consumerDetailsDTO) {
        return new Consumer(
                consumerDetailsDTO.getDeviceId(),
                consumerDetailsDTO.getMeasurementValue()
        );
    }
}
