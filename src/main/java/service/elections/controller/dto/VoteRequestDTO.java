package service.elections.controller.dto;

public record VoteRequestDTO(Long voterId, Long electionId, Long optionId) {
}
