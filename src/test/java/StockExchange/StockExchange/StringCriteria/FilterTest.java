package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Entities.Trader_;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilterTest {

    Filter filter1;
    Filter filter2;
    Filter filter3;
    String name = "name";
    @BeforeAll
    public void init(){
        MockitoAnnotations.initMocks(this);
        Offer_.cost = new MockAttribute<>("cost");
        Offer_.share = new MockAttribute<>("share");
        Offer_.owner = new MockAttribute<>("owner");
        Trader_.wealth = new MockAttribute<>("wealth");
        Trader_.name = new MockAttribute<>("name");
        Trader_.offers = new MockCollectionAttribute("offers");
        filter1 = new Filter(Entities.Trader, List.of(), new EnumMap<>(Map.of(Attributes.Wealth, new double[]{5, 10})),new EnumMap<>(Map.of(Attributes.Name,name)));
        filter2 = new Filter(Entities.Offer,List.of(filter1),new EnumMap<>(Map.of(Attributes.Cost,new double[]{4,8})),new EnumMap<>(Attributes.class));
        filter3 = new Filter(Entities.Trader, List.of(filter2), new EnumMap<>(Map.of(Attributes.Wealth, new double[]{5, 10})),new EnumMap<>(Map.of(Attributes.Name,name)));
    }

    @Test
    public void testSingleFilterAndCriteriaEquivalence(){
        TraderCriteriaBuilder builder = new TraderCriteriaBuilder();
        var list = List.of(builder.wealthInRange(5,10),builder.nameEquals(name));
        var filterList = filter1.buildCriteria();
        Assert.assertEquals(list,filterList);
    }

    @Test
    public void testMultipleFilterAndCriteriaEquivalence(){
        TraderCriteriaBuilder builder = new TraderCriteriaBuilder();
        OfferCriteriaBuilder offer = new OfferCriteriaBuilder();
        var list = List.of(offer.costInRange(4,8),offer.joinTrader(builder.wealthInRange(5,10),builder.nameEquals(name)));
        var filterList = filter2.buildCriteria();
        Assert.assertEquals(list,filterList);
    }

    @Test
    public void testExclusionOfRepeatedNestedFilters(){
        TraderCriteriaBuilder builder = new TraderCriteriaBuilder();
        OfferCriteriaBuilder offer = new OfferCriteriaBuilder();
        var list = List.of(builder.wealthInRange(5,10),builder.nameEquals(name),builder.joinOffer(offer.costInRange(4,8)));
        var filterList = filter3.buildCriteria();
        Assert.assertEquals(list,filterList);
    }




}

