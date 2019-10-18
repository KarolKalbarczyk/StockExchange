package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query(value = "Select account_id from Account where login = ?1",nativeQuery = true)
    public long getTraderId(String login);

}
