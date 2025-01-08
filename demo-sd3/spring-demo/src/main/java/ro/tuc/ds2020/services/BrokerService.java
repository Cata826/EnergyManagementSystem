package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.BrokerDTO;
import ro.tuc.ds2020.dtos.BrokerDetailsDTO;
import ro.tuc.ds2020.dtos.HourConsumptionDTO;
import ro.tuc.ds2020.dtos.builders.BrokerBuilder;
import ro.tuc.ds2020.entities.Broker;
import ro.tuc.ds2020.repositories.BrokerRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BrokerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerService.class);
    private final BrokerRepository brokerRepository;
    private final ConsumerService consumerService;
    @Autowired
    public BrokerService(BrokerRepository brokerRepository, ConsumerService consumerService) {
        this.brokerRepository = brokerRepository;
        this.consumerService = consumerService;
    }

    public List<BrokerDTO> findAllBrokers() {
        List<Broker> brokerList = brokerRepository.findAll();
        return brokerList.stream()
                .map(BrokerBuilder::toBrokerDTO)
                .collect(Collectors.toList());
    }
    public boolean checkIfExists(UUID deviceId, Timestamp timestamp) {
        return brokerRepository.existsByDeviceIdAndTimestamp(deviceId, timestamp);
    }
    public List<HourConsumptionDTO> getEnergyConsumptionByHour(UUID personId, String selectedDate) {
        Timestamp startOfDay = Timestamp.valueOf(selectedDate + " 00:00:00");
        Timestamp endOfDay = Timestamp.valueOf(selectedDate + " 23:59:59");
        List<UUID> deviceIds = consumerService.getDeviceIdsByPersonId(personId);
        List<Broker> brokers = brokerRepository.findByDeviceIdInAndTimestampBetween(deviceIds, startOfDay, endOfDay);
        Map<Integer, BigDecimal> hourlySum = new TreeMap<>();

        for (Broker broker : brokers) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(broker.getTimestamp());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            hourlySum.put(hour, hourlySum.getOrDefault(hour, BigDecimal.ZERO).add(broker.getMeasurementValue()));
        }

        List<HourConsumptionDTO> hourConsumptionList = hourlySum.entrySet().stream()
                .map(entry -> new HourConsumptionDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return hourConsumptionList;
    }
    public BrokerDetailsDTO findBrokerById(UUID id) {
        Optional<Broker> brokerOptional = brokerRepository.findById(id);
        if (!brokerOptional.isPresent()) {
            LOGGER.error("Broker with id {} was not found in db", id);
            throw new ResourceNotFoundException(Broker.class.getSimpleName() + " with id: " + id);
        }
        return BrokerBuilder.toBrokerDetailsDTO(brokerOptional.get());
    }
    public UUID insertBroker(BrokerDetailsDTO brokerDTO) {
        Broker broker = BrokerBuilder.toEntity(brokerDTO);
        broker = brokerRepository.save(broker);
        LOGGER.debug("Broker with id {} was inserted in db", broker.getId());
        consumerService.checkAndUpdateConsumer(broker.getDeviceId(), broker.getMeasurementValue());

        return broker.getId();
    }

    public BrokerDetailsDTO updateBroker(UUID id, BrokerDetailsDTO brokerDTO) {
        Optional<Broker> brokerOptional = brokerRepository.findById(id);
        if (!brokerOptional.isPresent()) {
            LOGGER.error("Broker with id {} was not found in db", id);
            throw new ResourceNotFoundException(Broker.class.getSimpleName() + " with id: " + id);
        }

        Broker brokerToUpdate = brokerOptional.get();
        brokerToUpdate.setDeviceId(brokerDTO.getDeviceId());
        brokerToUpdate.setTimestamp(brokerDTO.getTimestamp());
        brokerToUpdate.setMeasurementValue(brokerDTO.getMeasurementValue());

        brokerRepository.save(brokerToUpdate);
        LOGGER.debug("Broker with id {} was updated in db", brokerToUpdate.getId());
        return BrokerBuilder.toBrokerDetailsDTO(brokerToUpdate);
    }

    public void deleteBroker(UUID id) {
        Optional<Broker> brokerOptional = brokerRepository.findById(id);
        if (!brokerOptional.isPresent()) {
            LOGGER.error("Broker with id {} was not found in db", id);
            throw new ResourceNotFoundException(Broker.class.getSimpleName() + " with id: " + id);
        }

        brokerRepository.delete(brokerOptional.get());
        LOGGER.debug("Broker with id {} was deleted from db", id);
    }
}
