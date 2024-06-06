package com.example.demo.exception;

import com.example.demo.app.exception.GenericExceptionHandler;
import com.example.demo.interfaces.dto.error.CustomErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericExceptionHandlerTest {

    @Test
    void handleBadCredentialsException() {
        // Arrange
        GenericExceptionHandler handler = new GenericExceptionHandler();
        BadCredentialsException exception = new BadCredentialsException("Bad credentials");

        // Act
        ResponseEntity<CustomErrorResponse> responseEntity = handler.handleBadCredentialsException(exception);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Bad credentials", responseEntity.getBody().getError().get(0).getDetail());
    }
}