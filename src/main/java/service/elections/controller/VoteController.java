package service.elections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.elections.controller.dto.VoteRequestDTO;
import service.elections.service.VoteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoteService service;

    @PostMapping
    public void vote(VoteRequestDTO voteRequestDTO) {
        service.vote(voteRequestDTO);
    }
}
