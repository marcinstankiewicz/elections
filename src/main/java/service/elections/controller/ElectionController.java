package service.elections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.elections.model.Election;
import service.elections.model.Option;
import service.elections.repository.ElectionRepository;
import service.elections.repository.OptionRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elections")
public class ElectionController {
    private final ElectionRepository repo;
    private final OptionRepository optionRepo;

    @PostMapping
    public Election create(@RequestParam String name) {
        Election e = new Election();
        e.setName(name);
        return repo.save(e);
    }

    @PostMapping("/{id}/options")
    public Option addOption(@PathVariable Long id, @RequestParam String name) {
        Election e = repo.findById(id).orElseThrow();

        Option o = new Option();
        o.setName(name);
        o.setElection(e);

        return optionRepo.save(o);
    }
}
