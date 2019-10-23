package StockExchange.StockExchange.StringCriteria;

import javax.persistence.Query;

public interface QueryConstructor {
    <T> Query createQuery(Class<T> tClass,Criteria<T>... criteria);
}
