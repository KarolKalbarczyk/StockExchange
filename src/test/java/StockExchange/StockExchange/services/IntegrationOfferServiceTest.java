package StockExchange.StockExchange.services;

import StockExchange.StockExchange.repositories.OfferRepository;
import StockExchange.StockExchange.repositories.ShareRepository;
import StockExchange.StockExchange.stringCriteria.QueryConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegrationOfferServiceTest {

    @Autowired
    OfferService offerService;

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    ShareRepository shareRepository;


    @Autowired
    QueryConstructor constructor;
    long idshare = 7;
    long idoffer = 4;
    long idcompany = 3;
    long idcompany2 = 6;
    long idperson1 = 2;
    long idperson2 = 5;

    @AfterEach
    public void a(){
        offerRepository.deleteAll();
        shareRepository.deleteAll();
    }


    @Test
    @Sql("/offerNotPresent.sql")
    @Sql(scripts = {"/revertOfferCreation.sql","/revertOfferNotPresent.sql" },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreatingOffer(){
        offerService.createOffer(idshare,"acc1",50);
        var offer = offerRepository.findAllByCost(50);
        var share = shareRepository.findOneById(idshare).get();
        var money = 50;
        Assertions.assertEquals(offer.size(),1);
        Assertions.assertEquals(offer.get(0).getCost(),money);
        Assertions.assertNotNull(share.getOffer());
        Assertions.assertEquals(share.getOffer(),offer.get(0));
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testRevokingOffer(){
        offerService.revokeOffer(idoffer,"acc2");
        var offer = offerRepository.findOneById(idoffer);
        Assertions.assertTrue(offer.isEmpty());
        var share = shareRepository.findOneById(idshare).get();
        Assertions.assertNull(share.getOffer());
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testModyfyingOffer(){
        offerService.modifyOffer(idoffer,"acc2",40);
        var offer = offerRepository.findOneById(idoffer).get();
        var money = 40;
        Assertions.assertEquals(offer.getCost(),money);
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testPossesionCheckWhenIsOwner() throws Exception{
        var offer = offerRepository.findOneById(idoffer).get();
        var b = new TestBoolean();
        offerService.possesionCheck(idoffer,"acc2",(offer1) -> {b.a=true;},"error");
        Assertions.assertTrue(b.a);
    }

    @Test
    @Sql(scripts = {"/offerNotPresent.sql","/offerPresent.sql"})
    @Sql(scripts = {"/revertOfferPresent.sql","/revertOfferNotPresent.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testPossesionCheckWhenIsNotOwner() throws Exception{
        var b = new TestBoolean();
        Assertions.assertThrows(IllegalCallerException.class,
                ()->offerService.possesionCheck(idoffer,"acc1",(offer1) -> {},"error"));
        Assertions.assertFalse(b.a);
    }

}


