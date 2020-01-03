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
        var optShare = main.shareRepository.findOneById(shareId);
        var trader = main.traderRepository.findOneByAccountLogin(accountLogin);
        optShare.filter(share -> share.isOwner(trader)).ifPresentOrElse(
                (share) -> main.offerRepository.save(new Offer(cost,share,trader)),
                () -> {throw new IllegalCallerException(ErrorCodes.NotOwnedShare.name());});
    }

    @Transactional
    public void revokeOffer(long offerId, String accountLogin){
        possesionCheck(offerId,accountLogin,(offer)-> main.offerRepository.delete(offer), ErrorCodes.NotOwner.name());
    }

    @Transactional
    public void modifyOffer(long offerId, String accountLogin, int newCost){
        possesionCheck(offerId,accountLogin,(offer)-> offer.setCost(newCost), ErrorCodes.NotOwner.name());
    }

    @Transactional
    public void possesionCheck(long offerId, String accountLogin
            , Consumer<Offer> run, String errorMessage){
        var trader = main.traderRepository.findOneByAccountLogin(accountLogin);
        var optOffer = main.offerRepository.findOneById(offerId);
        optOffer.filter(offer -> offer.isOwner(trader)).
                ifPresentOrElse(run, () -> {throw new IllegalCallerException(errorMessage);});
    }
}
