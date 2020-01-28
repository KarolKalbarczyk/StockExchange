package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Response;
import StockExchange.StockExchange.Repositories.AccountRepository;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service("ResponseService")
public class ResponseService {

    private final Response DEFAULT_RESPONSE = new Response("default");
    private final String NOTIFICATION_NAME = "name";
    private final ResponseRepository responseRepository;
    private final OfferRepository offerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository,
                           OfferRepository offerRepository,
                           AccountRepository accountRepository) {
        this.responseRepository = responseRepository;
        this.offerRepository = offerRepository;
        this.accountRepository = accountRepository;
    }

    public Response getMessage(String name) {
        var locale = LocaleContextHolder.getLocale();
        var response = responseRepository.findOneByNameAndLocale(name, locale);
        return response.orElse(DEFAULT_RESPONSE);
    }

    @Transactional
    String getTranscationNotification(Principal login, long offerId){
        var offer = offerRepository.findOneById(offerId)
                .orElseThrow(IllegalArgumentException::new);
        var text = getMessage(NOTIFICATION_NAME).getText();
        return String.format(text,login.getName(),offer.getId(),offer.getCost());
    }
}
