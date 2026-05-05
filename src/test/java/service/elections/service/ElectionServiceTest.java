package service.elections.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.elections.controller.dto.AddOptionResponseDTO;
import service.elections.controller.dto.CreateElectionResponseDTO;
import service.elections.persistence.model.Election;
import service.elections.persistence.model.Option;
import service.elections.persistence.repository.ElectionRepository;
import service.elections.persistence.repository.OptionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectionServiceTest {

    @Mock
    private ElectionRepository repo;

    @Mock
    private OptionRepository optionRepo;

    @InjectMocks
    private ElectionService service;

    private Election election;

    @BeforeEach
    void setUp() {
        election = new Election();
        election.setId(1L);
        election.setName("Test Election");
    }

    @Test
    void shouldCreateElection() {
        // given
        when(repo.save(any(Election.class))).thenAnswer(invocation -> {
            Election e = invocation.getArgument(0);
            e.setId(1L);
            return e;
        });

        // when
        CreateElectionResponseDTO response = service.createElection("New Election");

        // then
        assertNotNull(response);
        assertEquals(1L, response.id());

        verify(repo).save(any(Election.class));
    }

    @Test
    void shouldAddOptionToElection() {
        // given
        when(repo.findById(1L)).thenReturn(Optional.of(election));

        when(optionRepo.save(any(Option.class))).thenAnswer(invocation -> {
            Option o = invocation.getArgument(0);
            o.setId(10L);
            return o;
        });

        // when
        AddOptionResponseDTO response = service.addOption(1L, "Option A");

        // then
        assertNotNull(response);
        assertEquals(10L, response.id());

        verify(repo).findById(1L);
        verify(optionRepo).save(any(Option.class));
    }

    @Test
    void shouldThrowExceptionWhenElectionNotFound() {
        // given
        when(repo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () ->
                service.addOption(1L, "Option A")
        );

        verify(repo).findById(1L);
        verify(optionRepo, never()).save(any());
    }
}