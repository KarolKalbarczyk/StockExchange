package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.Money.Money;
import StockExchange.StockExchange.Money.MoneyFactory;
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
public class ShareService extends MainService {

    private String NOT_COMPANY = "notCompany";

    @Transactional
    public void exchangeShare(long offerId, String accountName){
        var offer = offerRepository.getOne(offerId);
        var buyer = traderRepository.findOneByAccountLogin(accountName);
        var share = offer.getShare();
        exchangeShare(share,offer,buyer);
    }

    @Transactional
    private void exchangeShare(Share share,Offer offer,Trader buyer){
        exchangeMoney(buyer,offer.getOwner(),offer.getCost());
        createTransaction(share,offer,buyer);
        share.setOwner(buyer);
        offerRepository.delete(offer);
    }

    public boolean hasOffer(long shareId){
        var share = shareRepository.getOne(shareId);
        var offer = offerRepository.findOneByShare(share);
        return offer.isPresent();
    }

    public void exchangeMoney(Trader buyer, Trader seller, Money money){
        if(buyer.getWealth().compareTo(money) < 0)
            throw new IllegalCallerException("not enough money for this transcation");
        seller.changeWealth( money);
        buyer.get;
    }

    private void createTransaction(Share share, Offer offer, Trader buyer){
        var transaction = new StockTransaction(share,offer,buyer);
        transactionRepository.save(transaction);
    }

    @Transactional
    public Share createShareAndOfferIfCompany(String accountName, int cost){
        var company = traderRepository.findOneByAccountLogin(accountName);
        if(company instanceof Company) return createShareAndOffer((Company) company,cost);
        else throw new IllegalCallerException(responseService.getMessage(NOT_COMPANY));
    }

    private Share createShareAndOffer(Company company,int cost){
        var share = new Share(company);
        Money properCost = MoneyFactory.getMoney(cost);
        var offer = new Offer(properCost,share,company);
        shareRepository.save(share);
        offerRepository.save(offer);
        return share;
    }

    @Transactional
    public void revokeShare(String accountName, long shareId) throws IllegalAccessException{
        var company = traderRepository.findOneByAccountLogin(accountName);
        var share = shareRepository.findOneById(shareId);
        if(!share.getOwner().equals(company)){
            throw new IllegalAccessException("you can't revoke share that you do not own");
        }else {
            share.setOwner(null);
            share.setCompany(null);
            share.setOffer(null);
        }
    }




}
