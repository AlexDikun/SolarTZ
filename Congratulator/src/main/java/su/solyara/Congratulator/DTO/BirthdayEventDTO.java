package su.solyara.Congratulator.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import su.solyara.Congratulator.domain.PersonEntity;

@Data
public class BirthdayEventDTO {
    private LocalDate date; 
    private List<PersonEntity> persons;

    public BirthdayEventDTO(LocalDate date, List<PersonEntity> persons) {
        this.date = date;
        this.persons = persons;
    }
}
