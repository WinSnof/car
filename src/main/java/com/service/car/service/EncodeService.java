package com.service.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EncodeService {

    private final Cipher cipher;

    public String getEncodedText(String password, String toEncode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec((password).getBytes(), "AES"); //Сгенерировать ключ для AES на основании пароля
        cipher.init(Cipher.ENCRYPT_MODE, key); //Зашифовать текст
        return Base64.getEncoder().encodeToString(cipher.doFinal(toEncode.getBytes())); //Вернуть в Base64
    }
}
