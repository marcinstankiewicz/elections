package service.elections.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.persistence.model.Election;

public interface ElectionRepository extends JpaRepository<Election, Long> {}
