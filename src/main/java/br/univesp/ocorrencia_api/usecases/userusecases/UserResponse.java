package br.univesp.ocorrencia_api.usecases.userusecases;

import br.univesp.ocorrencia_api.entity.User;

public record UserResponse(
        String username,
        String password
) {
    public UserResponse(User user) {
        this(user.getUsername(), user.getPassword());
    }

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getUsername(), user.getPassword());
    }
}
