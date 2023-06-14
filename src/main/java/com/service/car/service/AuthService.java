package com.service.car.service;

import com.google.zxing.WriterException;
import com.service.car.domain.Account;
import com.service.car.domain.Role;
import com.service.car.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final TOTPService totpService;
    private final JwtDecoder jwtDecoder;

    public BufferedImage register(Account account) throws WriterException {
        if(accountRepo.findByEmail(account.getUsername()).isPresent()) throw new RuntimeException();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setSecret2FA(totpService.generateSecretKey());
        account.getRoles().add(Role.USER);
        accountRepo.save(account);
        return totpService.generate(account.getUsername(), account.getSecret2FA());
    }

    public String login(Account account, String code) {
        Account acc = accountRepo.findByEmail(account.getUsername()).orElseThrow();
        log.info("Input code - {}, Generated code - {}", code, totpService.getTOTPCode(acc.getSecret2FA()));
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
        if(code != null && code.trim().equals(totpService.getTOTPCode(acc.getSecret2FA()))){
            return tokenService.generateToken(authenticate);
        } else if(authenticate.isAuthenticated()){
            return "Предоставьте токен";
        } else throw new RuntimeException("Incorrect TOTP");

//        Authentication authenticate = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
//        return tokenService.generateToken(authenticate);
    }

//    public BufferedImage turnOn2FA(String token) throws WriterException {
//        String email = jwtDecoder.decode(token.substring(7)).getSubject();
//        log.info(email);
//        Account account = accountRepo.findByEmail(email).orElseThrow();
//        account.set2FA(true);
//        account.setSecret2FA(totpService.generateSecretKey());
//        accountRepo.save(account);
//        return totpService.generate(email, account.getSecret2FA());
//    }

//    public String validate(String code, Account account) {
//        Account acc = accountRepo.findByEmail(account.getUsername()).orElseThrow();
//        log.info("Input code - {}, Generated code - {}", code, totpService.getTOTPCode(acc.getSecret2FA()));
//        if(code.trim().equals(totpService.getTOTPCode(acc.getSecret2FA()))){
//            Authentication authenticate = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
//            return tokenService.generateToken(authenticate);
//        }
//        throw new RuntimeException("Incorrect TOTP");
//    }
}
