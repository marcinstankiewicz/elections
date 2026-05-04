package service.elections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.model.Election;

public interface ElectionRepository extends JpaRepository<Election, Long> {}
