package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share,Long>, JpaSpecificationExecutor {
     List<Share> findAll();
     List<Share> findAllByOwner(Trader owner);
     List<Share> findAllByCompany(Company company);

     @Query("Select s from share s join trader using(owner_id,id) where id = ?1")
     Share findAndJoinOwner(long id);


}
