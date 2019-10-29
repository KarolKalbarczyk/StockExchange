package StockExchange.StockExchange;

import StockExchange.StockExchange.Money.MoneyFactory;
import StockExchange.StockExchange.Repositories.*;
import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.Services.ShareService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Basic;

import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@SpringBootTest()
public class IntegrationShareServiceTest {


    @Autowired
    ShareRepository shareRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    TraderRepository traderRepository;
    int test = 0;
    @MockBean
    ResponseService responseService;
    @MockBean
    TransactionRepository transactionRepository;
    @Autowired
    @InjectMocks
    ShareService shareService;

    long idshare = 7;
    long idoffer = 4;
    long idcompany = 3;
    long idcompany2 = 6;
    long idperson1 = 2;
    long idperson2 = 5;


    @BeforeAll
    public static void init(){
        MockitoAnnotations.initMocks(IntegrationShareServiceTest.class);
    }

    @org.junit.jupiter.api.Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testHavingOffer(){
        Assertions.assertTrue(shareService.hasOffer(idshare));
    }

    @org.junit.jupiter.api.Test
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testNotHavingOffer(){
        Assertions.assertFalse(shareService.hasOffer(idshare));
    }

    @Test
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testMoneyExchangeWhenNotEnoughMoney(){
        Mockito.when(responseService.getMessage("noMoney")).thenReturn("noMoney");
        var trader1 = traderRepository.findOneById(idperson1);
        var trader2 = traderRepository.findOneById(idperson2);
        var money = MoneyFactory.getMoney(50);
        Assertions.assertThrows(IllegalCallerException.class,()-> shareService.exchangeMoney(trader2,trader1,money));
    }


    @Test
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testMoneyExchangeWhenEnoughMoney(){
        Mockito.when(responseService.getMessage("noMoney")).thenReturn("noMoney");
        var trader1 = traderRepository.findOneById(idperson1);
        var trader2 = traderRepository.findOneById(idperson2);
        var moneya = trader1.getWealth();
        var money = MoneyFactory.getMoney(50);
        var money2 = MoneyFactory.getMoney(10);
        var money3 = MoneyFactory.getMoney(90);
        shareService.exchangeMoney(trader1,trader2,money);
        Assertions.assertTrue(trader1.getWealth().compareTo(money2) == 0 );
        Assertions.assertTrue(trader2.getWealth().compareTo(money3) == 0 );
    }

    @Test
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreatingShareWhenNotCompany(){
        Mockito.when(responseService.getMessage("noMoney")).thenReturn("notCompany");
        var trader1 = traderRepository.findOneById(idperson1);
        Assertions.assertThrows(IllegalCallerException.class,() ->shareService.createShareAndOfferIfCompany(trader1.getName(),50));
    }

    @Test
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferAndShareCreation.sql","/revertOfferNotPresent.sql",},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreatingShareWhenCompany(){
        Mockito.when(responseService.getMessage("noMoney")).thenReturn("notCompany");
        var trader1 = traderRepository.findOneById(idcompany2);
        shareService.createShareAndOfferIfCompany(trader1.getName(),50);
        var shares = shareRepository.findAllByOwner(trader1);
        Assertions.assertFalse(shares.isEmpty());
        var optionalOffer = offerRepository.findOneByShare(shares.get(0));
        Assertions.assertTrue(optionalOffer.isPresent());
        var offer = optionalOffer.get();
        var money = MoneyFactory.getMoney(50);
        Assertions.assertEquals(offer.getCost(),money);
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRevokingShareWhenNotOwner(){
        Mockito.when(responseService.getMessage("notOwner")).thenReturn("notOwner");
        Assertions.assertThrows(IllegalCallerException.class,() -> shareService.revokeShare("acc2",idshare));
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRevokingShareWhenOwnerButNotCompany(){
        Mockito.when(responseService.getMessage("notOwner")).thenReturn("notOwner");
        Assertions.assertThrows(IllegalCallerException.class,() -> shareService.revokeShare("acc1",idshare));
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRevokingShareWhenCompanyButNotOwner(){
        Mockito.when(responseService.getMessage("notOwner")).thenReturn("notOwner");
        Assertions.assertThrows(IllegalCallerException.class,() -> shareService.revokeShare("acc0",idshare));
    }

    @Test
    @Sql(scripts = {"/shareInHandsOfCompany.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRevokingShareWhenCompanyAndOwner(){
        Mockito.when(responseService.getMessage("notOwner")).thenReturn("notOwner");
        var trader = traderRepository.findOneById(idcompany);
        shareService.revokeShare("acc0",idshare);
        var shares = shareRepository.findAllByOwner(trader);
        var share = shareRepository.findOneById(idshare);
        Assertions.assertTrue(shares.isEmpty());
        Assertions.assertTrue(share.isEmpty());
        var offer = offerRepository.findOneById(idoffer);
        Assertions.assertTrue(offer.isEmpty());
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testExchangingShare(){
        shareService.exchangeShare(idoffer,"acc1");
        var trader = traderRepository.findOneByAccountLogin("acc2");
        var trader2 = traderRepository.findOneByAccountLogin("acc1");
        var shares1 = shareRepository.findAllByOwner(trader);
        var shares2 = shareRepository.findAllByOwner(trader2);
        Assertions.assertTrue(shares1.isEmpty());
        Assertions.assertFalse(shares2.isEmpty());
        var share =  shareRepository.findOneById(idshare).get();
        Assertions.assertEquals(share.getOwner(),trader2);
        Assertions.assertEquals(share.getOffer(),null);
        var ofer = offerRepository.findOneByShare(share);
        Assertions.assertTrue(ofer.isEmpty());
        var offer = offerRepository.findOneById(idoffer);
        Assertions.assertTrue(offer.isEmpty());
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testExchangingShareWhenYoureTheOwner(){
        Assertions.assertThrows(IllegalCallerException.class, ()->shareService.exchangeShare(idoffer,"acc2"));
    }

}
