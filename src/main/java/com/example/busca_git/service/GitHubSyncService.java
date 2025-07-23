package com.example.busca_git.service;

import com.example.busca_git.model.User;
import com.example.busca_git.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GitHubSyncService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final String GITHUB_API_URL = "https://api.github.com/users";

    public void syncGitHubUsers() {
        try {
            // Busca os primeiros 30 usuários do GitHub
            List<Map<String, Object>> githubUsers = restTemplate.getForObject(
                    GITHUB_API_URL + "?per_page=30", List.class);

            if (githubUsers != null) {
                for (Map<String, Object> githubUser : githubUsers) {
                    String login = (String) githubUser.get("login");
                    String htmlUrl = (String) githubUser.get("html_url");

                    if (login != null && htmlUrl != null) {
                        // Verifica se já existe
                        User existingUser = userRepository.findByLogin(login);
                        if (existingUser == null) {
                            User user = new User();
                            user.setLogin(login);
                            user.setUrl(htmlUrl);
                            userRepository.save(user);
                        }
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro ao sincronizar usuários do GitHub: " + e.getMessage());
        }
    }
}