package service.elections.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.elections.controller.dto.AddVoterResponseDTO;
import service.elections.controller.dto.GetVotersResponseDTO;
import service.elections.service.VoterService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/voters")
public class VoterController {
    private final VoterService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GetVotersResponseDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public AddVoterResponseDTO add(@RequestParam @NotBlank String name) {
        return service.addVoter(name);
    }

    @PostMapping("/{id}/block")
    public void block(@PathVariable Long id) {
        service.block(id);
    }

    @PostMapping("/{id}/unblock")
    public void unblock(@PathVariable Long id) {
        service.unblock(id);
    }
}
