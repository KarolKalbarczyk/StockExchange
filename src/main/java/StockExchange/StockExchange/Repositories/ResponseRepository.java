package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Locale;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    Response findOneByNameAndLocale(String name, Locale locale);
}
