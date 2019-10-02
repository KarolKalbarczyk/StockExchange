package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Person;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class OfferService {
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public void createOffer(long shareId, long traderId, int cost){
        var share = shareRepository.getOne(shareId);
        var trader = traderRepository.getOne(traderId);
        var offer = new Offer(cost,share,trader);
        share.setOffer(offer);
        offerRepository.save(offer);
    }
}
