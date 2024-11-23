package com.louishhy.paperlinkbackend.service;

import com.louishhy.paperlinkbackend.dto.user.AuthenticationResult;
import com.louishhy.paperlinkbackend.dto.user.JwtResponse;
import com.louishhy.paperlinkbackend.dto.user.LoginRequest;
import com.louishhy.paperlinkbackend.dto.user.RegisterRequest;
import com.louishhy.paperlinkbackend.model.Account;
import com.louishhy.paperlinkbackend.repository.AccountRepository;
import com.louishhy.paperlinkbackend.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailService userDetailsService, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public AuthenticationResult login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            final UserDetails userDetails = (UserDetails) auth.getPrincipal();
            final String token = jwtTokenUtil.doGenerateToken(
                    new HashMap<>(),
                    userDetails.getUsername()
            );
            return AuthenticationResult.builder()
                    .success(true)
                    .token(token)
                    .username(userDetails.getUsername())
                    .expiresIn(jwtExpiration)
                    .build();

        } catch (BadCredentialsException e) {
            return AuthenticationResult.builder()
                    .success(false)
                    .build();
        }
    }

    // Use for checking the user's login status with valid JWT
    public ResponseEntity<?> checkLoginStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // If anonymous user, return 401
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (accountRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setHashedPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setStatus(Account.AccountStatus.ACTIVE);

        accountRepository.save(account);
        return ResponseEntity.ok().build();
    }
}
