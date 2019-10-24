package StockExchange.StockExchange;

import StockExchange.StockExchange.Controllers.ShareController;
import StockExchange.StockExchange.Services.ShareService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockExchangeApplicationTests {

    @Mock
    ShareService shareService;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    ShareController controller;
    @Mock
    Principal principal;


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void messageSentWithProperLocale(){
        var key = "key";
        var name = "name";
        var message = "good";
        var messageUs = "usgood";
        Mockito.when(LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);
        Mockito.when(messageSource.getMessage(key,null,Locale.ENGLISH)).thenReturn(message);
        Mockito.when(principal.getName()).thenReturn(name);
        var response = controller.runTranscation(1,principal);
        Assert.assertTrue(response.getBody().equals(message));
        Mockito.when(LocaleContextHolder.getLocale()).thenReturn(Locale.US);
        Mockito.when(messageSource.getMessage(key,null,Locale.US)).thenReturn(messageUs);
        Assert.assertTrue(response.getBody().equals(messageUs));
    }

    @Test
    public void caughtErrorFromController(){
        Mockito.when(principal.getName()).thenReturn("name");
        Mockito.when(shareService.createShareAndOfferIfCompany(principal.getName(),50)).
                thenThrow(new IllegalCallerException("error"));
        var response =  controller.createShare(50,principal);
        Assert.assertEquals(response.getBody(),"error");
    }

	@Test
	public void contextLoads() {
	}

}
