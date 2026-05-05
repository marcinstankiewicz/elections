package service.elections.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import service.elections.controller.dto.CreateElectionResponseDTO;
import service.elections.controller.dto.AddOptionResponseDTO;
import service.elections.service.ElectionService;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ElectionController.class)
class ElectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectionService electionService;

    @Test
    void shouldCreateElection() throws Exception {
        // given
        when(electionService.createElection("Test"))
                .thenReturn(new CreateElectionResponseDTO(1L));

        // when & then
        mockMvc.perform(post("/elections")
                        .param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(electionService).createElection("Test");
    }

    @Test
    void shouldReturn400WhenNameIsBlankOnCreate() throws Exception {
        mockMvc.perform(post("/elections")
                        .param("name", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAddOption() throws Exception {
        // given
        when(electionService.addOption(1L, "Option A"))
                .thenReturn(new AddOptionResponseDTO(10L));

        // when & then
        mockMvc.perform(post("/elections/1/options")
                        .param("name", "Option A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L));

        verify(electionService).addOption(1L, "Option A");
    }

    @Test
    void shouldReturn400WhenNameIsBlankOnAddOption() throws Exception {
        mockMvc.perform(post("/elections/1/options")
                        .param("name", ""))
                .andExpect(status().isBadRequest());
    }
}