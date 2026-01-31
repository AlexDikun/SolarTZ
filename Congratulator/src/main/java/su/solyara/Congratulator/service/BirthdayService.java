package su.solyara.Congratulator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import su.solyara.Congratulator.DTO.BirthdayEventDTO;
import su.solyara.Congratulator.DTO.PersonDTO;
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

        Map<LocalDate, List<PersonDTO>> eventsMap = new HashMap<>();

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
                PersonDTO personDTO = PersonDTO.fromEntity(person);  
                eventsMap.computeIfAbsent(thisYearBirthday, k -> new ArrayList<>())
                        .add(personDTO);
            }
        }

        List<BirthdayEventDTO> upcomingBirthdays = new ArrayList<>();
        for (Map.Entry<LocalDate, List<PersonDTO>> entry : eventsMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<PersonDTO> persons = entry.getValue();
            BirthdayEventDTO event = new BirthdayEventDTO(date, persons);
            upcomingBirthdays.add(event);
            upcomingBirthdays.sort(Comparator.comparing(BirthdayEventDTO::getDate));
        }

        return upcomingBirthdays;
    }
    
    /**
     * @return список объектов с датой и списком именинников в текущем году, 
     * отсортированных по месяцу и дню
     */
    public List<BirthdayEventDTO> getAllBirthdays() {
        List<PersonEntity> allPersons = personRepo.findAll();
        Map<LocalDate, List<PersonDTO>> eventsMap = new HashMap<>();  
        int currentYear = LocalDate.now().getYear();

        for (PersonEntity person : allPersons) {
            LocalDate thisYearBirthday = LocalDate.of(
                currentYear,
                person.getBirthDate().getMonth(),
                person.getBirthDate().getDayOfMonth()
            );

            PersonDTO personDTO = PersonDTO.fromEntity(person);

            eventsMap.computeIfAbsent(thisYearBirthday, k -> new ArrayList<>())
                    .add(personDTO);
        }

        List<BirthdayEventDTO> birthdayEvents = new ArrayList<>();
        for (Map.Entry<LocalDate, List<PersonDTO>> entry : eventsMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<PersonDTO> persons = entry.getValue();
            
            BirthdayEventDTO event = new BirthdayEventDTO(date, persons);
            birthdayEvents.add(event);
        }

        birthdayEvents.sort(Comparator.comparing(BirthdayEventDTO::getDate));

        return birthdayEvents;
    }

}
