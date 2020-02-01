package StockExchange.StockExchange.controllers;

import StockExchange.StockExchange.entities.Attributes;
import StockExchange.StockExchange.entities.Entities;
import StockExchange.StockExchange.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ResponseService service;

    @Autowired
    public GlobalExceptionHandler(ResponseService service) {
        this.service = service;
    }

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<String> handleIllegalCaller(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperation(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> handleException(Exception e, HttpStatus status) {
        return new ResponseEntity<>(getMessage(e.getMessage()), status);
    }

    private String getMessage(String code) {
        return service.getMessage(code);
    }

    @ExceptionHandler(EnumConstantNotPresentException.class)
    public ResponseEntity<String> handleUnsupportedOperation(EnumConstantNotPresentException ex, WebRequest request) {
        if (ex.enumType().equals(Entities.class))
            return new ResponseEntity<>(getMessage(ErrorCodes.NoSuchEntity.name()), HttpStatus.BAD_REQUEST);
        if (ex.enumType().equals(Attributes.class))
            return new ResponseEntity<>(getMessage(ErrorCodes.NoSuchValue.name()), HttpStatus.BAD_REQUEST);
        else throw new RuntimeException();
    }

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<String> handleConversionException(Exception ex, WebRequest request) {
        var message = service.getMessage(ErrorCodes.ConversionError.name());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJSONError(Exception ex, WebRequest request) {
        var message = service.getMessage(ErrorCodes.JSONError.name());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
