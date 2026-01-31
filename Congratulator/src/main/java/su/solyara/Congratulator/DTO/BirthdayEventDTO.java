package su.solyara.Congratulator.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BirthdayEventDTO {
    private LocalDate date; 
    private List<PersonDTO> persons;

    public BirthdayEventDTO(LocalDate date, List<PersonDTO> persons) {
        this.date = date;
        this.persons = persons;
    }
}
