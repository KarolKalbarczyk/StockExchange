package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.Services.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/share")
public class ShareController {
    private final String SUCCESS = "succes";

    @Autowired
    ShareService shareService;
    @Autowired
    ResponseService responseService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyShare(
            @RequestBody long offerId, Principal principal){
        shareService.exchangeShare(offerId,principal.getName());
        var message = responseService.getMessage(SUCCESS);
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createShare(@RequestBody int cost,Principal principal){
        shareService.createShareAndOfferIfCompany(principal.getName(),cost);
        var message = responseService.getMessage(SUCCESS);
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> revokeShare(@RequestBody long id,Principal principal ) throws IllegalAccessException{
        shareService.revokeShare(principal.getName(),id);
        var message = responseService.getMessage(SUCCESS);
        return new ResponseEntity(message,HttpStatus.OK);
    }


}
