package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.FilterController;
import StockExchange.StockExchange.Controllers.StringToOfferFilters;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Services.OfferService;
import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.StringCriteria.Criteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false,controllers = FilterController.class)
public class ControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Mock
    StringToOfferFilters filters;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void a() throws  Exception{
        Offer_.cost = new MockAttribute<>("cost");
        mockMvc.perform(get("/filter?offer=100,200")).andExpect(content().string("test"));
    }
}
