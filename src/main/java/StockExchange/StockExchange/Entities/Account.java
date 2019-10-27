package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Entities.BasicEntity;
import StockExchange.StockExchange.Entities.Trader;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "account_")
public class Account extends BasicEntity {
    private String login;
    private String password;
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
    mappedBy = "account")
    private Trader account;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "receiver")
    private List<Message> receivedMessages = new ArrayList<>();

    public Account(String login) {
        this.login = login;
    }

    public Account(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Trader getAccount() {
        return account;
    }

    public void setAccount(Trader account) {
        this.account = account;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

}
