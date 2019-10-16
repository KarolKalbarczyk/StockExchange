package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Repositories.ResponseRepository;
import StockExchange.StockExchange.Services.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.security.Principal;

@RestController
@RequestMapping("/transcation")
public class TransactionController {
    private final String SUCCESS = "succes";

    @Autowired
    ShareService shareService;
    @Autowired
    LocaleResolver locale;
    @Autowired
    ResponseRepository responseRepository;

    @PostMapping()
    @ResponseBody
    public ResponseEntity<String> runTranscation(
            @RequestBody long offerId, Principal principal){
        shareService.exchangeShare(offerId,principal.getName());
        var response = responseRepository.findOneByNameAndLanguage(SUCCESS,locale.)
        return ResponseEntity<>(HttpStatus.OK,SUCCESS)
    }
}
