package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Controllers.ErrorCodes;
import StockExchange.StockExchange.Entities.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class OfferService{

    @Autowired
    MainService main;

    @Transactional
    public void createOffer(long shareId, String accountLogin, int cost){
        var share = main.shareRepository.getOne(shareId);
        var trader = main.traderRepository.findOneByAccountLogin(accountLogin);
        if (share.getOwner().equals(trader)) {
            var offer = new Offer(cost, share, trader);
            main.offerRepository.save(offer);
        }else throw new IllegalCallerException(ErrorCodes.NotOwnedShare.name());
    }

    @Transactional
    public void revokeOffer(long offerId, String accountLogin){
        possesionCheck(offerId,accountLogin,(offer)-> main.offerRepository.delete(offer), ErrorCodes.NotOwner.name());
    }

    @Transactional
    public void modifyOffer(long offerId, String accountLogin, int newCost){
        possesionCheck(offerId,accountLogin,(offer)-> {offer.setCost(newCost);
        main.offerRepository.save(offer);}, ErrorCodes.NotOwner.name());
    }

    @Transactional
    public void possesionCheck(long offerId, String accountLogin
            , Consumer<Offer> run, String errorMessage){
        var trader = main.traderRepository.findOneByAccountLogin(accountLogin);
        var optOffer = main.offerRepository.findOneById(offerId);
        optOffer.ifPresent((offer) -> {
        { if (offer.getOwner().equals(trader)) {
                run.accept(offer);
            } else throw new IllegalCallerException(errorMessage); }
    });
    }
}
