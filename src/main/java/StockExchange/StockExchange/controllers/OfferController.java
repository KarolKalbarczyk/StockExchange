package StockExchange.StockExchange.controllers;

import StockExchange.StockExchange.entities.Response;
import StockExchange.StockExchange.services.OfferService;
import StockExchange.StockExchange.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequestMapping("/offer")
public class OfferController {
    private final String CREATION_SUCCESS = "creationSucces";
    private final String REVOKE_SUCCESS = "creationSucces";
    private final String MODIFY_SUCCESS = "modifySucces";
    private final ResponseService responseService;
    private final OfferService offerService;

    @Autowired
    public OfferController(ResponseService responseService, OfferService offerService) {
        this.responseService = responseService;
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<Response> createOffer(@RequestParam long shareId,
                                                @RequestBody int cost,
                                                Principal principal) {
        offerService.createOffer(shareId, principal.getName(), cost);
        var message = responseService.getMessage(CREATION_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Response> revokeOffer(@RequestBody long offerId,
                                                Principal principal) {
        offerService.revokeOffer(offerId, principal.getName());
        var message = responseService.getMessage(REVOKE_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Response> modifyOffer(@RequestParam long offerId,
                                                @RequestBody int newCost,
                                                Principal principal) {
        offerService.modifyOffer(offerId, principal.getName(), newCost);
        var message = responseService.getMessage(CREATION_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
