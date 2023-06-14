package com.service.car.controller;

import com.google.zxing.WriterException;
import com.service.car.domain.Account;
import com.service.car.domain.Profile;
import com.service.car.payload.ProfileRequest;
import com.service.car.repository.AccountRepo;
import com.service.car.repository.ProfileRepo;
import com.service.car.service.AuthService;
import com.service.car.service.EncodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final AuthService authService;
    private final ProfileRepo profileRepo;
    private final AccountRepo accountRepo;
    private final JwtDecoder jwtDecoder;
    private final EncodeService encodeService;

//    @GetMapping(value = "/enable-2fa", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity enable2FA(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws WriterException {
//        return ResponseEntity.ok(authService.turnOn2FA(token));
//    }

    @PostMapping
    public void profile(@RequestBody ProfileRequest profile,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Account byEmail = accountRepo
                .findByEmail(jwtDecoder.decode(token.substring(7)).getSubject()).orElseThrow(() -> new RuntimeException(""));
        Profile toSave = new Profile(encodeService.getEncodedText(profile.password(),
                profile.fullName()),encodeService.getEncodedText(profile.password(),
                profile.fullName()),encodeService.getEncodedText(profile.password(),
                profile.fullName()), byEmail);
        profileRepo.save(toSave);
    }
}
