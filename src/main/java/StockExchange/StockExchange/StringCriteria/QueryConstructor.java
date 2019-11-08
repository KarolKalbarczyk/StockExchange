package StockExchange.StockExchange.StringCriteria;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public interface QueryConstructor {
    public <T> String createQuery(Class<T> tClass,Criteria<T>... criteria);
    public <T> List<T> executeQuery(String query, int offset,int limit);
}
