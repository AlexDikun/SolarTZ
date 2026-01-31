package su.solyara.Congratulator.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;

import su.solyara.Congratulator.DTO.PersonDTO;
import su.solyara.Congratulator.domain.positions.Position;
import su.solyara.Congratulator.repos.PersonRepo;
import su.solyara.Congratulator.service.PersonService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepo personRepo;

    @Override
    @Transactional
    public void onApplicationEvent(final @NonNull ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        List<PersonDTO> demoPersons = List.of(
            new PersonDTO(
                "Анна",
                "Иванова",
                "anna.ivanova@example.com",
                LocalDate.of(1990, 5, 15),
                Position.MANAGER
            ),
            new PersonDTO(
                "Иван",
                "Петров",
                "ivan.petrov@example.com",
                LocalDate.of(1985, 8, 22),
                Position.ENGINEER
            ),
            new PersonDTO(
                "Мария",
                "Сидорова",
                "maria.sidorova@example.com",
                LocalDate.of(1992, 12, 3),
                Position.ADMINISTRATOR
            ),
            new PersonDTO(
                "Джон",
                "Сноу",
                "jhon.show@example.com",
                LocalDate.of(567, 12, 3),
                Position.SUPPORT_STAFF
            ),
            new PersonDTO(
                "Саша",
                "Грей",
                "sasha.grey@example.com",
                LocalDate.of(1996, 5, 8),
                Position.ADMINISTRATOR
            ),
            new PersonDTO(
                "Джозеф Огастус",
                "Зарелли",
                "zarelli@example.com",
                LocalDate.of(1947, 01, 01),
                Position.HSSE_SPECIALIST
            )
        );

        for (PersonDTO dto : demoPersons) {
            if (!personRepo.existsByEmail(dto.getEmail())){
                personService.createPerson(dto);
            }
        }
       
        alreadySetup = true;
    }

}
