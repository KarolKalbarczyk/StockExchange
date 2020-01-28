package StockExchange.StockExchange.aspect;

import StockExchange.StockExchange.Entities.Response;
import StockExchange.StockExchange.Services.LogEntryService;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Aspect
@Component
public class LogAspect {


    private final LogEntryService logEntryService;

    @Autowired
    public LogAspect(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @Pointcut("within(StockExchange.StockExchange.Controllers.*) && args(..,principal)")
    private void controllerMethod(Principal principal){};

    @AfterReturning(pointcut = "controllerMethod(principal)",
                    returning = "respEntity")
    public void log(ResponseEntity<Response> respEntity, Principal principal){
        logEntryService.createEntry(respEntity.getBody(),principal);
    }

    @AfterThrowing(pointcut = "controllerMethod(principal)",
                   throwing = "exception")
    public void logError(Exception exception, Principal principal){
        logEntryService.createEntry(exception.getMessage(),principal);
    }
}
