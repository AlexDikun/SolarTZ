package su.solyara.Congratulator.DTO;

import su.solyara.Congratulator.domain.positions.Position;

import lombok.Data;

@Data
public class PersonDTO {
    private String firstName;
    private String lastName;
    private Position position;
}
