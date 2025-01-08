package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.PersonReferenceDTO;
import ro.tuc.ds2020.entities.PersonReference;

public class PersonReferenceBuilder {

    private PersonReferenceBuilder() {
    }

    public static PersonReferenceDTO toDTO(PersonReference personReference) {
        if (personReference == null) {
            return null;
        }

        PersonReferenceDTO dto = new PersonReferenceDTO();
        dto.setId(personReference.getId());
        dto.setPersonId(personReference.getPersonId());

        return dto;
    }

    public static PersonReference toEntity(PersonReferenceDTO dto) {
        if (dto == null) {
            return null;
        }

        return new PersonReference(dto.getPersonId());
    }
}
