package service.elections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
}
