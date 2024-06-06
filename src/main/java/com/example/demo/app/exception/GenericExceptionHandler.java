package com.example.demo.app.exception;

import com.example.demo.interfaces.dto.error.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleRuntimeException(RuntimeException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<CustomErrorResponse> handleConflict(HttpClientErrorException.Conflict ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), // Use HTTP status code 409 for Conflict
                ex.getMessage()
        );

        CustomErrorResponse errorResponse = new CustomErrorResponse(Collections.singletonList(errorDetail));

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
