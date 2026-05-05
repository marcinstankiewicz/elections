package service.elections.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.persistence.model.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {}