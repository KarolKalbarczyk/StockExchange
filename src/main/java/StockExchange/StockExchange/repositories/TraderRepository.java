package StockExchange.StockExchange.repositories;

import StockExchange.StockExchange.entities.Company;
import StockExchange.StockExchange.entities.Person;
import StockExchange.StockExchange.entities.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderRepository  extends JpaRepository<Trader,Long>, JpaSpecificationExecutor<Trader> {
    Trader findOneByName(String name);
    Trader findOneByAccountLogin(String login);
    Trader findOneById(long id);

    @Query(value = "Select c from trader c where id = ?1 and dtype ='Company' ",nativeQuery = true)
    Optional<Company> findCompanyById(long id);

    @Query(value = "Select * from trader where trader.name = ?1 and trader.dtype ='Company' ",nativeQuery = true)
    Optional<Company> findCompanyByName(String name);

    @Query(value = "Select c from trader c where id = ?1 and dtype ='Person' ",nativeQuery = true)
    Optional<Person> findPersonyById(long id);

    @Query(value = "Select * from trader where trader.name = ?1 and trader.dtype ='Person' ",nativeQuery = true)
    Optional<Person> findPersonByName(String name);
}
