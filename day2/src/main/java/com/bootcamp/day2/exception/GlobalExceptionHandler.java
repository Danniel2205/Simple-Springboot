package com.bootcamp.day2.exception;

import com.bootcamp.day2.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(QuoteUnavailableException.class)
    public ResponseEntity<BaseResponse<Object>> handleQuoteUnavailableException(QuoteUnavailableException quoteUnavailableException) {
        log.info("QuoteUnavailableException: {}", quoteUnavailableException.getMessage());
        return build(HttpStatus.SERVICE_UNAVAILABLE, quoteUnavailableException.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException validException) {
        List<String> message = new ArrayList<>();
        for (FieldError fieldError : validException.getBindingResult().getFieldErrors()) {
            message.add(fieldError.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, String.join(",", message));
    }


    @ExceptionHandler(AnalysisNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound (AnalysisNotFoundException exception) {
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnalysisAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Object>> handleAlreadyExists (AnalysisAlreadyExistsException exception) {
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<BaseResponse<Object>> build(HttpStatus status,String message) {
        return ResponseEntity.status(status).body(BaseResponse.error(message));
        }
    }
