package com.erp.backend.security;

import com.erp.backend.entity.User;
import com.erp.backend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        var authorities = u.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Convertir ERole en String
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                authorities
        );
    }
}