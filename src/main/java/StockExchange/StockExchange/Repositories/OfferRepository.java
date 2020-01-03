package StockExchange.StockExchange.Repositories;


import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository  extends JpaRepository<Offer,Long>, JpaSpecificationExecutor<Offer> {

    Optional<Offer> findOneByShare(Share share);
    List<Offer> findAllByShare(Share share);
    List<Offer> findAllByCost(long money);
    Optional<Offer> findOneById(long id);
    boolean existsByShareId(long id);
}

