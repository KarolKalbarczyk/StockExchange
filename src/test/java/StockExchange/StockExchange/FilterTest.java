package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.Filter;
import StockExchange.StockExchange.Controllers.FilterController;
import StockExchange.StockExchange.Controllers.OfferController;
import StockExchange.StockExchange.Controllers.StringToOfferFilters;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Entities.Trader_;
import StockExchange.StockExchange.Repositories.ResponseRepository;
import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.StringCriteria.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringJUnit4ClassRunner.class)
public class FilterTest {

    Filter filter1;
    Filter filter2;
    Filter filter3;
    String name = "name";
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Offer_.cost = new MockAttribute<>("cost");
        Offer_.share = new MockAttribute<>("share");
        Offer_.owner = new MockAttribute<>("owner");
        Trader_.wealth = new MockAttribute<>("wealth");
        Trader_.name = new MockAttribute<>("name");
        Trader_.offers = new MockCollectionAttribute("offers");
        filter1 = new Filter("Trader", List.of(), Map.of("wealth", new double[]{5, 10}),Map.of("name",name));
        filter2 = new Filter("Offer",List.of(filter1),Map.of("cost",new double[]{4,8}),Map.of());
        filter3 = new Filter("Trader", List.of(filter2), Map.of("wealth", new double[]{5, 10}),Map.of("name",name));
    }
    @Test
    public void testSingleFilterAndCriteriaEquivalence(){
        TraderCriteria builder = new TraderCriteria();
        var list = List.of(builder.wealthInRange(5,10),builder.nameEquals(name));
        var list2 = list.stream().map(Criteria::where).collect(Collectors.toList());
        var filterList = filter1.buildCriteria().stream().map(Criteria::where).collect(Collectors.toList());
        Assert.assertEquals(list2,filterList);
    }

    @Test
    public void testMultipleFilterAndCriteriaEquivalence(){
        TraderCriteria builder = new TraderCriteria();
        OfferCriteria offer = new OfferCriteria();
        var list = List.of(offer.costInRange(4,8),offer.joinTrader(builder.wealthInRange(5,10),builder.nameEquals(name)));
        var list2 = list.stream().map(Criteria::where).collect(Collectors.toList());
        var filterList = filter2.buildCriteria().stream().map(Criteria::where).collect(Collectors.toList());
        Assert.assertEquals(list2,filterList);
    }

    @Test
    public void testExclusionOfRepeatedNestedFilters(){
        TraderCriteria builder = new TraderCriteria();
        OfferCriteria offer = new OfferCriteria();
        var list = List.of(builder.wealthInRange(5,10),builder.nameEquals(name),builder.joinOffer(offer.costInRange(4,8)));
        var list2 = list.stream().map(Criteria::where).collect(Collectors.toList());
        var filterList = filter3.buildCriteria().stream().map(Criteria::where).collect(Collectors.toList());
        Assert.assertEquals(list2,filterList);
    }

    @Test
    public void test(){

    }



}

