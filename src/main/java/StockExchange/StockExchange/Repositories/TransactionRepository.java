package StockExchange.StockExchange.Repositories;

import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.StockTransaction;
import StockExchange.StockExchange.Entities.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository  extends JpaRepository<StockTransaction,Long>, JpaSpecificationExecutor<StockTransaction> {

    List<StockTransaction> findAllByShareId(long id);
    List<StockTransaction> findAllByBuyerId(long id);
    List<StockTransaction> findAllBySellerId(long id);

}
