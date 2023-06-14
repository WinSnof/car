package com.service.car.payload;

public record ProfileRequest(
        String fullName,
        String passport,
        String phoneNumber,
        String password
) {
}
