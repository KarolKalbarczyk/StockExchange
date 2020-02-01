package StockExchange.StockExchange.services;

import StockExchange.StockExchange.entities.Message;
import StockExchange.StockExchange.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository,
                          MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public void createMessage(long senderId,
                              long receiverId,
                              String text,
                              String title) {
        var sender = accountRepository.getOne(senderId);
        var receiver = accountRepository.getOne(receiverId);
        var message = new Message(title, text, sender, receiver);
        messageRepository.save(message);
    }


}
