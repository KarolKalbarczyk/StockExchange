package StockExchange.StockExchange.Repositories;


import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository  extends JpaRepository<Offer,Long>, JpaSpecificationExecutor<Offer> {

}

