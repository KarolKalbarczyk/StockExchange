package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Services.OfferService;
import StockExchange.StockExchange.Services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class OfferController {
    private final String CREATION_SUCCESS
    @Autowired
    ResponseService responseService;
    @Autowired
    OfferService offerService;

    @PostMapping("/offer")
    public ResponseEntity<String> createOffer(@RequestBody long shareId,
                                              @RequestBody int cost,
                                              Principal principal){
        offerService.createOffer(shareId,principal.getName(),cost);
        var message = responseService.getMessage(CREATION_SUCCESS);
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
