package com.example.busca_git.service;

import com.example.busca_git.model.User;
import com.example.busca_git.model.Role;
import com.example.busca_git.repository.UserRepository;
import com.example.busca_git.repository.RoleRepository;
import com.example.busca_git.dto.UserWithRolesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Simply add role to user's roles collection and save
        user.getRoles().add(role);
        userRepository.saveAndFlush(user);
    }

    public List<UserWithRolesDto> getAllUsersWithRoles() {
        return userRepository.findAllWithRoles().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserWithRolesDto convertToDto(User user) {
        UserWithRolesDto dto = new UserWithRolesDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setUrl(user.getUrl());
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        return dto;
    }
}