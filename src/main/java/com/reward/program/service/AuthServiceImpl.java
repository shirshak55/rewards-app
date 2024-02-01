package com.reward.program.service;

import com.reward.program.config.security.JwtProvider;
import com.reward.program.dto.AuthenticationResponse;
import com.reward.program.dto.LoginRequest;
import com.reward.program.dto.RegisterRequest;
import com.reward.program.exceptions.BadRequestException;
import com.reward.program.model.User;
import com.reward.program.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Override
    public void signUp(RegisterRequest registerRequest) throws BadRequestException {
        if(userRepository.findUserByUsername(registerRequest.getUsername()).isEmpty()){
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreated(Instant.now());
            user.setEnabled(true);
            userRepository.save(user);
        }else{
            throw new BadRequestException("Username already Exists");
        }

    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt_token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(jwt_token)
                .username(loginRequest.getUsername())
//                .refreshToken("")
//                .expiresAt("")
                .build();
    }
}
