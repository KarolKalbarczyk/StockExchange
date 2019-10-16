package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message,Long> {

}
