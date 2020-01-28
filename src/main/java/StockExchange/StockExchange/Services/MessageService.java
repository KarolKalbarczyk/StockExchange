package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.Account;
import StockExchange.StockExchange.Entities.DTO.MessageDTO;
import StockExchange.StockExchange.Entities.Message;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageService {

    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;
    private final ResponseService responseService;
    private final OfferRepository offerRepository;
    private final Account AUTOMAT = new Account();
    private final String NOTIFICATION_TITLE = "title";

    @Autowired
    public MessageService(AccountRepository accountRepository,
                          MessageRepository messageRepository,
                          ResponseService responseService,
                          OfferRepository offerRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
        this.responseService = responseService;
        this.offerRepository = offerRepository;
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

    @Transactional
    public MessageDTO getTranscationNotification(Principal login, long offerId) {
        var text = responseService.getTranscationNotification(login, offerId);
        var seller = offerRepository.findOneById(offerId)
                .map(Offer::getOwner)
                .orElseThrow(IllegalArgumentException::new);
        var message = new Message(NOTIFICATION_TITLE, text, AUTOMAT, seller.getAccount());
        return new MessageDTO(message);
    }

    public void saveMessage(MessageDTO messageDTO){
        messageRepository.save(messageDTO.getEntity());
    }

}
