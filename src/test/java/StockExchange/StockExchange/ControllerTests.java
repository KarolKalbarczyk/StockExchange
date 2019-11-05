package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.FilterController;
import StockExchange.StockExchange.Entities.Offer_;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false,controllers = FilterController.class)
public class ControllerTests {
    @Autowired
    MockMvc mockMvc;

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
