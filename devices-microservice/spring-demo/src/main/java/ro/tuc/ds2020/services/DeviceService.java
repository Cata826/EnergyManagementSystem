package ro.tuc.ds2020.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDTOsend;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.PersonReferenceRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final PersonReferenceRepository personReferenceRepository;
    private final DeviceEventProducer deviceEventProducer;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository,
                         PersonReferenceRepository personReferenceRepository,
                         DeviceEventProducer deviceEventProducer) {
        this.deviceRepository = deviceRepository;
        this.personReferenceRepository = personReferenceRepository;
        this.deviceEventProducer = deviceEventProducer;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public Device getDeviceById(UUID id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
    }

//    public void updateDevice(DeviceDTO deviceDTO) {
//        // Logic to update device
//        deviceEventProducer.sendDeviceUpdateEvent(deviceDTO);  // Send update event
//    }
//
//    public void deleteDevice(DeviceDTO deviceDTO) {
//        deviceEventProducer.sendDeviceDeletionEvent(deviceDTO);  // Send deletion event
//    }

    public List<Device> getDevicesByPersonId(UUID personId) {
        return deviceRepository.findByPersonReference_PersonId(personId);
    }

    public void deleteDevicesByPersonId(UUID personId) {
        List<Device> devices = deviceRepository.findByPersonReference_PersonId(personId);
        deviceRepository.deleteAll(devices);
    }

    @Transactional
    public UUID createDevice(DeviceDetailsDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO, personReferenceRepository);
        device = deviceRepository.save(device);
        LOGGER.info("Device with id {} was created in db", device.getId());

        DeviceDTO deviceMessage = DeviceBuilder.toDeviceDTO(device);

        // deviceEventProducer.sendDeviceCreationEvent(deviceMessage);
        //deviceEventProducer.sendDeviceUpdateEvent(updatedDeviceMessage);
        DeviceDTOsend deviceDTOsend =new DeviceDTOsend();
        deviceDTOsend.setId(deviceMessage.getId());
        deviceDTOsend.setDescription(deviceMessage.getDescription());
        deviceDTOsend.setAddress(deviceMessage.getAddress());
        deviceDTOsend.setMhec(deviceMessage.getMhec());
        deviceDTOsend.setPersonReference(deviceMessage.getPersonReference());
        deviceDTOsend.setActionType("CREATE");
//        deviceRepository.deleteById(id);
//        LOGGER.info("Device with id {} was deleted from db", id);
        deviceEventProducer.sendDeviceEvent(deviceDTOsend, deviceDTOsend.getActionType());

        return device.getId();
    }


    @Transactional
    public void updateDevice(UUID id, DeviceDetailsDTO deviceDetailsDTO) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Device with id {} was not found in db", id);
                    return new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
                });

        device.setDescription(deviceDetailsDTO.getDescription());
        device.setAddress(deviceDetailsDTO.getAddress());
        device.setMhec(deviceDetailsDTO.getMhec());

        deviceRepository.save(device);
        LOGGER.info("Device with id {} was updated in db", device.getId());

        DeviceDTO updatedDeviceMessage = DeviceBuilder.toDeviceDTO(device);
        //deviceEventProducer.sendDeviceUpdateEvent(updatedDeviceMessage);
        DeviceDTOsend deviceDTOsend =new DeviceDTOsend();
        deviceDTOsend.setId(updatedDeviceMessage.getId());
        deviceDTOsend.setDescription(updatedDeviceMessage.getDescription());
        deviceDTOsend.setAddress(updatedDeviceMessage.getAddress());
        deviceDTOsend.setMhec(updatedDeviceMessage.getMhec());
        deviceDTOsend.setPersonReference(updatedDeviceMessage.getPersonReference());
        deviceDTOsend.setActionType("UPDATE");
//        deviceRepository.deleteById(id);
//        LOGGER.info("Device with id {} was deleted from db", id);
        deviceEventProducer.sendDeviceEvent(deviceDTOsend, deviceDTOsend.getActionType());
    }

    public List<Device> getAllDevicesByPersonReferenceId(UUID personReferenceId) {
        return deviceRepository.findAllByPersonReferenceId(personReferenceId);
    }
    public List<Device> findDevicesByPersonReferenceId(UUID personReferenceId) {
        List<Device> devices = deviceRepository.findAllByPersonReferenceId(personReferenceId);

        if (devices.isEmpty()) {
            LOGGER.warn("No devices found for personReferenceId: {}", personReferenceId);
            throw new ResourceNotFoundException("No devices found for personReferenceId: " + personReferenceId);
        }

        LOGGER.info("Found {} devices for personReferenceId: {}", devices.size(), personReferenceId);
        return devices;
    }
    @Transactional
    public void deleteDevicesByPersonReferenceId(UUID personReferenceId) {
        List<Device> devices = deviceRepository.findAllByPersonReferenceId(personReferenceId);

        if (devices.isEmpty()) {
            LOGGER.warn("No devices found for personReferenceId: {}", personReferenceId);
            throw new ResourceNotFoundException("No devices found for personReferenceId: " + personReferenceId);
        }

        deviceRepository.deleteAll(devices);
        LOGGER.info("Deleted {} devices for personReferenceId: {}", devices.size(), personReferenceId);
    }
    public UUID getPersonIdByDeviceId(UUID deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with ID: " + deviceId));

        return device.getPersonReference().getPersonId();
    }

    public void deleteDevice(UUID id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Device with id {} was not found in db", id);
                    return new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
                });

        DeviceDTO deletedDeviceMessage = DeviceBuilder.toDeviceDTO(device);
        DeviceDTOsend deviceDTOsend =new DeviceDTOsend();
        deviceDTOsend.setId(deletedDeviceMessage.getId());
        deviceDTOsend.setDescription(deletedDeviceMessage.getDescription());
        deviceDTOsend.setAddress(deletedDeviceMessage.getAddress());
        deviceDTOsend.setMhec(deletedDeviceMessage.getMhec());
        deviceDTOsend.setPersonReference(deletedDeviceMessage.getPersonReference());
        deviceDTOsend.setActionType("DELETE");
        deviceRepository.deleteById(id);
        LOGGER.info("Device with id {} was deleted from db", id);
        deviceEventProducer.sendDeviceEvent(deviceDTOsend, deviceDTOsend.getActionType());
        //deviceEventProducer.sendDeviceDeletionEvent(deletedDeviceMessage);
    }
    public double getMhecByDeviceId(UUID deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if (device != null) {
            return device.get().getMhec();
        } else {
            throw new RuntimeException("Device not found with ID: " + deviceId);
        }
    }

}