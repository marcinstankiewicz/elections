package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.controller.dto.CreateElectionResponseDTO;
import service.elections.controller.dto.AddOptionResponseDTO;
import service.elections.controller.dto.GetElectionsResponseDTO;
import service.elections.persistence.model.Election;
import service.elections.persistence.model.Option;
import service.elections.persistence.repository.ElectionRepository;
import service.elections.persistence.repository.OptionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ElectionService {
    private final ElectionRepository repo;
    private final OptionRepository optionRepo;

    public List<GetElectionsResponseDTO> getAll() {
        return List.of(repo.findAll()
                .stream()
                .map(election -> new GetElectionsResponseDTO(election.getName()))
                .toArray(GetElectionsResponseDTO[]::new));
    }

    public CreateElectionResponseDTO createElection(String name) {
        Election election = new Election();
        election.setName(name);
        return new CreateElectionResponseDTO(repo.save(election).getId());
    }

    public AddOptionResponseDTO addOption(Long id, String name) {
        Election election = repo.findById(id).orElseThrow();
        Option option = new Option();
        option.setName(name);
        option.setElection(election);
        return new AddOptionResponseDTO(optionRepo.save(option).getId());
    }
}
