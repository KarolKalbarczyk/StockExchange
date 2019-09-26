package StockExchange.StockExchange;

import StockExchange.StockExchange.Specs.ShareSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class Test {

    @Autowired
    TraderRepository repo;

    @Autowired
    ShareRepository shareRepository;

    @PersistenceContext
    EntityManager manager;


    @EventListener(ApplicationStartedEvent.class)
    public void test() {

        int[] cash = {5,10,15,4,25};
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

        List<Share> a = shareRepository.findAll(Specification.where((ShareSpecs.joinValue(
                ShareSpecs.valueInBetween(8,11,Person_.wealth),Share_.owner))));
        System.out.println(a);
    }

}
