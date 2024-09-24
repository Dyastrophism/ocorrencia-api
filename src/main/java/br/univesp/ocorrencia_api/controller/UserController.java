package br.univesp.ocorrencia_api.controller;

import br.univesp.ocorrencia_api.service.UserService;
import br.univesp.ocorrencia_api.usecases.userusecases.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRequest user) {
        userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.login(user));
    }

}
