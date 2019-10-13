package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class ShareService {
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @PersistenceContext
    EntityManager manager;

    @Transactional
    public void exchangeShare(long shareId, long offerId, long buyerId){
        var offer = offerRepository.getOne(offerId);
        var buyer = traderRepository.getOne(buyerId);
        var share = shareRepository.getOne(shareId);
        exchangeShare(share,offer,buyer);
    }

    @Transactional
    private void exchangeShare(Share share,Offer offer,Trader buyer){
        exchangeMoney(buyer,offer.getOwner(),offer.getCost());
        createTransaction(share,offer,buyer);
        share.setOwner(buyer);
        offerRepository.delete(offer);
        offer.setOwner(null);
        share.setOffer(null);
    }

    public boolean hasOffer(long shareId){
        var share = shareRepository.getOne(shareId);
        var offer = offerRepository.findOneByShare(share);
        return offer.isPresent();
    }

    public void exchangeMoney(Trader buyer, Trader seller, BigDecimal money){
        if(buyer.getWealth().compareTo(money) >= 0) throw new IllegalCallerException("not enough money for this transcation");
        seller.changeWealth( money);
        buyer.changeWealth(money.negate());
    }

    private void createTransaction(Share share, Offer offer, Trader buyer){
        var transaction = new StockTransaction(share,offer,buyer);
        transactionRepository.save(transaction);
    }

    @Transactional
    public Share createShare(long companyId){
        var company = manager.getReference(Company.class,companyId);
        var share = new Share(company);
        shareRepository.save(share);
        return share;
    }

  /*  @Transactional
    public void revokeShare(long companyId, long shareId) throws IllegalAccessException{
        var company = traderRepository.getOne(companyId);
        var share = shareRepository.findAndJoinOwner(shareId);
        if(!share.getOwner().equals(company)){
            throw new IllegalAccessException("you can't revoke share that you do not own");
        }
        share.setOwner(null);
        share.setCompany(null);
    }*/




}
