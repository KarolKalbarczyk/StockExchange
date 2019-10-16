package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Responses.Language;
import StockExchange.StockExchange.Entities.Responses.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    Response findOneByNameAndLanguage(String name, Language language);
}
