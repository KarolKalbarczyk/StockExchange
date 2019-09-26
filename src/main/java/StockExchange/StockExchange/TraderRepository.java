package StockExchange.StockExchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraderRepository  extends JpaRepository<Trader,Long>, JpaSpecificationExecutor<Trader> {
    List<Trader> findAll();

}
