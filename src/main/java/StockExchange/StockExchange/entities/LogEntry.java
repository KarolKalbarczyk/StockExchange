package StockExchange.StockExchange.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogEntry extends BasicEntity {

    @CreationTimestamp
    private LocalDateTime timeStamp;
    private String responseName;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public LogEntry() {
    }

    public LogEntry(String responseName, Account account) {
        this.responseName = responseName;
        this.account = account;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
