package com.example.demo.infra.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Helpers {

    public boolean patternMatches(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }

}
