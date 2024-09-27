package br.univesp.ocorrencia_api.web;

import br.univesp.ocorrencia_api.controller.ClientController;
import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.service.ClientService;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static br.univesp.ocorrencia_api.common.ClientConstants.CLIENT_RESQUEST;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void createClientClient_WithValidData_ReturnCreated() throws Exception{
        ClientResponse clientResponse = ClientResponse.fromEntity(Client.fromRequest(CLIENT_RESQUEST.name(), CLIENT_RESQUEST.birthDate(), CLIENT_RESQUEST.cpf()));
        when(clientService.create(CLIENT_RESQUEST)).thenReturn(clientResponse);
        mockMvc.perform(post("/clients").content(objectMapper.writeValueAsString(CLIENT_RESQUEST)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(clientResponse.name()))
                .andExpect(jsonPath("$.birthDate").value(clientResponse.birthDate().toString()))
                .andExpect(jsonPath("$.cpf").value(clientResponse.cpf()));
    }

    @Test
    void createClientClient_WithInvalidData_ReturnsBadRequest() throws Exception{
        ClientResponse invalidClient = new ClientResponse("", LocalDate.of(2000, 1, 1), "");
        mockMvc.perform(post("/clients").content(objectMapper.writeValueAsString(invalidClient)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllClientClients_ReturnPageOfClients() throws Exception{
        Pageable pageable = PageRequest.of(0, 10);
        when(clientService.findAll(pageable)).thenReturn(null);
        mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllClients_ReturnNoContent() throws Exception{
        Pageable pageable = PageRequest.of(99, 10);
        when(clientService.findAll(pageable)).thenReturn(Page.empty());
        mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
