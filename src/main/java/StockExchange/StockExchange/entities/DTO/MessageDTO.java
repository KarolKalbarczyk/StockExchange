package StockExchange.StockExchange.entities.DTO;

import StockExchange.StockExchange.entities.Account;
import StockExchange.StockExchange.entities.Message;
import java.lang.String;
import java.util.Calendar;

public class MessageDTO {
    private Account receiver;

    private Calendar creationTime;

    private Account sender;

    private String text;

    private String title;

    public MessageDTO(Message Message) {
        this.receiver = Message.getReceiver();
        this.creationTime = Message.getCreationTime();
        this.sender = Message.getSender();
        this.text = Message.getText();
        this.title = Message.getTitle();
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setCreationTime(Calendar creationTime) {
        this.creationTime = creationTime;
    }

    public Calendar getCreationTime() {
        return creationTime;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getSender() {
        return sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Message getEntity() {
        var Message = new Message();
        Message.setReceiver(receiver);
        Message.setCreationTime(creationTime);
        Message.setSender(sender);
        Message.setText(text);
        Message.setTitle(title);
        return Message;
    }
}
