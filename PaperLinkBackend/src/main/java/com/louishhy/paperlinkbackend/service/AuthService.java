package com.louishhy.paperlinkbackend.service;

import com.louishhy.paperlinkbackend.dto.user.JwtResponse;
import com.louishhy.paperlinkbackend.dto.user.LoginRequest;
import com.louishhy.paperlinkbackend.dto.user.RegisterRequest;
import com.louishhy.paperlinkbackend.model.Account;
import com.louishhy.paperlinkbackend.repository.AccountRepository;
import com.louishhy.paperlinkbackend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailService userDetailsService, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            final UserDetails userDetails = (UserDetails) auth.getPrincipal();
            final String token = jwtTokenUtil.doGenerateToken(
                    new HashMap<>(),
                    userDetails.getUsername()
            );

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

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
