package ro.tuc.ds2020.dtos.builders;
import ro.tuc.ds2020.dtos.BrokerDTO;
import ro.tuc.ds2020.dtos.BrokerDetailsDTO;
import ro.tuc.ds2020.entities.Broker;

public class BrokerBuilder {

    private BrokerBuilder() {
    }

    public static BrokerDTO toBrokerDTO(Broker broker) {
        return new BrokerDTO(broker.getId(),broker.getTimestamp(), broker.getDeviceId(), broker.getMeasurementValue());
    }

    public static BrokerDetailsDTO toBrokerDetailsDTO(Broker broker) {
        return new BrokerDetailsDTO(broker.getId(),  broker.getTimestamp(),broker.getDeviceId(), broker.getMeasurementValue());
    }

    public static Broker toEntity(BrokerDetailsDTO brokerDetailsDTO) {
        return new Broker(
                brokerDetailsDTO.getDeviceId(),
                brokerDetailsDTO.getTimestamp(),
                brokerDetailsDTO.getMeasurementValue()
        );
    }
}
