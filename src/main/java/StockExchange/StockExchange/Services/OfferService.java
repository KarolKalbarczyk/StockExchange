package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class OfferService{

    private final String NOT_OWNER = "NOT_OWNER";
    private final String NOT_OWNED_SHARE = "NOT_OWNED_SHARE";
    private final String NOT_LOGGED = "notLogged";

    @Autowired
    MainService main;

    @Transactional
    public void createOffer(long shareId, String accountLogin, int cost){
        var share = main.shareRepository.getOne(shareId);
        var trader = main.traderRepository.findOneByAccountLogin(accountLogin);
        if (share.getOwner().equals(trader)) {
            var offer = new Offer(MoneyFactory.getMoney(cost), share, trader);
            main.offerRepository.save(offer);
        }else throw new IllegalCallerException(NOT_OWNED_SHARE);
    }

    @Transactional
    public void revokeOffer(long offerId, String accountLogin){
        possesionCheck(offerId,accountLogin,(offer)-> main.offerRepository.delete(offer), NOT_OWNER);
    }

    @Transactional
    public void modifyOffer(long offerId, String accountLogin, int newCost){
        possesionCheck(offerId,accountLogin,(offer)-> {offer.setCost(MoneyFactory.getMoney(newCost));
        main.offerRepository.save(offer);}, NOT_OWNER);
    }

    @Transactional
    public void possesionCheck(long offerId, String accountLogin
            , Consumer<Offer> run, String errorMessage){
        var trader = main.traderRepository.findOneByAccountLogin(accountLogin);
        var optOffer = main.offerRepository.findOneById(offerId);
        optOffer.ifPresent((offer) -> {
        { if (offer.getOwner().equals(trader)) {
                run.accept(offer);
            } else throw new IllegalCallerException(NOT_OWNER); }
    });
    }
}
