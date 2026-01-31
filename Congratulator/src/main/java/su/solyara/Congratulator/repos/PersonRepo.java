package su.solyara.Congratulator.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import su.solyara.Congratulator.domain.PersonEntity;

public interface PersonRepo extends JpaRepository <PersonEntity, Long> {}
