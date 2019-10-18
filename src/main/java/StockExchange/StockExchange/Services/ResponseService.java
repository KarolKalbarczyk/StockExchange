package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ResponseService {
    @Autowired
    ResponseRepository repository;
    public String getMessage(String name) {
        var locale = LocaleContextHolder.getLocale();
        var response = repository.findOneByNameAndLocale(name,locale);
        return response.getText();
    }
}
