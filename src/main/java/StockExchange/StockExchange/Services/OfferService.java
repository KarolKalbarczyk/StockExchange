package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Person;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OfferService extends MainService{

    @Transactional
    public void createOffer(long shareId, String accountLogin, int cost){
        var share = shareRepository.getOne(shareId);
        var trader = traderRepository.findOneByAccountLogin(accountLogin);
        var offer = new Offer(new BigDecimal(cost),share,trader);
        share.setOffer(offer);
        offerRepository.save(offer);
    }
}
