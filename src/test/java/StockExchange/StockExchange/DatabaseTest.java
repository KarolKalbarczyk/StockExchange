package StockExchange.StockExchange;

import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Services.ShareService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class DatabaseTest {
    @Autowired
    TestEntityManager manager;
    @Mock
    OfferRepository offerRepository2;
    @InjectMocks
    ShareService shareService;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    OfferRepository offerRepository;

    Resource testOfferCreation;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Sql("/offerNotPresent.sql")
    public void testNotHavingOffer(){
        var share = shareRepository.getOne(1l);
        Mockito.when(offerRepository2.findOneByShare(share)).thenReturn(offerRepository.findOneByShare(share));
        Assert.assertFalse(shareService.hasOffer(1l));
    }

}
