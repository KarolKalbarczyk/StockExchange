package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Message;
import StockExchange.StockExchange.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageService{

    @Autowired
    MainService main;

    public void createMessage(long senderId,
                              long receiverId,String text, String title){
        var sender = main.accountRepository.getOne(senderId);
        var receiver = main.accountRepository.getOne(receiverId);
        var message = new Message(title,text,sender,receiver);
        main.messageRepository.save(message);
    }



}
