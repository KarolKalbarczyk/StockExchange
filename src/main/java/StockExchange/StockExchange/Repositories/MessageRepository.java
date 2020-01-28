package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Account;
import StockExchange.StockExchange.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository  extends JpaRepository<Message,Long> {

}
