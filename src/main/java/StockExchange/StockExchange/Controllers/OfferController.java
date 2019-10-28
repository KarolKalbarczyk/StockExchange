package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Services.OfferService;
import StockExchange.StockExchange.Services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequestMapping("/offer")
public class OfferController {
    private final String CREATION_SUCCESS = "creationSucces";
    private final String REVOKE_SUCCESS = "creationSucces";
    @Autowired
    ResponseService responseService;
    @Autowired
    OfferService offerService;

    @PostMapping
    public ResponseEntity<String> createOffer(@RequestParam long shareId,
                                              @RequestBody int cost,
                                              Principal principal){
        offerService.createOffer(shareId,principal.getName(),cost);
        var message = responseService.getMessage(CREATION_SUCCESS);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> revokeOffer(@RequestBody long offerId,
                                              Principal principal){
        offerService.revokeOffer(offerId,principal.getName());
        var message = responseService.getMessage(REVOKE_SUCCESS);
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
