package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Person;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Money.MoneyFactory;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class OfferService extends MainService{

    private final String notOwner = "notOwner";
    private final String notOwnerShare = "notOwnerShare";

    @Transactional
    public void createOffer(long shareId, String accountLogin, int cost){
        var share = shareRepository.getOne(shareId);
        var trader = traderRepository.findOneByAccountLogin(accountLogin);
        if (share.getOwner().equals(trader)) {
            var offer = new Offer(MoneyFactory.getMoney(cost), share, trader);
            offerRepository.save(offer);
        }else throw new IllegalCallerException(notOwnerShare);
    }

    @Transactional
    public void revokeOffer(long offerId, String accountLogin){
        possesionCheck(offerId,accountLogin,(offer)-> offerRepository.delete(offer),notOwner);
    }

    @Transactional
    public void modifyOffer(long offerId, String accountLogin, int newCost){
        possesionCheck(offerId,accountLogin,(offer)-> {offer.setCost(MoneyFactory.getMoney(newCost));
        offerRepository.save(offer);},notOwner);
    }

    @Transactional
    public void possesionCheck(long offerId, String accountLogin
            , Consumer<Offer> run, String errorMessage){
        var trader = traderRepository.findOneByAccountLogin(accountLogin);
        var offer = offerRepository.findOneById(offerId);
        if (offer.getOwner().equals(trader)){
            run.accept(offer);
        }else throw new IllegalCallerException(notOwner);
    }
}
