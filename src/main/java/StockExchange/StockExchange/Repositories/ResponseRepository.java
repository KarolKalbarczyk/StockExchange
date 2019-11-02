package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {
    Optional<Response> findOneByNameAndLocale(String name, Locale locale);
}
