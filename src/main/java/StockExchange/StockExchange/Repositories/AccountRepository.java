package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByLogin(String login);

    @Query("select count (m) from Account a join a.receivedMessages m where m.unread = true and a.id = ?1")
    public int getNumberOfUnreadMessages(long accountId);
}
