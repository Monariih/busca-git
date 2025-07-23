package com.example.busca_git.service;

import com.example.busca_git.dto.UserWithRolesDto;
import com.example.busca_git.model.Role;
import com.example.busca_git.model.User;
import com.example.busca_git.repository.RoleRepository;
import com.example.busca_git.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("testuser");
        testUser.setUrl("https://github.com/testuser");
        testUser.setRoles(new HashSet<>());

        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("ADMIN");
    }

    @Test
    void testAssignRoleToUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(testRole));

        userService.assignRoleToUser(1L, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).saveAndFlush(testUser);
        assertEquals(1, testUser.getRoles().size());
    }
}