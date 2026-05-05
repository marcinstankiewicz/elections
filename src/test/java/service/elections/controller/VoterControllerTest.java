package service.elections.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.elections.controller.dto.AddVoterResponseDTO;
import service.elections.service.VoterService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoterController.class)
class VoterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoterService voterService;

    @Autowired
    private ObjectMapper objectMapper;

    // -----------------------
    // ADD VOTER
    // -----------------------

    @Test
    void shouldAddVoter() throws Exception {
        // given
        when(voterService.addVoter("Jan"))
                .thenReturn(new AddVoterResponseDTO(1L));

        // when & then
        mockMvc.perform(post("/voters")
                        .param("name", "Jan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(voterService).addVoter("Jan");
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        mockMvc.perform(post("/voters")
                        .param("name", ""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(voterService);
    }

    @Test
    void shouldReturn400WhenNameMissing() throws Exception {
        mockMvc.perform(post("/voters"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(voterService);
    }

    // -----------------------
    // BLOCK
    // -----------------------

    @Test
    void shouldBlockVoter() throws Exception {
        doNothing().when(voterService).block(1L);

        mockMvc.perform(post("/voters/1/block"))
                .andExpect(status().isOk());

        verify(voterService).block(1L);
    }

    @Test
    void shouldCallBlockWithCorrectId() throws Exception {
        doNothing().when(voterService).block(anyLong());

        mockMvc.perform(post("/voters/99/block"))
                .andExpect(status().isOk());

        verify(voterService).block(99L);
    }

    // -----------------------
    // UNBLOCK
    // -----------------------

    @Test
    void shouldUnblockVoter() throws Exception {
        doNothing().when(voterService).unblock(1L);

        mockMvc.perform(post("/voters/1/unblock"))
                .andExpect(status().isOk());

        verify(voterService).unblock(1L);
    }

    @Test
    void shouldCallUnblockWithCorrectId() throws Exception {
        doNothing().when(voterService).unblock(anyLong());

        mockMvc.perform(post("/voters/50/unblock"))
                .andExpect(status().isOk());

        verify(voterService).unblock(50L);
    }
}