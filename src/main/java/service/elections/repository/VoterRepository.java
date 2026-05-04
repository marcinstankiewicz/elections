package service.elections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.model.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {}