package StockExchange.StockExchange.stringCriteria;

import StockExchange.StockExchange.entities.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.LinkedList;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
public class CriteriaTest {

    int offercost60id = 2;
    int offercost70id = 3;
    int offercost50id = 1;
    int offercost40id = 0;

    @Autowired
    QueryConstructor constructor;
    OfferCriteriaBuilder crit = new OfferCriteriaBuilder();
    ShareCriteriaBuilder share = new ShareCriteriaBuilder();
    TraderCriteriaBuilder trader = new TraderCriteriaBuilder();
    AccountCriteriaBuilder account = new AccountCriteriaBuilder();

    @BeforeAll
    public static void init(){
        Offer_.cost = new MockAttribute<>("cost");
        Offer_.share = new MockAttribute<>("share");
        Trader_.wealth = new MockAttribute<>("wealth");
        Trader_.name = new MockAttribute<>("name");
        Trader_.account = new MockAttribute<>("account");
        Account_.login = new MockAttribute<>("login");
    }

    @Test
    @Sql(scripts = {"/CriteriaTest.sql"})
    @Sql(scripts = {"/revertCriteriaTest.sql" },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSingleCriteria(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60));
        var list = constructor.executeQuery(query,0,0);
        assertThat(list).hasSize(3);
        Assert.assertTrue(list.size()==3);
        var offerList = getOfferCollection(40,offercost40id,50,offercost50id,60,offercost60id);
        Assert.assertTrue(list.containsAll(offerList));
    }

    @Test
    @Sql(scripts = {"/CriteriaTest.sql"})
    @Sql(scripts = {"/revertCriteriaTest.sql" },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testJoin(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60),crit.joinShare(share.testcheck(7,10)));
        var list = constructor.executeQuery(query,0,100);
        assertThat(list).hasSize(2);
        Assert.assertTrue(list.size()  == 2);
        var offerList = getOfferCollection(40,offercost40id,50,offercost50id);
        Assert.assertTrue(list.containsAll(offerList));
    }

    @Test
    @Sql(scripts = {"/CriteriaTest.sql"})
    @Sql(scripts = {"/revertCriteriaTest.sql" },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testMultipleJoin(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60),
                crit.joinShare(share.testcheck(7,10)),crit.joinTrader(trader.wealthInRange(4,10),trader.nameEquals("comp0")));
        var list = constructor.executeQuery(query,0,100);
        assertThat(list).hasSize(1);
        Assert.assertTrue(list.size()  == 1);
        var offerList = getOfferCollection(40,offercost40id);
        Assert.assertTrue(list.containsAll(offerList));
    }

    @Test
    @Sql(scripts = {"/CriteriaTest.sql"})
    @Sql(scripts = {"/revertCriteriaTest.sql" },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testNestedJoins(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60),
                crit.joinShare(share.testcheck(7,10)),crit.joinTrader(trader.wealthInRange(4,10),trader.nameEquals("comp0"),trader.joinAccount(account.loginEquals("login0"))));
        String query1 = "select offer from Offer offer join offer.share share join offer.owner trader join trader.account account where offer.cost between 40.00 and 60.00 and share.test between 7.00 and 10.00 and trader.wealth between 4.00 and 10.00 and trader.name = 'comp0' and account.login = 'login0'";
        var list = constructor.executeQuery(query,0,100);
        assertThat(list).hasSize(1);
        Assert.assertTrue(list.size()  == 1);
        var offerList = getOfferCollection(40,offercost40id);
        Assert.assertTrue(list.containsAll(offerList));
    }



    Collection<Offer> getOfferCollection(int... args){
        var list = new LinkedList<Offer>();
        for (int i = 0; i <args.length ; i += 2) {
            var offer = new Offer();
            offer.setCost(args[i]);
            offer.setId(args[i+1]);
            list.add(offer);
        }
        return list;
    }


}
