package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;

import javax.persistence.metamodel.Attribute;
import java.util.List;

public interface CriteriaBuilder {

    <T> Criteria<T> chooseMethod(Attributes attribute, String name);
    <T> Criteria<T> chooseMethod(Attributes attribute, double min, double max);
    <T> Criteria<T> chooseJoin(Entities entities, Criteria... criteria);
    <T> Criteria<T> valueInRange(Class<T> clazz, Attribute<T,?> attr, double min, double max);
    <T> Criteria<T> stringEqual(Class<T> clazz, Attribute<T,String> attr,String name);
    <T,K> Criteria<T> join(Attribute<T,K> attr,Class<T> clazz,List<Criteria<K>> criteria,Class<K> kClass);
}
