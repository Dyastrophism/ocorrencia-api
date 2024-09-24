package br.univesp.ocorrencia_api.controller;

import br.univesp.ocorrencia_api.service.AddressService;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressRequest;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    public final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        var response = addressService.createAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<AddressResponse> findAllAddresses(Pageable pageable) {
        return addressService.findAllAddresses(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findAddressById(@PathVariable Long id) {
        return addressService.findAddressById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(id, addressRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
