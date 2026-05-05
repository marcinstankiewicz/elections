package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.controller.dto.VoteRequestDTO;
import service.elections.persistence.model.Election;
import service.elections.persistence.model.Option;
import service.elections.persistence.model.Vote;
import service.elections.persistence.model.Voter;
import service.elections.persistence.repository.ElectionRepository;
import service.elections.persistence.repository.OptionRepository;
import service.elections.persistence.repository.VoteRepository;
import service.elections.persistence.repository.VoterRepository;

@RequiredArgsConstructor
@Service
public class VoteService {
    private final VoteRepository voteRepo;
    private final VoterRepository voterRepo;
    private final ElectionRepository electionRepo;
    private final OptionRepository optionRepo;

    public void vote(VoteRequestDTO voteRequestDTO) {
        Voter voter = voterRepo.findById(voteRequestDTO.voterId()).orElseThrow();
        if (voter.isBlocked()) {
            throw new RuntimeException("Voter is blocked");
        }

        if (voteRepo.existsByVoterIdAndElectionId(voteRequestDTO.voterId(), voteRequestDTO.electionId())) {
            throw new RuntimeException("Already voted");
        }

        Election election = electionRepo.findById(voteRequestDTO.electionId()).orElseThrow();
        Option option = optionRepo.findById(voteRequestDTO.optionId()).orElseThrow();

        if (!option.getElection().getId().equals(voteRequestDTO.electionId())) {
            throw new RuntimeException("Option not in this election");
        }

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setOption(option);

        voteRepo.save(vote);
    }
}
