package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.LogEntry;
import StockExchange.StockExchange.Entities.Response;
import StockExchange.StockExchange.Repositories.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class LogEntryService {

    private LogEntryRepository logEntryRepository;
    private AccountService accountService;

    @Autowired
    public LogEntryService(LogEntryRepository logEntryrepository, AccountService accountService) {
        this.logEntryRepository = logEntryrepository;
        this.accountService = accountService;
    }

    public LogEntry createEntry(Response response, Principal principal) {
        return createEntry(response.getName(), principal);
    }

    public LogEntry createEntry(String responseName, Principal principal) {
        var account = accountService.getByLogin(principal.getName());
        var entry = new LogEntry(responseName, account);
        return logEntryRepository.save(entry);
    }
}