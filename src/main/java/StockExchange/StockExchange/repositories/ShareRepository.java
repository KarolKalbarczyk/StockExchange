package StockExchange.StockExchange.repositories;

import StockExchange.StockExchange.entities.Company;
import StockExchange.StockExchange.entities.Share;
import StockExchange.StockExchange.entities.Trader;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareRepository extends JpaRepository<Share,Long>, JpaSpecificationExecutor {
     List<Share> findAll();
     List<Share> findAllByOwner(Trader owner);
     List<Share> findAllByCompany(Company company);

    @EntityGraph(attributePaths = {"offer","company","owner"})
    Optional<Share> findOneById(long id);
}
