package StockExchange.StockExchange.Services;

import StockExchange.StockExchange.Controllers.ErrorCodes;
import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.Entities.DTO.ShareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShareService{
    @Autowired
    MainService main;
    @Autowired
    TranscationService transcationService;

    @Transactional()
    public void exchangeShare(long offerId, String accountName){
        var optOffer = main.offerRepository.findOneById(offerId);
        var buyer = main.traderRepository.findOneByAccountLogin(accountName);
        optOffer.ifPresentOrElse(
                offer -> exchangeShare(offer, buyer)
                , () -> {throw new IllegalCallerException(ErrorCodes.NotLogged.name());});
    }

    @Transactional
    private void exchangeShare(Offer offer,Trader buyer){
        if (offer.getOwner().equals(buyer)) throw new IllegalCallerException(ErrorCodes.YoureOwner.name());
        var share = offer.getShare();
        exchangeMoney(buyer,offer.getOwner(),offer.getCost());
        transcationService.createTransaction(offer,buyer);
        share.setOwner(buyer);
        share.setOffer(null);
    }

    public boolean hasOffer(long shareId){
        return main.offerRepository.existsByShareId(shareId);
    }

    void exchangeMoney(Trader buyer, Trader seller, long money){
        if(buyer.getWealth() < money)
            throw new IllegalCallerException(ErrorCodes.NoMoney.name());
        seller.addWealth(money);
        buyer.subtractWealth(money);
    }

    @Transactional
    public ShareDTO createShareAndOfferIfCompany(String accountName, int cost){
        var company = main.traderRepository.findCompanyByName(accountName);
        var share = company.map(comp -> createShareAndOffer(comp,cost)).
                orElseThrow(() -> new IllegalCallerException(ErrorCodes.NotCompany.name()));
        return new ShareDTO(share);
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
        optShare.filter(share-> isSharesOwnerAndCreator(share,company)).
                ifPresentOrElse(
                        share -> main.shareRepository.delete(share),
                        () -> {throw new IllegalCallerException(ErrorCodes.NotOwner.name());});
    }

    private boolean isSharesOwnerAndCreator(Share share,Trader company){
        return share.getOwner().equals(company) && share.getCompany().equals(company);
    }




}
