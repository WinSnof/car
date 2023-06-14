package com.service.car.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {

    @Bean
    public QRCodeWriter qrCodeWriter() {
        return new QRCodeWriter();
    }

    @Bean
    public HttpMessageConverter<BufferedImage> imageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @Bean
    public Cipher getAESInstance() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance("AES");
    }
}
