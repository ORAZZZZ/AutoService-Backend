package com.oraz.saken.demo.service;

import com.oraz.saken.demo.entity.Role;
import com.oraz.saken.demo.entity.User;
import com.oraz.saken.demo.exception.NotFoundException;
import com.oraz.saken.demo.repository.RoleRepository;
import com.oraz.saken.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Role role = roleRepository.findByName(roleName.toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new NotFoundException("Роль не найдена"));

        user.getRoles().add(role);

        userRepository.save(user);
    }

    public void removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Role role = roleRepository.findByName(roleName.toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new NotFoundException("Роль не найдена"));

        if (!user.getRoles().contains(role)) {
            throw new NotFoundException("У пользователя нет такой роли");
        }

        user.getRoles().remove(role);

        userRepository.save(user);
    }

    public void replaceUserRoles(Long userId, List<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Set<Role> newRoles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName.toUpperCase(Locale.ROOT))
                        .orElseThrow(() -> new NotFoundException("Роль " + roleName + " не найдена")))
                .collect(Collectors.toSet());

        user.getRoles().clear();
        user.getRoles().addAll(newRoles);

        userRepository.save(user);
    }
}
