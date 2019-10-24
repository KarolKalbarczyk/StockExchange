package StockExchange.StockExchange.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<String> handleIllegalCaller(Exception ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }


}
