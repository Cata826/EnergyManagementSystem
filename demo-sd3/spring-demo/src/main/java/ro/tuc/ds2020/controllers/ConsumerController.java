package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ConsumerDTO;
import ro.tuc.ds2020.dtos.ConsumerDetailsDTO;
import ro.tuc.ds2020.services.ConsumerService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping(value = "/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
    @GetMapping("/checkMhec/{deviceId}")
    public String checkMhec(@PathVariable UUID deviceId) {
        BigDecimal higherValue = consumerService.checkMhecAgainstMeasurement(deviceId);
        if (higherValue != null) {
            return "Higher value for device " + deviceId + ": " + higherValue;
        } else {
            return "Error occurred while checking MHEC for device " + deviceId;
        }
    }

    @GetMapping("/checkMhec/person/{personId}")
    public ResponseEntity<String> checkMhecForPerson(@PathVariable UUID personId) {
        System.out.println("Processing MHEC for person with ID: " + personId);
        List<UUID> deviceIds = consumerService.getDeviceIdsByPersonId(personId);
        System.out.println("Devices for person " + personId + ": " + deviceIds);

        if (deviceIds.isEmpty()) {
            return ResponseEntity.ok("No devices found for person with ID " + personId);
        }
        StringBuilder resultMessages = new StringBuilder();
        for (UUID deviceId : deviceIds) {
            BigDecimal higherValue = consumerService.checkMhecAgainstMeasurement(deviceId);
            if (higherValue != null) {
                resultMessages.append("Higher value for device ").append(deviceId).append(": ").append(higherValue).append("\n");
            } else {
                resultMessages.append("Error occurred while checking MHEC for device ").append(deviceId).append("\n");
            }
        }

        return ResponseEntity.ok("MHEC check completed for person " + personId + ".\n" + resultMessages.toString());
    }

    @GetMapping("/device/{id}/mhec")
    public BigDecimal getDeviceMhec(@PathVariable UUID id) {
        return consumerService.getMhecFromDeviceService(id);
    }
    @GetMapping("/person/{personId}/deviceIds")
    public ResponseEntity<List<UUID>> getDeviceIdsByPersonId(@PathVariable UUID personId) {
        List<UUID> deviceIds = consumerService.getDeviceIdsByPersonId(personId);
        return ResponseEntity.ok(deviceIds);
    }
    @GetMapping("/device/{id}/personId")
    public UUID getDevicePersonId(@PathVariable UUID id) {
        return consumerService.getPersonIdFromDeviceService(id);
    }
    @GetMapping()
    public ResponseEntity<List<ConsumerDTO>> getConsumers() {
        List<ConsumerDTO> dtos = consumerService.findAllConsumers();
        for (ConsumerDTO dto : dtos) {
            Link consumerLink = linkTo(methodOn(ConsumerController.class)
                    .getConsumer(dto.getId())).withRel("consumerDetails");
            dto.add(consumerLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertConsumer(@Valid @RequestBody ConsumerDetailsDTO consumerDTO) {
        UUID consumerID = consumerService.insertConsumer(consumerDTO);
        return new ResponseEntity<>(consumerID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsumerDetailsDTO> getConsumer(@PathVariable("id") UUID consumerId) {
        ConsumerDetailsDTO dto = consumerService.findConsumerById(consumerId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ConsumerDetailsDTO> updateConsumer(
            @PathVariable("id") UUID consumerId,
            @Valid @RequestBody ConsumerDetailsDTO consumerDTO) {
        ConsumerDetailsDTO updatedDTO = consumerService.updateConsumer(consumerId, consumerDTO);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable("id") UUID consumerId) {
        consumerService.deleteConsumer(consumerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
