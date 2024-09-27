package br.univesp.ocorrencia_api.web;

import br.univesp.ocorrencia_api.controller.AddressController;
import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.service.AddressService;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressRequest;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static br.univesp.ocorrencia_api.common.AddressConstants.ADDRESS_REQUEST;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @Test
    void createAddress_WithValidData_ReturnCreated() throws Exception {
        AddressResponse addressResponse = AddressResponse.fromEntity(Address.fromRequest(ADDRESS_REQUEST.publicPlace(), ADDRESS_REQUEST.neighborhood(), ADDRESS_REQUEST.city(), ADDRESS_REQUEST.state(), ADDRESS_REQUEST.zipCode()));
        when(addressService.createAddress(ADDRESS_REQUEST)).thenReturn(addressResponse);
        mockMvc.perform(post("/addresses").content(objectMapper.writeValueAsString(ADDRESS_REQUEST)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.publicPlace").value(addressResponse.publicPlace()))
                .andExpect(jsonPath("$.neighborhood").value(addressResponse.neighborhood()))
                .andExpect(jsonPath("$.zipCode").value(addressResponse.zipCode()))
                .andExpect(jsonPath("$.city").value(addressResponse.city()))
                .andExpect(jsonPath("$.state").value(addressResponse.state()));
    }

    @Test
    void findAllAddresses_ReturnsPageOfAddresses() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        AddressResponse addressResponse = new AddressResponse("publicPlace", "neighborhood", "city", "state", "zipCode");
        Page<AddressResponse> page = new PageImpl<>(List.of(addressResponse));
        when(addressService.findAllAddresses(pageable)).thenReturn(page);

        mockMvc.perform(get("/addresses")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].publicPlace").value(addressResponse.publicPlace()))
                .andExpect(jsonPath("$.content[0].neighborhood").value(addressResponse.neighborhood()))
                .andExpect(jsonPath("$.content[0].city").value(addressResponse.city()))
                .andExpect(jsonPath("$.content[0].state").value(addressResponse.state()))
                .andExpect(jsonPath("$.content[0].zipCode").value(addressResponse.zipCode()));
    }

    @Test
    void findAddressById_WithValidId_ReturnsAddress() throws Exception {
        Long id = 1L;
        AddressResponse addressResponse = new AddressResponse("publicPlace", "neighborhood", "city", "state", "zipCode");
        when(addressService.findAddressById(id)).thenReturn(Optional.of(addressResponse));

        mockMvc.perform(get("/addresses/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicPlace").value(addressResponse.publicPlace()))
                .andExpect(jsonPath("$.neighborhood").value(addressResponse.neighborhood()))
                .andExpect(jsonPath("$.city").value(addressResponse.city()))
                .andExpect(jsonPath("$.state").value(addressResponse.state()))
                .andExpect(jsonPath("$.zipCode").value(addressResponse.zipCode()));
    }

    @Test
    void findAddressById_WithInvalidId_ReturnsNotFound() throws Exception {
        Long id = 1L;
        when(addressService.findAddressById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/addresses/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAddress_WithValidData_ReturnsUpdatedAddress() throws Exception {
        Long id = 1L;
        AddressRequest addressRequest = new AddressRequest("publicPlace", "neighborhood", "city", "state", "zipCode");
        AddressResponse addressResponse = new AddressResponse("publicPlace", "neighborhood", "city", "state", "zipCode");
        when(addressService.updateAddress(id, addressRequest)).thenReturn(addressResponse);

        mockMvc.perform(patch("/addresses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicPlace").value(addressResponse.publicPlace()))
                .andExpect(jsonPath("$.neighborhood").value(addressResponse.neighborhood()))
                .andExpect(jsonPath("$.city").value(addressResponse.city()))
                .andExpect(jsonPath("$.state").value(addressResponse.state()))
                .andExpect(jsonPath("$.zipCode").value(addressResponse.zipCode()));
    }

    @Test
    void deleteAddress_WithValidId_ReturnsNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/addresses/{id}", id))
                .andExpect(status().isNoContent());
    }


}
