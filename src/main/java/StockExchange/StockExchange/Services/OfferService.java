package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Controllers.ErrorCodes;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class OfferService{

    private final ShareRepository shareRepository;
    private final TraderRepository traderRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(ShareRepository shareRepository, TraderRepository traderRepository, OfferRepository offerRepository) {
        this.shareRepository = shareRepository;
        this.traderRepository = traderRepository;
        this.offerRepository = offerRepository;
    }

    @Transactional
    public void createOffer(long shareId, String accountLogin, int cost){
        var optShare = shareRepository.findOneById(shareId);
        var trader = traderRepository.findOneByAccountLogin(accountLogin);
        optShare.filter(share -> share.isOwner(trader)).ifPresentOrElse(
                (share) -> offerRepository.save(new Offer(cost,share,trader)),
                () -> {throw new IllegalCallerException(ErrorCodes.NotOwnedShare.name());});
    }

    @Transactional
    public void revokeOffer(long offerId, String accountLogin){
        possesionCheck(offerId,accountLogin,(offer)-> offerRepository.delete(offer), ErrorCodes.NotOwner.name());
    }

    @Transactional
    public void modifyOffer(long offerId, String accountLogin, int newCost){
        possesionCheck(offerId,accountLogin,(offer)-> offer.setCost(newCost), ErrorCodes.NotOwner.name());
    }

    @Transactional
    public void possesionCheck(long offerId, String accountLogin
            , Consumer<Offer> run, String errorMessage){
        var trader = traderRepository.findOneByAccountLogin(accountLogin);
        var optOffer = offerRepository.findOneById(offerId);
        optOffer.filter(offer -> offer.isOwner(trader)).
                ifPresentOrElse(run, () -> {throw new IllegalCallerException(errorMessage);});
    }
}
