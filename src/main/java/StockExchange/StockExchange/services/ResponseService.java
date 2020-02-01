package StockExchange.StockExchange.services;

import StockExchange.StockExchange.entities.Response;
import StockExchange.StockExchange.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service("ResponseService")
public class ResponseService {

    private final String DEFAULT_MESSAGE = "default";
    private final ResponseRepository repository;

    @Autowired
    public ResponseService(ResponseRepository repository) {
        this.repository = repository;
    }

    public String getMessage(String name) {
        var locale = LocaleContextHolder.getLocale();
        var response = repository.findOneByNameAndLocale(name, locale);
        return response.map(Response::getText)
                .orElse(DEFAULT_MESSAGE);
    }
}
