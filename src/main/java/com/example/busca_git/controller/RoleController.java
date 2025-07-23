package com.example.busca_git.controller;

import com.example.busca_git.model.Role;
import com.example.busca_git.service.RoleService;
import com.example.busca_git.dto.CreateRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleDto createRoleDto) {
        Role role = roleService.createRole(createRoleDto);
        return ResponseEntity.ok(role);
    }
}