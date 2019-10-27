package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
}
