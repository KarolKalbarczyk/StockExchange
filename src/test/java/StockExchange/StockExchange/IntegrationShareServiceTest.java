package StockExchange.StockExchange;

import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Services.ShareService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationShareServiceTest {

    @Autowired
    ShareService shareService;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    TraderRepository traderRepository;
    int test = 0;

    @org.junit.jupiter.api.Test
    @Order(2)
    @Sql(scripts = {"/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testHavingOffer(){
        var share = shareRepository.getOne(1l);
        var a= shareService.hasOffer(1l);
        System.out.println("AAA");
        System.out.println(test++);
        Assertions.assertTrue(shareService.hasOffer(1l));
    }

    @org.junit.jupiter.api.Test
    @Order(1)
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testNotHavingOffer(){
        System.out.println("AAA");
        System.out.println(test++);
        var share = shareRepository.getOne(1l);
        Assertions.assertFalse(shareService.hasOffer(1l));
    }


}
