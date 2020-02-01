package StockExchange.StockExchange.services;

import StockExchange.StockExchange.entities.Offer;
import StockExchange.StockExchange.entities.StockTransaction;
import StockExchange.StockExchange.entities.Trader;
import StockExchange.StockExchange.repositories.ShareRepository;
import StockExchange.StockExchange.repositories.TraderRepository;
import StockExchange.StockExchange.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TranscationService {

    private final TransactionRepository transactionRepository;
    private final ShareRepository shareRepository;
    private final TraderRepository traderRepository;

    @Autowired
    public TranscationService(TransactionRepository transactionRepository, ShareRepository shareRepository, TraderRepository traderRepository) {
        this.transactionRepository = transactionRepository;
        this.shareRepository = shareRepository;
        this.traderRepository = traderRepository;
    }

    public List<StockTransaction> findAllByShareId(long id) {
        return transactionRepository.findAllByShareId(id);
    }

    public List<StockTransaction> findAllByBuyerId(long id) {
        return transactionRepository.findAllByBuyerId(id);
    }

    public List<StockTransaction> findAllBySellerId(long id) {
        return transactionRepository.findAllBySellerId(id);
    }

    @Transactional
    StockTransaction createTransaction(Offer offer, Trader buyer){
        var transaction = new StockTransaction(offer,buyer);
        shareRepository.save(offer.getShare());
        traderRepository.save(buyer);
        return transactionRepository.save(transaction);
    }


}
