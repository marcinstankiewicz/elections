package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.controller.dto.VoterResponseDTO;
import service.elections.persistence.model.Voter;
import service.elections.persistence.repository.VoterRepository;

@RequiredArgsConstructor
@Service
public class VoterService {
    private final VoterRepository repo;

    public VoterResponseDTO addVoter(String name) {
        Voter voter = new Voter();
        voter.setName(name);
        return new VoterResponseDTO(repo.save(voter).getId());
    }

    public void block(Long id) {
        Voter voter = repo.findById(id).orElseThrow();
        voter.setBlocked(true);
        repo.save(voter);
    }

    public void unblock(Long id) {
        Voter voter = repo.findById(id).orElseThrow();
        voter.setBlocked(false);
        repo.save(voter);
    }
}
