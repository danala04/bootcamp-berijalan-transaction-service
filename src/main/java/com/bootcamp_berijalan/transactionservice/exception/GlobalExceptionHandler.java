package com.bootcamp_berijalan.transactionservice.exception;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<BaseResponseDto> buildResponse(HttpStatus status, String message) {
        BaseResponseDto response = BaseResponseDto.builder()
                .status(status)
                .description(message)
                .data(new HashMap<>())
                .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(
            {
                    UsernameNotFoundException.class,
                    WalletTypeNotFoundException.class,
                    AuthenticationException.class,
                    WalletNotFoundException.class,
                    TransactionTypeNotFoundException.class,
                    CategoryNotFoundException.class,
                    TransactionNotFoundException.class
            }
            )
    public ResponseEntity<BaseResponseDto> handleCustomExceptions(RuntimeException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessages.append(fieldName).append(": ").append(errorMessage).append("; ");
        });

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation error: " + errorMessages.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDto> handleRuntimeException(RuntimeException exception) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<BaseResponseDto> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException exception) {
        return buildResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponseDto> handleAccessDeniedException(AccessDeniedException exception) {
        return buildResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }
}
