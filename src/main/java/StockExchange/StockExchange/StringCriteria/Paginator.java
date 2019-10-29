package StockExchange.StockExchange.StringCriteria;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class Paginator implements QueryPaginator {

    @Override
    @Transactional
    public <T> List<T> paginateAndExecuteQuery(TypedQuery<T> query,int offset,int limit) {
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
