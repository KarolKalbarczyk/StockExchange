package StockExchange.StockExchange;

import StockExchange.StockExchange.controllers.GlobalExceptionHandler;
import StockExchange.StockExchange.controllers.ShareController;
import StockExchange.StockExchange.repositories.ResponseRepository;
import StockExchange.StockExchange.services.ResponseService;
import StockExchange.StockExchange.services.ShareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.security.Principal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class StockExchangeApplicationTests {

    @Mock
    ShareService shareService;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    ShareController controller;
    @Mock
    Principal principal;

    ResponseService responseService;

    @Mock
    ResponseRepository responseRepository;

    MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        responseService = new ResponseService(responseRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler(responseService))
                .build();
    }

    @Test
    public void caughtErrorFromController()throws Exception{
        Mockito.when(principal.getName()).thenReturn("name");
        Mockito.when(shareService.createShareAndOfferIfCompany(principal.getName(),50)).
                thenThrow(new IllegalCallerException("error"));
        mockMvc.perform(get("/share")).andExpect(status().is4xxClientError());
    }



}
