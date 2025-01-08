package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.BrokerDTO;
import ro.tuc.ds2020.dtos.BrokerDetailsDTO;
import ro.tuc.ds2020.dtos.HourConsumptionDTO;
import ro.tuc.ds2020.services.BrokerService;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping(value = "/broker")
public class BrokerController {

    private final BrokerService brokerService;
    @GetMapping("/energy-consumption/{personId}")
    public List<HourConsumptionDTO> getEnergyConsumptionByHour(
            @PathVariable UUID personId,
            @RequestParam String date) {
        return brokerService.getEnergyConsumptionByHour(personId, date);
    }

    @Autowired
    public BrokerController(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    @GetMapping()
    public ResponseEntity<List<BrokerDTO>> getBrokers() {
        List<BrokerDTO> dtos = brokerService.findAllBrokers();
        for (BrokerDTO dto : dtos) {
            Link brokerLink = linkTo(methodOn(BrokerController.class)
                    .getBroker(dto.getId())).withRel("brokerDetails");
            dto.add(brokerLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertBroker(@Valid @RequestBody BrokerDetailsDTO brokerDTO) {
        UUID brokerID = brokerService.insertBroker(brokerDTO);
        return new ResponseEntity<>(brokerID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BrokerDetailsDTO> getBroker(@PathVariable("id") UUID brokerId) {
        BrokerDetailsDTO dto = brokerService.findBrokerById(brokerId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BrokerDetailsDTO> updateBroker(
            @PathVariable("id") UUID brokerId,
            @Valid @RequestBody BrokerDetailsDTO brokerDTO) {
        BrokerDetailsDTO updatedDTO = brokerService.updateBroker(brokerId, brokerDTO);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBroker(@PathVariable("id") UUID brokerId) {
        brokerService.deleteBroker(brokerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
