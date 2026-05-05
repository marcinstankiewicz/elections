package service.elections.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import service.elections.persistence.model.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private VoterRepository voterRepo;

    @Autowired
    private ElectionRepository electionRepo;

    @Autowired
    private OptionRepository optionRepo;

    @Test
    void shouldSaveVote() {
        // given
        Voter voter = new Voter();
        voter.setName("Jan");
        voter.setBlocked(false);
        voter = voterRepo.save(voter);

        Election election = new Election();
        election.setName("Election");
        election = electionRepo.save(election);

        Option option = new Option();
        option.setName("Option A");
        option.setElection(election);
        option = optionRepo.save(option);

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setOption(option);

        // when
        Vote saved = voteRepo.save(vote);

        // then
        assertNotNull(saved.getId());
    }

    @Test
    void shouldReturnTrueWhenVoteExists() {
        // given
        Voter voter = voterRepo.save(new Voter(null, "Jan", false));
        Election election = electionRepo.save(new Election(null, "Election", null));
        Option option = optionRepo.save(new Option(null, "Option", election));

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setOption(option);
        voteRepo.save(vote);

        // when
        boolean exists = voteRepo.existsByVoterIdAndElectionId(
                voter.getId(),
                election.getId()
        );

        // then
        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenVoteDoesNotExist() {
        // given
        Voter voter = voterRepo.save(new Voter());
        Election election = electionRepo.save(new Election());

        // when
        boolean exists = voteRepo.existsByVoterIdAndElectionId(
                voter.getId(),
                election.getId()
        );

        // then
        assertFalse(exists);
    }
}