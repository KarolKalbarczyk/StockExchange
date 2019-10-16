package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
