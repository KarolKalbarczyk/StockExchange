package StockExchange.StockExchange.StringCriteria;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public interface QueryPaginator {
    public <T> List<T> paginateAndExecuteQuery(TypedQuery<T> query,int offset,int limit);
}
