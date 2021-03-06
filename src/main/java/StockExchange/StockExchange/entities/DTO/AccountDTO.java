package StockExchange.StockExchange.entities.DTO;

import StockExchange.StockExchange.entities.Account;
import StockExchange.StockExchange.entities.Message;
import StockExchange.StockExchange.entities.Trader;
import java.lang.String;
import java.util.List;

public class AccountDTO {
    private String password;

    private List<Message> receivedMessages;

    private String login;

    private List<Message> sentMessages;

    private Trader account;

    public AccountDTO(Account Account) {
        this.password = Account.getPassword();
        this.receivedMessages = Account.getReceivedMessages();
        this.login = Account.getLogin();
        this.sentMessages = Account.getSentMessages();
        this.account = Account.getAccount();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setAccount(Trader account) {
        this.account = account;
    }

    public Trader getAccount() {
        return account;
    }

    public Account getEntity() {
        var Account = new Account();
        Account.setPassword(password);
        Account.setReceivedMessages(receivedMessages);
        Account.setLogin(login);
        Account.setSentMessages(sentMessages);
        Account.setAccount(account);
        return Account;
    }
}
