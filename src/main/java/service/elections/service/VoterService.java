package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.model.Voter;
import service.elections.repository.VoterRepository;

@RequiredArgsConstructor
@Service
public class VoterService {
    private final VoterRepository repo;

    public Voter addVoter(String name) {
        Voter voter = new Voter();
        voter.setName(name);
        return repo.save(voter);
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
