package service.elections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.elections.controller.dto.ElectionResponseDTO;
import service.elections.controller.dto.OptionResponseDTO;
import service.elections.service.ElectionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elections")
public class ElectionController {
    private final ElectionService electionService;

    @PostMapping
    public ElectionResponseDTO create(@RequestParam String name) {
        return electionService.createElection(name);
    }

    @PostMapping("/{id}/options")
    public OptionResponseDTO addOption(@PathVariable Long id, @RequestParam String name) {
        return electionService.addOption(id, name);
    }
}
