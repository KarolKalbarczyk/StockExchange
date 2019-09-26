package StockExchange.StockExchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share,Long>, JpaSpecificationExecutor {
     List<Share> findAll();
     List<Share> findAllByOwner(Trader owner);
     List<Share> findAllByCompany(Company company);


}
