package com.school.service;

import com.school.dto.request.LoginRequest;
import com.school.dto.request.RefreshTokenRequest;
import com.school.dto.response.AuthResponse;
import com.school.entity.RefreshToken;
import com.school.entity.User;
import com.school.exception.BadRequestException;
import com.school.repository.UserRepository;
import com.school.security.JwtUtil;
import com.school.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found."));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken storedToken = refreshTokenService.findByToken(request.getRefreshToken());
        storedToken = refreshTokenService.verifyExpiration(storedToken);

        User user = storedToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(storedToken.getToken())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public void logout(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }
}