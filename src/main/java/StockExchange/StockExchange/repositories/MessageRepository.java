package StockExchange.StockExchange.repositories;

import StockExchange.StockExchange.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message,Long> {

}
