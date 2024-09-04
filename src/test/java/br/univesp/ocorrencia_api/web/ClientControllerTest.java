package br.univesp.ocorrencia_api.web;

import br.univesp.ocorrencia_api.controller.ClientController;
import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.service.ClientService;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static br.univesp.ocorrencia_api.common.ClientConstants.CLIENT_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    void createClient_WithValidData_ReturnCreated() throws Exception{
        ClientResponseDTO clientResponseDTO = ClientResponseDTO.fromEntity(Client.fromRequest(CLIENT_DTO.name(), CLIENT_DTO.birthDate(), CLIENT_DTO.cpf()));
        when(clientService.create(CLIENT_DTO)).thenReturn(clientResponseDTO);
        mockMvc.perform(post("/clients").content(objectMapper.writeValueAsString(CLIENT_DTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(clientResponseDTO.name()))
                .andExpect(jsonPath("$.birthDate").value(clientResponseDTO.birthDate().toString()))
                .andExpect(jsonPath("$.cpf").value(clientResponseDTO.cpf()));
    }

    @Test
    void createClient_WithInvalidData_ReturnsBadRequest() throws Exception{
        ClientResponseDTO invalidClient = new ClientResponseDTO("", LocalDate.of(2000, 1, 1), "");
        mockMvc.perform(post("/clients").content(objectMapper.writeValueAsString(invalidClient)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
