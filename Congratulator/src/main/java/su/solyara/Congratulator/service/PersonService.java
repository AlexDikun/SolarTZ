package su.solyara.Congratulator.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import su.solyara.Congratulator.DTO.PersonDTO;
import su.solyara.Congratulator.domain.PersonEntity;
import su.solyara.Congratulator.exceptions.ConflictException;
import su.solyara.Congratulator.repos.PersonRepo;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PhotoService photoService;
    
    public PersonDTO createPerson(PersonDTO personDTO, MultipartFile file) throws IOException {
        Optional<PersonEntity> optPerson = personRepo.findByEmail(personDTO.getEmail());
        PersonEntity personEntity;

         if (optPerson.isPresent()) {
            throw new ConflictException(
                "Email '" + personDTO.getEmail() + "' уже зарегистрирован. Попробуйте войти или восстановить пароль."
            );
        } 

        personEntity = new PersonEntity();
        personEntity.setEmail(personDTO.getEmail());
        personEntity.setFirstName(personDTO.getFirstName());
        personEntity.setLastName(personDTO.getLastName());
        personEntity.setBirthDate(personDTO.getBirthDate());
        personEntity.setPosition(personDTO.getPosition());
        
        if (file != null && !file.isEmpty()) {
            String photoFileName = photoService.savePhoto(personEntity.getId(), file);
            personEntity.setPhotoFileName(photoFileName);
            personRepo.save(personEntity);
        }

        personEntity = personRepo.save(personEntity); 

        return PersonDTO.fromEntity(personEntity);
    } 

    public PersonDTO updatePerson(Long id, PersonDTO personDTO, MultipartFile file) throws IOException {
        Optional<PersonEntity> optPerson = personRepo.findByEmail(personDTO.getEmail());

        if (optPerson.isPresent()) {
            throw new EntityNotFoundException("Сотрудник с id=" + id + " не найден");
        }

        PersonEntity personEntity = optPerson.get();

        personEntity.setEmail(personDTO.getEmail());
        personEntity.setFirstName(personDTO.getFirstName());
        personEntity.setLastName(personDTO.getLastName());
        personEntity.setBirthDate(personDTO.getBirthDate());
        personEntity.setPosition(personDTO.getPosition());

        if (file != null && !file.isEmpty()) {
            String photoFileName = photoService.savePhoto(id, file);
            personEntity.setPhotoFileName(photoFileName);
        }

        PersonEntity updated = personRepo.save(personEntity);
        return PersonDTO.fromEntity(updated);
    }

    public void deletePerson(Long id) {
        if (!personRepo.existsById(id)) {
            throw new EntityNotFoundException("Сотрудник с id=" + id + " не найден");
        }
        personRepo.deleteById(id);
    }

    public PersonDTO getPersonById(Long id) {
        PersonEntity personEntity = personRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Сотрудник с id=" + id + " не найден"));
        return PersonDTO.fromEntity(personEntity);
    }

}
