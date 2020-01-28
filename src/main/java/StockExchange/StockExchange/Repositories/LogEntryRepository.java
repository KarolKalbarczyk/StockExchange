package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry,Long> {

    List<LogEntry> findAllByAccountId(long accountId);
}
