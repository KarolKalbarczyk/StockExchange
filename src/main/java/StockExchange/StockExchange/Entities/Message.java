package StockExchange.StockExchange.Entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity(name = "_Message")
public class Message extends BasicEntity {
    @Size(max = 50)
    String title;
    @Lob
    @Size(max = 600)
    String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Account sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Account receiver;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Calendar creationTime;

    public Message() {
    }

    public Message(String title,String text, Account sender, Account receiver) {
        this.title = title;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }
}
