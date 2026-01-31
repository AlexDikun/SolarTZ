package su.solyara.Congratulator.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import su.solyara.Congratulator.domain.BirthdayEntity;

public interface  BirthdayRepo extends JpaRepository <BirthdayEntity, Long> {}
