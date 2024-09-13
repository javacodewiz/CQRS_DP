package com.javacodewiz.exception;


import com.javacodewiz.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> handleGloabException(Exception exception, WebRequest request)
    {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR,
                "Please Connect with Your IT Administrator", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageDto);
    }


    @ExceptionHandler(ResourceNotSaveException.class)
    public ResponseEntity<ErrorMessageDto> handleDataNotSaveException(ResourceNotSaveException dataNotSaveException, WebRequest request)
    {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                dataNotSaveException.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request)
    {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                resourceNotFoundException.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageDto);
    }
}
