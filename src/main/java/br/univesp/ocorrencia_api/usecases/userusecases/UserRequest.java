package br.univesp.ocorrencia_api.usecases.userusecases;

import br.univesp.ocorrencia_api.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserRequest(
        @NotBlank
        String username,
        @Size(min = 6, max = 100)
        @NotBlank
        String password
) {
        public User toEntity(PasswordEncoder passwordEncoder) {
                return User.fromRequest(username, passwordEncoder.encode(password));
        }
}
