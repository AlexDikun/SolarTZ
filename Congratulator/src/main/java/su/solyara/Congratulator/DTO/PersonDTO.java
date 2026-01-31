package su.solyara.Congratulator.DTO;

import su.solyara.Congratulator.domain.PersonEntity;
import su.solyara.Congratulator.domain.positions.Position;

import lombok.Data;

@Data
public class PersonDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Position position;

    public static PersonDTO fromEntity(PersonEntity personEntity) {
        PersonDTO dto = new PersonDTO();
        dto.setEmail(personEntity.getEmail());
        dto.setFirstName(personEntity.getFirstName());
        dto.setLastName(personEntity.getLastName());
        dto.setPosition(personEntity.getPosition());
        return dto;
    }
}
