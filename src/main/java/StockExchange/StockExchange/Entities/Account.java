package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Entities.BasicEntity;
import StockExchange.StockExchange.Entities.Trader;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Account extends BasicEntity {
    String login;
    String password;
    @OneToOne(fetch = FetchType.LAZY,
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
}
