package com.reward.program.service;

import com.reward.program.dto.AuthenticationResponse;
import com.reward.program.dto.LoginRequest;
import com.reward.program.dto.RegisterRequest;
import com.reward.program.exceptions.BadRequestException;

public interface AuthService {
    void signUp(RegisterRequest registerRequest) throws BadRequestException;
    AuthenticationResponse login(LoginRequest loginRequest);
}
