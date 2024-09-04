package br.univesp.ocorrencia_api.controller;

import br.univesp.ocorrencia_api.service.ClientService;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponseDTO;
import br.univesp.ocorrencia_api.usecases.clientusecases.CreateClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody CreateClientDTO createClientDTO) {
        var client = clientService.create(createClientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping
    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @RequestBody CreateClientDTO createClientDTO) {
        return ResponseEntity.ok(clientService.update(id, createClientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
