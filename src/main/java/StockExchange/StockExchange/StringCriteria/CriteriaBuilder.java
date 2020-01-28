package StockExchange.StockExchange.StringCriteria;


import com.company.Attribute;

import java.util.List;

public interface CriteriaBuilder {

    <T> Criteria<T> valueInRange(Attribute<T, ?> attr, double min, double max);

    <T> Criteria<T> stringEqual(Attribute<T, String> attr, String name);

    <T, K> Criteria<T> join(Attribute<T,K> attr, List<Criteria<K>> criteria);
}
