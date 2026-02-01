package su.solyara.Congratulator.DTO;

import su.solyara.Congratulator.domain.PersonEntity;
import su.solyara.Congratulator.domain.positions.Position;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id; 
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Position position;
    private String photoFileName;

    public static PersonDTO fromEntity(PersonEntity personEntity) {
        PersonDTO dto = new PersonDTO();
        dto.setId(personEntity.getId());
        dto.setEmail(personEntity.getEmail());
        dto.setFirstName(personEntity.getFirstName());
        dto.setLastName(personEntity.getLastName());
        dto.setBirthDate(personEntity.getBirthDate());
        dto.setPosition(personEntity.getPosition());
        dto.setPhotoFileName(personEntity.getPhotoFileName());
        return dto;
    }

    public PersonDTO() {}

    public PersonDTO(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        Position position
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.position = position;
    }
}
