package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.Filter;
import StockExchange.StockExchange.Controllers.FilterController;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.StringCriteria.Criteria;
import StockExchange.StockExchange.StringCriteria.GenericCriteria;
import StockExchange.StockExchange.StringCriteria.QueryConstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FilterController.class)
public class FilterControllerTest {

    @MockBean
    QueryConstructor constructor;
    @Autowired
    @InjectMocks
    FilterController controller;
    MockMvc mockMvc;
    Filter filter1;
    Filter filter2;
    Filter filter3;
    String json1;
    String json2;
    String json3;
    @Captor
    private ArgumentCaptor<Criteria[]> registerMessageLambdaCaptor;

    @Before
    public void init() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ObjectMapper mapper = new ObjectMapper();
        filter1 = new Filter("Trader", List.of(), Map.of("wealth", new double[]{5, 10}),Map.of("name","name1"));
        filter2 = new Filter("Offer",List.of(filter1),Map.of("cost",new double[]{4,8,3}),Map.of());
        filter3 = new Filter("Trader", List.of(filter2), Map.of("wealth", new double[]{5, 10}),Map.of("name","name2"));
        json1 =mapper.writeValueAsString(filter1);
        json2 = mapper.writeValueAsString(filter2);
        json3 = mapper.writeValueAsString(filter3);
    }

    @Test
    public void testFilterValidation() throws Exception{
        mockMvc.perform(post("/filter").
                param("offset","1").param("limit","1").
                content(json2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    public void workingProperly() throws Exception{
        mockMvc.perform(post("/filter").
                param("offset","1").param("limit","1").
                content(json1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        Mockito.verify(constructor).executeQuery(any(),eq(1),eq(1));
        Mockito.verify(constructor).createQuery(eq(Trader.class),any());
    }

    @Test
    public void notWorkingWithWrongLimit() throws Exception{
        Assertions.assertThrows(Exception.class, () ->mockMvc.perform(post("/filter").
                param("offset","1").param("limit","-1").
                content(json1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)));

    }

    @Test
    public void notWorkingWithWrongOffset() throws Exception{
        Assertions.assertThrows(Exception.class, () ->mockMvc.perform(post("/filter").
                param("offset","-1").param("limit","1").
                content(json1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)));
    }
}
