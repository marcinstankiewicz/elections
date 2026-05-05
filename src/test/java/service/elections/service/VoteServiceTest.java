package service.elections.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.elections.controller.dto.VoteRequestDTO;
import service.elections.persistence.model.*;

import service.elections.persistence.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepo;
    @Mock
    private VoterRepository voterRepo;
    @Mock
    private ElectionRepository electionRepo;
    @Mock
    private OptionRepository optionRepo;

    @InjectMocks
    private VoteService service;

    private Voter voter;
    private Election election;
    private Option option;

    @BeforeEach
    void setUp() {
        voter = new Voter();
        voter.setId(1L);
        voter.setBlocked(false);

        election = new Election();
        election.setId(2L);

        option = new Option();
        option.setId(3L);
        option.setElection(election);
    }

    @Test
    void shouldVoteSuccessfully() {
        // given
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.of(voter));
        when(voteRepo.existsByVoterIdAndElectionId(1L, 2L)).thenReturn(false);
        when(electionRepo.findById(2L)).thenReturn(Optional.of(election));
        when(optionRepo.findById(3L)).thenReturn(Optional.of(option));

        // when
        service.vote(request);

        // then
        verify(voteRepo).save(any(Vote.class));
    }

    @Test
    void shouldThrowWhenVoterBlocked() {
        // given
        voter.setBlocked(true);
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.of(voter));

        // when & then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.vote(request));

        assertEquals("Voter is blocked", ex.getMessage());
        verify(voteRepo, never()).save(any());
    }

    @Test
    void shouldThrowWhenAlreadyVoted() {
        // given
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.of(voter));
        when(voteRepo.existsByVoterIdAndElectionId(1L, 2L)).thenReturn(true);

        // when & then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.vote(request));

        assertEquals("Already voted", ex.getMessage());
        verify(voteRepo, never()).save(any());
    }

    @Test
    void shouldThrowWhenOptionNotInElection() {
        // given
        Election otherElection = new Election();
        otherElection.setId(999L);

        option.setElection(otherElection);

        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.of(voter));
        when(voteRepo.existsByVoterIdAndElectionId(1L, 2L)).thenReturn(false);
        when(electionRepo.findById(2L)).thenReturn(Optional.of(election));
        when(optionRepo.findById(3L)).thenReturn(Optional.of(option));

        // when & then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.vote(request));

        assertEquals("Option not in this election", ex.getMessage());
        verify(voteRepo, never()).save(any());
    }

    @Test
    void shouldThrowWhenVoterNotFound() {
        // given
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> service.vote(request));

        verify(voteRepo, never()).save(any());
    }

    @Test
    void shouldThrowWhenElectionNotFound() {
        // given
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.of(voter));
        when(voteRepo.existsByVoterIdAndElectionId(1L, 2L)).thenReturn(false);
        when(electionRepo.findById(2L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> service.vote(request));

        verify(voteRepo, never()).save(any());
    }

    @Test
    void shouldThrowWhenOptionNotFound() {
        // given
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        when(voterRepo.findById(1L)).thenReturn(Optional.of(voter));
        when(voteRepo.existsByVoterIdAndElectionId(1L, 2L)).thenReturn(false);
        when(electionRepo.findById(2L)).thenReturn(Optional.of(election));
        when(optionRepo.findById(3L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> service.vote(request));

        verify(voteRepo, never()).save(any());
    }
}