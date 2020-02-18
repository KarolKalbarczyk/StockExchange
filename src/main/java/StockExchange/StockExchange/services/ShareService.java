package StockExchange.StockExchange.services;

import Entities.ShareDTO;
import StockExchange.StockExchange.repositories.OfferRepository;
import StockExchange.StockExchange.repositories.ShareRepository;
import StockExchange.StockExchange.repositories.TraderRepository;
import StockExchange.StockExchange.controllers.ErrorCodes;
import StockExchange.StockExchange.entities.Company;
import StockExchange.StockExchange.entities.Offer;
import StockExchange.StockExchange.entities.Share;
import StockExchange.StockExchange.entities.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShareService {

    private final TranscationService transcationService;
    private final OfferRepository offerRepository;
    private final ShareRepository shareRepository;
    private final TraderRepository traderRepository;

    @Autowired
    public ShareService(TranscationService transcationService,
                        OfferRepository offerRepository,
                        ShareRepository shareRepository,
                        TraderRepository traderRepository) {
        this.transcationService = transcationService;
        this.offerRepository = offerRepository;
        this.shareRepository = shareRepository;
        this.traderRepository = traderRepository;
    }

    @Transactional
    public void exchangeShare(long offerId, String accountName) {
        var optOffer = offerRepository.findOneById(offerId);
        var buyer = traderRepository.findOneByAccountLogin(accountName);
        optOffer.ifPresentOrElse(
                offer -> exchangeShare(offer, buyer),
                () -> {
                    throw new IllegalCallerException(ErrorCodes.NotLogged.name());
                });
    }

    private void exchangeShare(Offer offer, Trader buyer) {
        if (offer.getOwner().equals(buyer))
            throw new IllegalCallerException(ErrorCodes.YoureOwner.name());
        var share = offer.getShare();
        exchangeMoney(buyer, offer.getOwner(), offer.getCost());
        transcationService.createTransaction(offer, buyer);
        share.setOwner(buyer);
        share.setOffer(null);
    }

    public boolean hasOffer(long shareId) {
        return offerRepository.existsByShareId(shareId);
    }

    void exchangeMoney(Trader buyer, Trader seller, long money) {
        if (buyer.getWealth() < money)
            throw new IllegalCallerException(ErrorCodes.NoMoney.name());
        seller.addWealth(money);
        buyer.subtractWealth(money);
    }

    @Transactional
    public ShareDTO createShareAndOfferIfCompany(String accountName, int cost) {
        var company = traderRepository.findCompanyByName(accountName);
        var share = company.map(comp -> createShareAndOffer(comp, cost))
                .orElseThrow(() -> new IllegalCallerException(ErrorCodes.NotCompany.name()));
        return new ShareDTO(share);
    }

    private Share createShareAndOffer(Company company, int cost) {
        var share = new Share(company);
        var offer = new Offer(cost, share, company);
        shareRepository.save(share);
        offerRepository.save(offer);
        System.out.println("AAAAAAAAAAAAAAAAAA");
        return share;
    }

    @Transactional
    public void revokeShare(String accountName, long shareId) {
        var company = traderRepository.findOneByAccountLogin(accountName);
        var optShare = shareRepository.findOneById(shareId);
        optShare.filter(share -> isSharesOwnerAndCreator(share, company))
                .ifPresentOrElse(
                        share -> shareRepository.delete(share),
                        () -> {
                            throw new IllegalCallerException(ErrorCodes.NotOwner.name());
                        });
    }

    private boolean isSharesOwnerAndCreator(Share share, Trader company) {
        return share.getOwner().equals(company) && share.getCompany().equals(company);
    }
}
