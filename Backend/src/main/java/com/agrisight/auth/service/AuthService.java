package com.agrisight.auth.service;

import com.agrisight.auth.dto.AuthRequest;
import com.agrisight.auth.dto.AuthResponse;
import com.agrisight.auth.dto.RegisterRequest;
import com.agrisight.exception.BadRequestException;
import com.agrisight.security.JwtTokenProvider;
import com.agrisight.security.UserPrincipal;
import com.agrisight.user.entity.Role;
import com.agrisight.user.entity.User;
import com.agrisight.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Set<String> roles = principal.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toSet());

        return new AuthResponse(jwt, principal.getId(), principal.getName(), principal.getEmail(), roles);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered: " + request.getEmail());
        }

        Set<Role> roles = (request.getRoles() == null || request.getRoles().isEmpty())
                ? Set.of(Role.ROLE_OFFICER)
                : request.getRoles();

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                roles
        );

        user = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String jwt = tokenProvider.generateToken(authentication);

        Set<String> roleStrings = roles.stream().map(Role::name).collect(Collectors.toSet());
        return new AuthResponse(jwt, user.getId(), user.getName(), user.getEmail(), roleStrings);
    }
}
