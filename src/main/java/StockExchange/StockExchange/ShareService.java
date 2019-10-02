package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

public class ShareService {
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public void exchangeShare(long shareId, long offerId, String buyerName){
        var offer = offerRepository.getOne(offerId);
        var buyer = traderRepository.findOneByName(buyerName);
        if(buyer.getWealth()<offer.getCost()) return;
        var share = shareRepository.getOne(shareId);
        share.setOwner(buyer);
        createTransaction(share,offer,buyer);
        exchangeMoney(buyer,offer.getOwner(),offer.getCost());
        offer.setOwner(null);
        share.setOffer(null);
    }

    public boolean hasOffer(long shareId){
        var share = shareRepository.getOne(shareId);
        var offer = offerRepository.findOneByShare(share);
        return offer.isPresent();
    }

    public void exchangeMoney(Trader buyer, Trader seller, int money){
        seller.changeWealth(money);
        buyer.changeWealth(-money);
    }

    private void createTransaction(Share share, Offer offer, Trader buyer){
        var transaction = new StockTransaction(share,offer,buyer);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void createShare(long companyId){
        var company = new Company();
        company.setId(companyId);
        var share = new Share(company);
        shareRepository.save(share);
    }

    @Transactional
    public void revokeShare(long companyId, long shareId) throws IllegalAccessException{
        var company = traderRepository.getOne(companyId);
        var share = shareRepository.findAndJoinOwner(shareId);
        if(!share.getOwner().equals(company)){
            throw new IllegalAccessException("you can't revoke share that you do not own");
        }
        share.setOwner(null);
        share.setCompany(null);
    }




}
