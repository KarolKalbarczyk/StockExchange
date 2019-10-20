package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.Services.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/transcation")
public class StockTransactionController {
    private final String SUCCESS = "succes";

    @Autowired
    ShareService shareService;
    @Autowired
    ResponseService responseService;

    @PostMapping
    public ResponseEntity<String> runTranscation(
            @RequestBody long offerId, Principal principal){
        shareService.exchangeShare(offerId,principal.getName());
        var message = responseService.getMessage(SUCCESS);
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @PostMapping("/aa")
    public ResponseEntity<String> createShare(@RequestBody int cost,Principal principal){
        shareService.createShareIfCompany(principal.getName(),cost);
        var message = responseService.getMessage(SUCCESS);
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @GetMapping
    public Company test(){
        Company company = new Company(10);
        return company;
    }
}
