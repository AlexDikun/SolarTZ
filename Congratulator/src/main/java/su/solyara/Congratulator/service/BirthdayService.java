package su.solyara.Congratulator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import su.solyara.Congratulator.DTO.BirthdayEventDTO;
import su.solyara.Congratulator.domain.PersonEntity;
import su.solyara.Congratulator.repos.PersonRepo;

@Service
@Transactional
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
    
    /**
     * @return список объектов с датой и списком именинников в текущем году, 
     * отсортированных по месяцу и дню
     */
    public List<BirthdayEventDTO> getAllBirthdays() {
        List<PersonEntity> allPersons = personRepo.findAll();
        List<BirthdayEventDTO> birthdayEvents = new ArrayList<>();

        int currentYear = LocalDate.now().getYear();

        for (PersonEntity person : allPersons) {
            LocalDate thisYearBirthday = LocalDate.of(
                currentYear,
                person.getBirthDate().getMonth(),
                person.getBirthDate().getDayOfMonth()
            );

            BirthdayEventDTO event = new BirthdayEventDTO(thisYearBirthday, List.of(person));
            birthdayEvents.add(event);
        }

        birthdayEvents.sort(Comparator.comparing(
            event -> LocalDate.of(1900, event.getDate().getMonth(), event.getDate().getDayOfMonth())
        ));

        return birthdayEvents;
    }
}
