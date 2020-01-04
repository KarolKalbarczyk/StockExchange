package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.Repositories.*;
import StockExchange.StockExchange.Services.OfferService;
import StockExchange.StockExchange.Services.ShareService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegrationStockTranscationTest {

    @Autowired
    ShareService shareService;

    @Autowired
    OfferService offerService;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TranscationService transcationService;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    TraderRepository traderRepository;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void reset(){
        transactionRepository.deleteAll();
        shareRepository.deleteAll();
        offerRepository.deleteAll();

    }
    @Test()
    @Transactional
    public void testTranscationCreationWhenExchangingShare(){
        var login = "a";
        var acc = accountRepository.save(new Account(login));
        var buyer = traderRepository.save(new Person(acc));
        var seller = traderRepository.save(new Company(10));
        var share = shareRepository.save(new Share(seller));
        var offer = offerRepository.save(new Offer(0,share,seller));
        //var trans = transactionRepository.save(new StockTransaction(offer,buyer));

        //var transcation = transactionRepository.findAllByShareId(share.getId());
        shareService.exchangeShare(offer.getId(),login);
        assertThat(transactionRepository.count()).isEqualTo(1);
        //assertThat(traderRepository.getOne(buyer.getId()).)
        var share1 = shareRepository.findOneById(share.getId()).get();
        assertThat(share1.getTransactions()).hasSize(1);
    }
}
