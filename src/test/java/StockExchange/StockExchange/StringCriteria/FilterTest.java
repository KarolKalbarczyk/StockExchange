package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Entities.Trader_;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest()
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
        filter1 = new Filter(Entities.Trader, List.of(), new EnumMap<>(Map.of(Attributes.Wealth, new double[]{5, 10})),new EnumMap<>(Map.of(Attributes.Name,name)));
        filter2 = new Filter(Entities.Offer,List.of(filter1),new EnumMap<>(Map.of(Attributes.Cost,new double[]{4,8})),new EnumMap<>(Attributes.class));
        filter3 = new Filter(Entities.Trader, List.of(filter2), new EnumMap<>(Map.of(Attributes.Wealth, new double[]{5, 10})),new EnumMap<>(Map.of(Attributes.Name,name)));
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
