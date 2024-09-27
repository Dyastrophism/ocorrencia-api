package br.univesp.ocorrencia_api.controller;

import br.univesp.ocorrencia_api.service.ClientService;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponse;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Documentação Guilherme 
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Create client")
    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        var client = clientService.create(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @Operation(summary = "Get all clients")
    @GetMapping
    public Page<ClientResponse> findAllClient(Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @Operation(summary = "Get client by id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update client")
    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.update(id, clientRequest));
    }

    @Operation(summary = "Delete client")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
