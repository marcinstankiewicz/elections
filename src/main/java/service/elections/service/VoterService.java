package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.controller.dto.AddVoterResponseDTO;
import service.elections.controller.dto.GetVotersResponseDTO;
import service.elections.persistence.model.Voter;
import service.elections.persistence.repository.VoterRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoterService {
    private final VoterRepository repo;

    public List<GetVotersResponseDTO> getAll() {
        return List.of(repo.findAll()
                .stream()
                .map(voter -> new GetVotersResponseDTO(voter.getName()))
                .toArray(GetVotersResponseDTO[]::new));
    }

    public AddVoterResponseDTO addVoter(String name) {
        Voter voter = new Voter();
        voter.setName(name);
        return new AddVoterResponseDTO(repo.save(voter).getId());
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
