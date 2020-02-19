package StockExchange.StockExchange.stringCriteria;

import javax.persistence.Query;
import java.util.List;

public interface QueryConstructor {
    public <T> Query createQuery(Class<T> tClass, Criteria<T>... criteria);

    public <T> List<T> executeQuery(Query query, int offset, int limit);
}
