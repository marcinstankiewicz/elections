package service.elections.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.elections.controller.dto.VoteRequestDTO;
import service.elections.service.VoteService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldVoteSuccessfully() throws Exception {
        // given
        VoteRequestDTO request = new VoteRequestDTO(1L, 2L, 3L);

        doNothing().when(voteService).vote(any(VoteRequestDTO.class));

        // when & then
        mockMvc.perform(post("/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(voteService).vote(any(VoteRequestDTO.class));
    }

    @Test
    void shouldCallServiceWithCorrectData() throws Exception {
        // given
        VoteRequestDTO request = new VoteRequestDTO(10L, 20L, 30L);

        doNothing().when(voteService).vote(any(VoteRequestDTO.class));

        // when
        mockMvc.perform(post("/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // then
        verify(voteService).vote(argThat(dto ->
                dto.voterId().equals(10L) &&
                        dto.electionId().equals(20L) &&
                        dto.optionId().equals(30L)
        ));
    }

    @Test
    void shouldReturn400WhenBodyIsMissing() throws Exception {
        mockMvc.perform(post("/votes"))
                .andExpect(status().isBadRequest());
    }
}