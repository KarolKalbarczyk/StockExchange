package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.Filter;
import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.StringCriteria.*;
import org.hibernate.query.internal.QueryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
import org.springframework.data.jpa.util.BeanDefinitionUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { PersistenConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@Sql(scripts = {"/CriteriaTest.sql"})
public class CriteriaTest {

    int offercost60id = 2;
    int offercost70id = 3;
    int offercost50id = 1;
    int offercost40id = 0;

    @PersistenceContext
    EntityManager manager;
    QueryConstructorImpl constructor;
    OfferCriteria crit = new OfferCriteria();
    ShareCriteria share = new ShareCriteria();
    TraderCriteria trader = new TraderCriteria();
    AccountCriteria account = new AccountCriteria();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        constructor = new QueryConstructorImpl();
        Offer_.cost = new MockAttribute<>("cost");
        Offer_.share = new MockAttribute<>("share");
        Trader_.wealth = new MockAttribute<>("wealth");
        Trader_.name = new MockAttribute<>("name");
        Trader_.account = new MockAttribute<>("account");
        Account_.login = new MockAttribute<>("login");
    }

    @Test
    public void testSingleCriteria(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60));
        var list = manager.createQuery(query).getResultList();
        Assert.assertTrue(list.size()==3);
        var offerList = getOfferCollection(40,offercost40id,50,offercost50id,60,offercost60id);
        Assert.assertTrue(list.containsAll(offerList));
    }

    @Test
    public void testJoin(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60),crit.joinShare(share.testcheck(7,10)));
        var list = manager.createQuery(query).getResultList();
        Assert.assertTrue(list.size()  == 2);
        var offerList = getOfferCollection(40,offercost40id,50,offercost50id);
        Assert.assertTrue(list.containsAll(offerList));
    }

    @Test
    public void testMultipleJoin(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60),
                crit.joinShare(share.testcheck(7,10)),crit.joinTrader(trader.wealthInRange(4,10),trader.nameEquals("comp0")));
        var list = manager.createQuery(query).getResultList();
        Assert.assertTrue(list.size()  == 1);
        var offerList = getOfferCollection(40,offercost40id);
        Assert.assertTrue(list.containsAll(offerList));
    }

    @Test
    public void testNestedJoins(){
        String query = constructor.createQuery(Offer.class,crit.costInRange(40,60),
                crit.joinShare(share.testcheck(7,10)),crit.joinTrader(trader.wealthInRange(4,10),trader.nameEquals("comp0"),trader.joinAccount(account.loginEquals("login0"))));
        String query1 = "select offer from Offer offer join offer.share share join offer.owner trader join trader.account account where offer.cost between 40.00 and 60.00 and share.test between 7.00 and 10.00 and trader.wealth between 4.00 and 10.00 and trader.name = 'comp0' and account.login = 'login0'";
        var list = manager.createQuery(query1).getResultList();
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
