package StockExchange.StockExchange.stringCriteria;

import java.util.List;

public interface QueryConstructor {
    public <T> String createQuery(Class<T> tClass, Criteria<T>... criteria);

    public <T> List<T> executeQuery(String query, int offset, int limit);
}
