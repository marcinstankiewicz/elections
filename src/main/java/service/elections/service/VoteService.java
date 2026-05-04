package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.model.Election;
import service.elections.model.Option;
import service.elections.model.Vote;
import service.elections.model.Voter;
import service.elections.repository.ElectionRepository;
import service.elections.repository.OptionRepository;
import service.elections.repository.VoteRepository;
import service.elections.repository.VoterRepository;

@RequiredArgsConstructor
@Service
public class VoteService {
    private final VoteRepository voteRepo;
    private final VoterRepository voterRepo;
    private final ElectionRepository electionRepo;
    private final OptionRepository optionRepo;

    public void vote(Long voterId, Long electionId, Long optionId) {

        Voter voter = voterRepo.findById(voterId).orElseThrow();
        if (voter.isBlocked()) {
            throw new RuntimeException("Voter is blocked");
        }

        if (voteRepo.existsByVoterIdAndElectionId(voterId, electionId)) {
            throw new RuntimeException("Already voted");
        }

        Election election = electionRepo.findById(electionId).orElseThrow();
        Option option = optionRepo.findById(optionId).orElseThrow();

        if (!option.getElection().getId().equals(electionId)) {
            throw new RuntimeException("Option not in this election");
        }

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setOption(option);

        voteRepo.save(vote);
    }
}
