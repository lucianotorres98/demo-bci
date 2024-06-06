package com.example.demo.infra.constant;

public interface JWTConstant {

    String SECRET_KEY = "mamushka";

    long LONG_EXPIRATION_TIME = 864_000_000; // 10 days
    long EXPIRATION_TIME = 60_000; // 1 minute

    String EMAIL_REGEX = "^[\\w.-]+@[a-z]+\\.+[a-z]+$";

    String PASSWORD_REGEX = "^(?=.*[A-Z])(?![^A-Z]*[A-Z].*[A-Z])(?=(.*\\d){2})(?![^\\d]*\\d[^\\d]*\\d[^\\d]*\\d)[a-zA-Z\\d]{8,12}$";
}
