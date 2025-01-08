package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.PersonReferenceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.PersonReference;
import ro.tuc.ds2020.repositories.PersonReferenceRepository;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        PersonReference personReference = device.getPersonReference();
        PersonReferenceDTO personReferenceDTO = null;

        if (personReference != null) {
            personReferenceDTO = new PersonReferenceDTO(personReference.getPersonId());
        }

        return new DeviceDTO(device.getId(),
                device.getDescription(),
                device.getAddress(),
                device.getMhec(),
                personReferenceDTO);
    }


    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        PersonReference personReference = device.getPersonReference();
        PersonReferenceDTO personReferenceDTO = null;

        if (personReference != null) {
            personReferenceDTO = new PersonReferenceDTO(personReference.getPersonId());
        }


        return new DeviceDetailsDTO(device.getId(),
                device.getDescription(),
                device.getAddress(),
                device.getMhec(),
                personReferenceDTO.getPersonReference());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO, PersonReferenceRepository personReferenceRepository) {

        PersonReference personReference =
                personReferenceRepository.findByPersonId(deviceDetailsDTO.getPersonReference().getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("PersonReference with personId: " +
                        deviceDetailsDTO.getPersonReference().getPersonId() + " does not exist"));

        return new Device(deviceDetailsDTO.getDescription(),
                deviceDetailsDTO.getAddress(),
                deviceDetailsDTO.getMhec(),
                personReference);
    }


}
