package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Message;
import StockExchange.StockExchange.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageService extends MainService {


    public void createMessage(long senderId,
                              long receiverId,String text, String title){
        var sender = accountRepository.getOne(senderId);
        var receiver = accountRepository.getOne(receiverId);
        var message = new Message(title,text,sender,receiver);
        messageRepository.save(message);
        AtomicInteger a = new AtomicInteger(0);
    }



}
