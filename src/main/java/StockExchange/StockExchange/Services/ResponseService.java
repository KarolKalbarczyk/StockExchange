package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Response;
import StockExchange.StockExchange.Repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service("ResponseService")
public class ResponseService {

    private final String DEFAULT_MESSAGE = "default";
    @Autowired
    ResponseRepository repository;
    public String getMessage(String name) {
        var locale = LocaleContextHolder.getLocale();
        var response = repository.findOneByNameAndLocale(name,locale);
        return response.map(Response::getText).orElse(DEFAULT_MESSAGE);
    }
}
