package com.service.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class DecodeService {

    private final Cipher cipher;

    public String getDecodedText(String password, String toDecode)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec((password).getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(toDecode)), StandardCharsets.UTF_8);
        //return Base64.getEncoder().encodeToString(cipher.doFinal(toDecode.getBytes()));
    }
}
