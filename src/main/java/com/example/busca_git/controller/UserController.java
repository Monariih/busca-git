package com.example.busca_git.controller;

import com.example.busca_git.service.UserService;
import com.example.busca_git.service.GitHubSyncService;
import com.example.busca_git.dto.AssignRoleDto;
import com.example.busca_git.dto.UserWithRolesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final GitHubSyncService gitHubSyncService;

    @PostMapping("/sync")
    public ResponseEntity<Void> syncGitHubUsers() {
        gitHubSyncService.syncGitHubUsers();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign-role")
    public ResponseEntity<Void> assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        userService.assignRoleToUser(assignRoleDto.getUserId(), assignRoleDto.getRoleId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserWithRolesDto>> getAllUsersWithRoles() {
        List<UserWithRolesDto> users = userService.getAllUsersWithRoles();
        return ResponseEntity.ok(users);
    }
}