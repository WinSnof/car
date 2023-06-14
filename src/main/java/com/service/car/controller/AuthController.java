package com.service.car.controller;

import com.google.zxing.WriterException;
import com.service.car.domain.Account;
import com.service.car.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

//    //Авторизация и выдача токена
//    @PostMapping("/token")
//    String getToken(Authentication authentication) {
//        log.info("Token for requested user - {}", authentication.getName());
//        String token = tokenService.generateToken(authentication);
//        return token;
//    }

    @PostMapping(value = "/reg", produces = MediaType.IMAGE_PNG_VALUE)
    ResponseEntity<BufferedImage> registration(@RequestBody Account account) throws WriterException {
        return ResponseEntity.ok(authService.register(account));
    }

    @PostMapping("/log/{pin}")
    ResponseEntity<String> login(@RequestBody Account account, @PathVariable("pin") String code) {
        String login = authService.login(account, code);
        return ResponseEntity.ok(login);
    }


//    @GetMapping("/verify-2fa/{code}")
//    ResponseEntity verify(@PathVariable("code") String code, @RequestBody Account account) {
//        return ResponseEntity.ok(authService.validate(code, account));
//    }
}
