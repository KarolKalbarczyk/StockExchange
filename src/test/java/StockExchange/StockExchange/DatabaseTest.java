package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.Company;
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
    @InjectMocks
    ShareService shareService;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    OfferRepository offerRepository;

    Resource testOfferCreation;

    long shareid = 7;
    long offerid = 4;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.jupiter.api.Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRemovingShareRemovesOffer(){
        var share = shareRepository.findOneById(shareid);
        shareRepository.delete(share);
        var offer = offerRepository.findOneById(offerid);
        Assert.assertNull(offer);
    }

    @org.junit.jupiter.api.Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRemovingOfferDoesntRemoveShare(){
        var offer = offerRepository.findOneById(offerid);
        offerRepository.delete(offer);
        var share = shareRepository.findOneById(shareid);
        Assert.assertNotNull(share);
    }


    @org.junit.jupiter.api.Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRemovingOffer(){
        offerRepository.deleteById(offerid);
        var offer = offerRepository.findOneById(offerid);
        Assert.assertNotNull(offer);
    }


}
