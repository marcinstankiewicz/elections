package service.elections.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.elections.controller.dto.CreateElectionResponseDTO;
import service.elections.controller.dto.AddOptionResponseDTO;
import service.elections.service.ElectionService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/elections")
public class ElectionController {
    private final ElectionService electionService;

    @PostMapping
    public CreateElectionResponseDTO createElection(@RequestParam @NotBlank String name) {
        return electionService.createElection(name);
    }

    @PostMapping("/{id}/options")
    public AddOptionResponseDTO addOption(@PathVariable Long id, @RequestParam @NotBlank String name) {
        return electionService.addOption(id, name);
    }
}
