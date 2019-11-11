package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Controllers.ErrorCodes;
import StockExchange.StockExchange.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShareService{
    @Autowired
    MainService main;

    @Transactional
    public void exchangeShare(long offerId, String accountName){
        var optOffer = main.offerRepository.findOneById(offerId);
        var buyer = main.traderRepository.findOneByAccountLogin(accountName);
        optOffer.ifPresentOrElse(offer -> {
            var share = offer.getShare();
            exchangeShare(share, offer, buyer);
            share.setOffer(null); }, () -> {throw new IllegalCallerException(ErrorCodes.NotLogged.name());});
    }

    @Transactional
    private void exchangeShare(Share share,Offer offer,Trader buyer){
        if (offer.getOwner().equals(buyer)) throw new IllegalCallerException(ErrorCodes.YoureOwner.name());
        exchangeMoney(buyer,offer.getOwner(),offer.getCost());
        createTransaction(share,offer,buyer);
        share.setOwner(buyer);
    }

    public boolean hasOffer(long shareId){
        var share = main.shareRepository.getOne(shareId);
        var offer = main.offerRepository.findOneByShare(share);
        return offer.isPresent();
    }

    public void exchangeMoney(Trader buyer, Trader seller, long money){
        if(buyer.getWealth() < money)
            throw new IllegalCallerException(ErrorCodes.NoMoney.name());
        seller.addWealth(money);
        buyer.subtractWealth(money);
    }

    private void createTransaction(Share share, Offer offer, Trader buyer){
        var transaction = new StockTransaction(share,offer,buyer);
        main.transactionRepository.save(transaction);
    }

    @Transactional
    public Share createShareAndOfferIfCompany(String accountName, int cost){
        var company = main.traderRepository.findCompanyByName(accountName);
        if(company.isPresent()){
           return createShareAndOffer(company.get(), cost);
        }
        else {
            throw new IllegalCallerException(ErrorCodes.NotCompany.name());
        }
    }

    private Share createShareAndOffer(Company company,int cost){
        var share = new Share(company);
        var offer = new Offer(cost,share,company);
        main.shareRepository.save(share);
        main.offerRepository.save(offer);
        return share;
    }

    @Transactional
    public void revokeShare(String accountName, long shareId){
        var company = main.traderRepository.findOneByAccountLogin(accountName);
        var optShare = main.shareRepository.findOneById(shareId);
        optShare.ifPresent(share -> {
            if (!share.getOwner().equals(company) || !share.getCompany().equals(company)) {
                throw new IllegalCallerException(ErrorCodes.NotOwner.name());
            } else main.shareRepository.delete(share);
         });
    }




}
