package com.example.busca_git.controller;

import com.example.busca_git.dto.AssignRoleDto;
import com.example.busca_git.dto.UserWithRolesDto;
import com.example.busca_git.service.GitHubSyncService;
import com.example.busca_git.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GitHubSyncService gitHubSyncService;

    @Test
    @WithMockUser(username = "admin", password = "password")
    void testGetAllUsersWithRoles() throws Exception {

        UserWithRolesDto userDto = new UserWithRolesDto();
        userDto.setId(1L);
        userDto.setLogin("testuser");
        userDto.setUrl("https://github.com/testuser");
        userDto.setRoles(Arrays.asList("ADMIN"));

        List<UserWithRolesDto> usersList = Arrays.asList(userDto);
        when(userService.getAllUsersWithRoles()).thenReturn(usersList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].login").value("testuser"))
                .andExpect(jsonPath("$[0].roles[0]").value("ADMIN"));

        verify(userService, times(1)).getAllUsersWithRoles();
    }
}