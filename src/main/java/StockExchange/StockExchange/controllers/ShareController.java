package StockExchange.StockExchange.controllers;

import StockExchange.StockExchange.entities.Response;
import StockExchange.StockExchange.services.MessageService;
import StockExchange.StockExchange.services.ResponseService;
import StockExchange.StockExchange.services.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/share")
public class ShareController {
    private final String BUY_SUCCESS = "succes";
    private final String REVOKE_SUCCESS = "succes";
    private final String CREATE_SUCCESS = "succes";

    private final ShareService shareService;
    private final ResponseService responseService;
    private final MessageService messageService;

    @Autowired
    public ShareController(ShareService shareService,
                           ResponseService responseService,
                           MessageService messageService) {
        this.shareService = shareService;
        this.responseService = responseService;
        this.messageService = messageService;
    }

    @PostMapping("/buy")
    public ResponseEntity<Response> buyShare(
            @RequestBody long offerId, Principal principal) {
        var notification = messageService.getTranscationNotification(principal,offerId);
        shareService.exchangeShare(offerId, principal.getName());
        var message = responseService.getMessage(BUY_SUCCESS);
        messageService.saveMessage(notification);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Response> createShare(@RequestBody int cost, Principal principal) {
        shareService.createShareAndOfferIfCompany(principal.getName(), cost);
        var message = responseService.getMessage(CREATE_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Response> revokeShare(@RequestBody long id, Principal principal) {
        shareService.revokeShare(principal.getName(), id);
        var message = responseService.getMessage(REVOKE_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
