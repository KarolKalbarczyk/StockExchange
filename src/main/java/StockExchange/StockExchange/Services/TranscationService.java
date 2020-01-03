package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.StockTransaction;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
