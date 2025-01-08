package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{id}/mhec")
    public double getDeviceMhec(@PathVariable UUID id) {
        return deviceService.getMhecByDeviceId(id);
    }
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = deviceService.findDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDeviceById(@PathVariable UUID id) {
        Device device = deviceService.getDeviceById(id);

        DeviceDetailsDTO deviceDetailsDTO = DeviceBuilder.toDeviceDetailsDTO(device);
        return ResponseEntity.ok(deviceDetailsDTO);
    }

    @DeleteMapping("/person/{personId}")
    public ResponseEntity<Void> deleteDevicesByPersonId(@PathVariable UUID personId) {
        deviceService.deleteDevicesByPersonId(personId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<DeviceDetailsDTO>> getDevicesByPersonId(@PathVariable UUID personId) {
        List<Device> devices = deviceService.getDevicesByPersonId(personId);
        List<DeviceDetailsDTO> deviceDetailsDTOs = devices.stream()
                .map(DeviceBuilder::toDeviceDetailsDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deviceDetailsDTOs);
    }
    @GetMapping("/{deviceId}/personId")
    public ResponseEntity<UUID> getPersonIdByDeviceId(@PathVariable UUID deviceId) {
        UUID personId = deviceService.getPersonIdByDeviceId(deviceId);
        return ResponseEntity.ok(personId);
    }
    @PostMapping
    public ResponseEntity<UUID> createDevice(@RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceId = deviceService.createDevice(deviceDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(@PathVariable UUID id, @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        deviceService.updateDevice(id, deviceDetailsDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        deviceService.deleteDevice(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/person-reference/{personReferenceId}")
    public ResponseEntity<List<Device>> getDevicesByPersonReferenceId(@PathVariable UUID personReferenceId) {
        List<Device> devices = deviceService.getAllDevicesByPersonReferenceId(personReferenceId);
        return ResponseEntity.ok(devices);
    }
    @DeleteMapping("/person-reference/{personReferenceId}")
    public ResponseEntity<Void> deleteDevicesByPersonReferenceId(@PathVariable UUID personReferenceId) {
        deviceService.deleteDevicesByPersonReferenceId(personReferenceId);
        return ResponseEntity.noContent().build();
    }
}