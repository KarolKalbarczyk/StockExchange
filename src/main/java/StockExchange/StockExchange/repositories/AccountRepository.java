package StockExchange.StockExchange.repositories;

import StockExchange.StockExchange.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {


}
