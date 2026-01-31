package su.solyara.Congratulator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import su.solyara.Congratulator.DTO.BirthdayEventDTO;
import su.solyara.Congratulator.domain.PersonEntity;
import su.solyara.Congratulator.repos.PersonRepo;

@Service
public class BirthdayService {

    @Autowired
    private PersonRepo personRepo;

    /**
     * Возвращает список дней рождения на заданный период.
     * @param startDate начальная дата 
     * @param days количество дней 
     * @return список объектов с датой и списком именинников
     */
    public List<BirthdayEventDTO> getUpcomingBirthdays (LocalDate startDate, int days) {
        LocalDate endDate = startDate.plusDays(days - 1);
        List<PersonEntity> allPersons = personRepo.findAll();
        List<BirthdayEventDTO> upcomingBirthdays = new ArrayList<>();

        for (PersonEntity person : allPersons) {
            LocalDate thisYearBirthday = LocalDate.of(
                startDate.getYear(),
                person.getBirthDate().getMonth(),
                person.getBirthDate().getDayOfMonth()
            );

            boolean isInRange = (
                (thisYearBirthday.isEqual(startDate) || thisYearBirthday.isAfter(startDate))
                && thisYearBirthday.isBefore(endDate.plusDays(1))
            );

            if (isInRange) {
                BirthdayEventDTO event = new BirthdayEventDTO(thisYearBirthday, List.of(person));
                upcomingBirthdays.add(event);
            }
        }

        return upcomingBirthdays;
    }
    
}
