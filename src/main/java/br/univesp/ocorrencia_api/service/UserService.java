package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.User;
import br.univesp.ocorrencia_api.repository.UserRepository;
import br.univesp.ocorrencia_api.usecases.userusecases.UserRequest;
import br.univesp.ocorrencia_api.usecases.userusecases.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register user
     * @param userRequest user request
     * @return user response
     */
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new IllegalArgumentException("Login already exists");
        }
        User user = userRequest.toEntity(passwordEncoder);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new UserResponse(user);
    }

    /**
     * Login user
     * @param userRequest user request
     * @return token
     */
    public String login(UserRequest userRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequest.username(), userRequest.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return tokenService.generateToken(authentication.getName());
    }

}
