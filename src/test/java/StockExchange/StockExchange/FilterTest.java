package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.FilterController;
import StockExchange.StockExchange.Controllers.OfferController;
import StockExchange.StockExchange.Controllers.StringToOfferFilters;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Repositories.ResponseRepository;
import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.StringCriteria.OfferCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import java.lang.reflect.Member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
public class FilterTest {



    @Test
    public void testConversionFromStringToCriteria(){
        Offer_.cost = new MockAttribute<>("cost");
        var conv = new StringToOfferFilters();
        var list = conv.convert("100,200");
        var s = list.get(0).where();
    }


}

