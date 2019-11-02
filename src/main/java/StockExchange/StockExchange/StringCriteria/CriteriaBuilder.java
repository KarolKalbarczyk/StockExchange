package StockExchange.StockExchange.StringCriteria;

import javax.persistence.metamodel.Attribute;
import java.util.List;

public interface CriteriaBuilder {

    <T> Criteria<T> chooseMethod(String attribute, String name);
    <T> Criteria<T> chooseMethod(String attribute, double min, double max);
    <T,K> Criteria<T> chooseJoin(String attribute, List<Criteria<?>> criteria);
    <T> Criteria<T> valueInRange(Class<T> clazz, Attribute<T,?> attr, double min, double max);
    <T> Criteria<T> stringEqual(Class<T> clazz, Attribute<T,String> attr,String name);
    <T,K> Criteria<T> join(Attribute<T,K> attr,Class<T> clazz,List<Criteria<K>> criteria,Class<K> kClass);
}
