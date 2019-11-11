package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.Access;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ResponseService service;

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<String> handleIllegalCaller(Exception ex, WebRequest request){
        return handleException(ex,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperation(Exception ex, WebRequest request){
        return handleException(ex,HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> handleException(Exception e,HttpStatus status){
        return new ResponseEntity<>(getMessage(e.getMessage()), status);
    }

    private String getMessage(String code){
        return service.getMessage(code);
    }


}
