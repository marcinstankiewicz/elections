package service.elections.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.elections.controller.dto.AddVoterResponseDTO;
import service.elections.persistence.model.Voter;
import service.elections.persistence.repository.VoterRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoterServiceTest {

    @Mock
    private VoterRepository repo;

    @InjectMocks
    private VoterService service;

    private Voter voter;

    @BeforeEach
    void setUp() {
        voter = new Voter();
        voter.setId(1L);
        voter.setName("Jan");
        voter.setBlocked(false);
    }

    @Test
    void shouldAddVoter() {
        // given
        when(repo.save(any(Voter.class))).thenAnswer(invocation -> {
            Voter v = invocation.getArgument(0);
            v.setId(1L);
            return v;
        });

        // when
        AddVoterResponseDTO response = service.addVoter("Jan");

        // then
        assertNotNull(response);
        assertEquals(1L, response.id());

        verify(repo).save(any(Voter.class));
    }

    @Test
    void shouldBlockVoter() {
        // given
        when(repo.findById(1L)).thenReturn(Optional.of(voter));

        // when
        service.block(1L);

        // then
        assertTrue(voter.isBlocked());
        verify(repo).findById(1L);
        verify(repo).save(voter);
    }

    @Test
    void shouldUnblockVoter() {
        // given
        voter.setBlocked(true);
        when(repo.findById(1L)).thenReturn(Optional.of(voter));

        // when
        service.unblock(1L);

        // then
        assertFalse(voter.isBlocked());
        verify(repo).findById(1L);
        verify(repo).save(voter);
    }

    @Test
    void shouldThrowWhenBlockingNonExistingVoter() {
        // given
        when(repo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> service.block(1L));

        verify(repo).findById(1L);
        verify(repo, never()).save(any());
    }

    @Test
    void shouldThrowWhenUnblockingNonExistingVoter() {
        // given
        when(repo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> service.unblock(1L));

        verify(repo).findById(1L);
        verify(repo, never()).save(any());
    }
}