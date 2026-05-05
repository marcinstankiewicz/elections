package service.elections.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.persistence.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
}
