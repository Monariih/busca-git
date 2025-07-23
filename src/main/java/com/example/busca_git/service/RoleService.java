package com.example.busca_git.service;

import com.example.busca_git.model.Role;
import com.example.busca_git.repository.RoleRepository;
import com.example.busca_git.dto.CreateRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(CreateRoleDto createRoleDto) {
        Role role = new Role();
        role.setName(createRoleDto.getName());
        return roleRepository.save(role);
    }
}