package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device insertDevice(UUID id,String address, String description, double mhec, UUID personId) {
        Device device = new Device(id,address, description, mhec, personId);
        return deviceRepository.save(device);
    }
    public Device updateDevice(UUID id, String address, String description, double mhec, UUID personId) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            device.setAddress(address);
            device.setDescription(description);
            device.setMhec(mhec);
            device.setPersonId(personId);
            return deviceRepository.save(device);
        } else {
            throw new RuntimeException("Device with ID " + id + " not found.");
        }
    }

    // Delete a device by id
    public void deleteDevice(UUID id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Device with ID " + id + " not found.");
        }
    }
}
