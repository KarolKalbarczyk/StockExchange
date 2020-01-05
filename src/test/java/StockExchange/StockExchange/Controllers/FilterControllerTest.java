package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.StringCriteria.Filter;
import StockExchange.StockExchange.Controllers.FilterController;
import StockExchange.StockExchange.Controllers.GlobalExceptionHandler;
import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Services.ResponseService;
import StockExchange.StockExchange.StringCriteria.Criteria;
import StockExchange.StockExchange.StringCriteria.QueryConstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = FilterController.class)
public class FilterControllerTest {

    @MockBean
    ResponseService service;
    @MockBean
    QueryConstructor constructor;
    @Autowired
    @InjectMocks
    FilterController controller;
    @Mock
    GlobalExceptionHandler handler;
    MockMvc mockMvc;
    Filter filter1;
    Filter filter2;
    Filter filter3;
    String json1;
    String json2;
    String json3;
    @Captor
    private ArgumentCaptor<Criteria[]> registerMessageLambdaCaptor;

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(handler).build();
        ObjectMapper mapper = new ObjectMapper();
        filter1 = new Filter(Entities.Trader, List.of(), new EnumMap<>(Map.of(Attributes.Wealth, new double[]{5, 10})),new EnumMap<>(Map.of(Attributes.Name,"name1")));
        filter2 = new Filter(Entities.Offer,List.of(filter1),new EnumMap<>(Map.of(Attributes.Cost,new double[]{4,8,3})),new EnumMap<>(Attributes.class));
        filter3 = new Filter(Entities.Trader, List.of(filter2), new EnumMap<>(Map.of(Attributes.Wealth, new double[]{5, 10})),new EnumMap<>(Map.of(Attributes.Name,"name2")));
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
    public void testConversionError() throws Exception{
        Mockito.when(handler.handleJSONError(any(),any())).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        mockMvc.perform(post("/filter").
                param("offset","1").param("limit","1").
                content("AAAAAAAA").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
        Mockito.verify(handler,times(1)).handleJSONError(any(),any());
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
