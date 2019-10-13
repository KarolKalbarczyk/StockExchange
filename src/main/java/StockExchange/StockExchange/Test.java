package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.*;
import StockExchange.StockExchange.Entities.Share_;
import StockExchange.StockExchange.Repositories.OfferRepository;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Specs.*;
import StockExchange.StockExchange.StringCriteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static StockExchange.StockExchange.StringCriteria.CompanyCriteria.valueInBetween;
import static StockExchange.StockExchange.StringCriteria.ShareCriteria.*;
import static StockExchange.StockExchange.StringCriteria.TraderCriteria.*;

@Component
public class Test {

    @Autowired
    TraderRepository traderRepository;
    @Autowired
    QueryConstructor queryConstructor;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    ShareService shareService;
    @Autowired
    OfferService offerService;
    @Autowired
    OfferRepository offerRepository;


    @PersistenceContext
    EntityManager manager;

    List<Trader> traders = new ArrayList<>();
    List<Share> shares = new ArrayList<>();
    List<Offer> offers = new ArrayList<>();



    @EventListener(ApplicationStartedEvent.class)
    @Order(0)
    public void prepare(){
        var company = new Company(10);
        traders.add(company);
        traderRepository.save(company);
        shares.add(shareService.createShare(company.getId()));
        shareRepository.save(shares.get(0));
    }

     @EventListener(ApplicationStartedEvent.class)
     @Order(1)
     public void testShareCreation(){
         List<Trader> a = queryConstructor.createQuery(Trader.class,joinShare(testcheck(15,25)));
         System.out.println("shareCreation");
         System.out.println(a);
         assert a.size() == 1;
     }

     @Transactional
    @EventListener(ApplicationStartedEvent.class)
    @Order(2)
    public void testOfferCreation(){
        Share share = shareRepository.findAll().get(0);
        var company = traderRepository.findAll().get(0);
        offerService.createOffer(share.getId(),company.getId(),new BigDecimal(50));
        List<Offer> a = queryConstructor.createQuery(Offer.class, OfferCriteria.costEquals(50));
        assert a.size() == 1;
        Share share3 = shareRepository.findAll().get(0);
        offerRepository.delete(a.get(0));
        Share share2 = shareRepository.findAll().get(0);

    }

    @EventListener(ApplicationStartedEvent.class)
    @Order(3)
    public void testOfferReplacement(){
        Share share = shareRepository.findAll().get(0);
        var company = traderRepository.findAll().get(0);
        offerService.createOffer(share.getId(),company.getId(),new BigDecimal(50));
        offerService.createOffer(share.getId(),company.getId(),new BigDecimal(75));
        List<Offer> a = offerRepository.findAllByShare(share);
        assert a.size() == 1 && a.get(0).getCost().equals(75);

        offerRepository.delete(a.get(0));

    }

    @EventListener(ApplicationStartedEvent.class)
    @Order(4)
    public void testHasOffer(){
        Share share = shareRepository.findAll().get(0);
        var company = traderRepository.findAll().get(0);
        offerService.createOffer(share.getId(),company.getId(),new BigDecimal(40));
        assert shareService.hasOffer(share.getId());
        List<Offer> a = offerRepository.findAllByShare(share);
        offers.addAll(a);
    }

    @EventListener(ApplicationStartedEvent.class)
    @Order(5)
    public void testExchangeShareWhenNotEnoughMoney(){
        var person = new Person();
        person.setWealth(new BigDecimal(50));
        traderRepository.save(person);
        shareService.exchangeShare(shares.get(0).getId(),offers.get(0).getId(),person.getId());
        List<Share> list1 = shareRepository.findAllByOwner(traders.get(0));
        List<Share> list2 = shareRepository.findAllByOwner(person);
        assert list1.size() == 1;
        assert list2.size() == 0;
        traderRepository.delete(person);
    }

    @EventListener(ApplicationStartedEvent.class)
    @Order(6)
    public void testExchangeShare(){
        var person = new Person();
        person.setWealth(new BigDecimal(50));
        traderRepository.save(person);
        shareService.exchangeShare(shares.get(0).getId(),offers.get(0).getId(),person.getId());
        List<Share> list1 = shareRepository.findAllByOwner(traders.get(0));
        List<Share> list2 = shareRepository.findAllByOwner(person);
        assert list1.size() == 0;
        assert list2.size() == 1;
    }

/* @EventListener(ApplicationStartedEvent.class)
    public void test() {

        int[] cash = {6,10,15,4,25};
        int[] wel = {9,19,25,11,17};
        ArrayList<Company> companies = new ArrayList<>();
        ArrayList<Person> person = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            companies.add(new Company(cash[i]));
            person.add(new Person(wel[i]));
            var share = companies.get(i).newShare();
           /* var transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(companies.get(i));
            manager.persist(share); *//*
            share.changeOwner(person.get(i));
            traderRepository.save(person.get(i));
            traderRepository.save(companies.get(i));
            shareRepository.save(share);
        }
        System.out.println(List.of("ada").getClass().getSimpleName());

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        //List<Share> a = shareRepository.findAll(ShareSpecs.companyValueInRange(5,15));
        //List<Share> a = queryConstructor.createQuery(Share.class, testcheck(5,15), joinCompany(valueInBetween(5,15)), joinOwner(wealthInRange(5,15)) );
        List<Trader> a = queryConstructor.createQuery(Trader.class,joinShare(testcheck(5,15)));
        System.out.println(a);
    }*/

}
