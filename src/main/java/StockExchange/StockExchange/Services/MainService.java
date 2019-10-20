package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MainService {
    @Autowired
    protected ShareRepository shareRepository;
    @Autowired
    protected TraderRepository traderRepository;
    @Autowired
    protected OfferRepository offerRepository;
    @Autowired
    protected TransactionRepository transactionRepository;
    @Autowired
    protected MessageRepository messageRepository;
    @Autowired
    protected AccountRepository accountRepository;
    @PersistenceContext
    protected EntityManager manager;
}