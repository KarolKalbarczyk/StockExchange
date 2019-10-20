package StockExchange.StockExchange;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import org.hibernate.jpa.AvailableSettings;
import org.hibernate.jpamodelgen.xml.jaxb.Persistence;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StockExchangeApplication {

	public static void main(String[] args) {

        DispatcherServlet a = new DispatcherServlet();
	    SpringApplication.run(StockExchangeApplication.class, args);
	}
}




