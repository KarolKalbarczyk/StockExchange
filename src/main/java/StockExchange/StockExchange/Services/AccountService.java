package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Account;
import StockExchange.StockExchange.Repositories.*;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;

@Service
class AccountService  {

    private AccountRepository accountRepository;
    private final Account ERROR_ACCOUNT = new Account();

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    Account getByLogin(String login){
        return accountRepository.findByLogin(login).orElse(ERROR_ACCOUNT);
    }

    public int getNumberOfUnreadMessages(Principal principal){
        var account = accountRepository.findByLogin(principal.getName())
                .orElseThrow(IllegalCallerException::new);
        return accountRepository.getNumberOfUnreadMessages(account.getId());
    }
}
