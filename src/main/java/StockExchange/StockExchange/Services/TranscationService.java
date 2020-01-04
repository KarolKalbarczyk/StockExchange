package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.StockTransaction;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Repositories.TransactionRepository;
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
