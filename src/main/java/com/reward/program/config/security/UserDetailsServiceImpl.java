package com.reward.program.config.security;

import com.reward.program.model.User;
import com.reward.program.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            Optional<User> userOptional = userRepository.findUserByUsername(username);
            User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username + "Not Found"));
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.getEnabled(),
                    true, true, true,
                    getAuthorities("USER")
            );
        } catch (Error err) {
            System.out.println("err: " + err);
            throw err;
        }
    }
    public Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

}
