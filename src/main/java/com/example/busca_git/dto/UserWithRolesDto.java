package com.example.busca_git.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserWithRolesDto {
    private Long id;
    private String login;
    private String url;
    private List<String> roles;
}