package service.elections.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.elections.controller.dto.CreateElectionResponseDTO;
import service.elections.controller.dto.AddOptionResponseDTO;
import service.elections.controller.dto.GetElectionsResponseDTO;
import service.elections.service.ElectionService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/elections")
public class ElectionController {
    private final ElectionService electionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GetElectionsResponseDTO> getAll() {
        return electionService.getAll();
    }

    @PostMapping
    public CreateElectionResponseDTO createElection(@RequestParam @NotBlank String name) {
        return electionService.createElection(name);
    }

    @PostMapping("/{id}/options")
    public AddOptionResponseDTO addOption(@PathVariable Long id, @RequestParam @NotBlank String name) {
        return electionService.addOption(id, name);
    }
}
