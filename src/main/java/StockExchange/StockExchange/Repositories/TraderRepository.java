package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraderRepository  extends JpaRepository<Trader,Long>, JpaSpecificationExecutor<Trader> {
    Trader findOneByName(String name);
    Trader findOneByAccountLogin(String login);
    Trader findOneById(long id);
}
