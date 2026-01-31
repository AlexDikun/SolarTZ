package su.solyara.Congratulator.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import su.solyara.Congratulator.domain.PersonEntity;

public interface PersonRepo extends JpaRepository <PersonEntity, Long> {
    Optional<PersonEntity> findByEmail(String email);
}
