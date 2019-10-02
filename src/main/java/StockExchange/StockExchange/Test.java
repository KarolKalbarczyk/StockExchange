package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Person;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.Share_;
import StockExchange.StockExchange.Repositories.ShareRepository;
import StockExchange.StockExchange.Repositories.TraderRepository;
import StockExchange.StockExchange.Specs.*;
import StockExchange.StockExchange.StringCriteria.QueryConstructor;
import StockExchange.StockExchange.StringCriteria.ShareCriteria;
import StockExchange.StockExchange.StringCriteria.CompanyCriteria;
import StockExchange.StockExchange.StringCriteria.TraderCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


        import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static StockExchange.StockExchange.StringCriteria.CompanyCriteria.valueInBetween;
import static StockExchange.StockExchange.StringCriteria.TraderCriteria.wealthInRange;
import static StockExchange.StockExchange.StringCriteria.ShareCriteria.*;
import static StockExchange.StockExchange.StringCriteria.TraderCriteria.wealthInRange;

@Component
public class Test {

    @Autowired
    TraderRepository repo;

    @Autowired
    QueryConstructor queryConstructor;


    @Autowired
    ShareRepository shareRepository;

    @PersistenceContext
    EntityManager manager;


    @EventListener(ApplicationStartedEvent.class)
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
            manager.persist(share);*/
            share.changeOwner(person.get(i));
            repo.save(person.get(i));
            repo.save(companies.get(i));
            shareRepository.save(share);
        }
        System.out.println(List.of("ada").getClass().getSimpleName());

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        //List<Share> a = shareRepository.findAll(ShareSpecs.companyValueInRange(5,15));
        List<Share> a = queryConstructor.createQuery(Share.class, testcheck(5,15), joinCompany(valueInBetween(5,15)), joinOwner(wealthInRange(5,15)) );
        System.out.println(a);
    }


}
