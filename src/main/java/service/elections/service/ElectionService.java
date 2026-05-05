package service.elections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.elections.controller.dto.ElectionResponseDTO;
import service.elections.controller.dto.OptionResponseDTO;
import service.elections.persistence.model.Election;
import service.elections.persistence.model.Option;
import service.elections.persistence.repository.ElectionRepository;
import service.elections.persistence.repository.OptionRepository;

@RequiredArgsConstructor
@Service
public class ElectionService {
    private final ElectionRepository repo;
    private final OptionRepository optionRepo;

    public ElectionResponseDTO createElection(String name) {
        Election election = new Election();
        election.setName(name);
        return new ElectionResponseDTO(repo.save(election).getId());
    }

    public OptionResponseDTO addOption(Long id, String name) {
        Election election = repo.findById(id).orElseThrow();
        Option option = new Option();
        option.setName(name);
        option.setElection(election);
        return new OptionResponseDTO(optionRepo.save(option).getId());
    }
}
