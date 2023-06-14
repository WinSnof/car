package com.service.car.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import de.taimos.totp.TOTP;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TOTPService {
    //TOTP Impl
    @Value("${app.issuer}")
    private String issuer;
    private final QRCodeWriter qrCodeWriter;

    //Создает TOTP на основе ключа
    public String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    //Генерирует случайный 32 байтный ключ для 2FA
    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public BufferedImage generate(String email, String secretKey) throws WriterException {
        var uri = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", issuer,email,secretKey, issuer);
        BitMatrix matrix = qrCodeWriter.encode(uri, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
