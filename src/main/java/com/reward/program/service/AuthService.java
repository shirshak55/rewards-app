package com.reward.program.service;

import com.reward.program.dto.AuthenticationResponse;
import com.reward.program.dto.LoginRequest;
import com.reward.program.dto.RegisterRequest;

public interface AuthService {
    void signUp(RegisterRequest registerRequest);
    AuthenticationResponse login(LoginRequest loginRequest);
}
