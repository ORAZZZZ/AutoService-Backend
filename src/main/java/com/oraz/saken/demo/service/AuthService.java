package com.oraz.saken.demo.service;

import com.oraz.saken.demo.dto.auth.AuthResponse;
import com.oraz.saken.demo.dto.auth.LoginRequest;
import com.oraz.saken.demo.dto.auth.RegisterRequest;
import com.oraz.saken.demo.entity.Role;
import com.oraz.saken.demo.entity.User;
import com.oraz.saken.demo.exception.ConflictException;
import com.oraz.saken.demo.exception.NotFoundException;
import com.oraz.saken.demo.exception.UnauthorizedException;
import com.oraz.saken.demo.repository.RoleRepository;
import com.oraz.saken.demo.repository.UserRepository;
import com.oraz.saken.demo.service.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Пользователь с таким email уже существует");
        }

        Role clientRole = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new NotFoundException("Роль CLIENT не найдена"));

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(clientRole));

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Неверный email или пароль"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Неверный email или пароль");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
