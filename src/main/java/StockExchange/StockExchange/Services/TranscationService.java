package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.StockTransaction;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TranscationService {
    @Autowired
    MainService main;


    public List<StockTransaction> findAllByShareId(long id) {
        return main.transactionRepository.findAllByShareId(id);
    }

    public List<StockTransaction> findAllByBuyerId(long id) {
        return main.transactionRepository.findAllByBuyerId(id);
    }

    public List<StockTransaction> findAllBySellerId(long id) {
        return main.transactionRepository.findAllBySellerId(id);
    }

    @Transactional
    StockTransaction createTransaction(Offer offer, Trader buyer){
        var transaction = new StockTransaction(offer,buyer);
        main.shareRepository.save(offer.getShare());
        main.traderRepository.save(buyer);
        return main.transactionRepository.save(transaction);
    }


}
