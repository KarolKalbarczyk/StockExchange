package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Entities.Trader_;

import javax.persistence.metamodel.Attribute;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public abstract class GenericCriteria implements CriteriaBuilder {


    public  <T> Criteria<T> valueInRange(Class<T> clazz, Attribute<T,?> attr, double min, double max){
        var maxS = new BigDecimal(max).setScale(2, RoundingMode.HALF_UP).toString();
        var minS = new BigDecimal(min).setScale(2, RoundingMode.HALF_UP).toString();
        return ()-> List.of(String.format(Locale.ENGLISH," %s.%s between %s and %s",clazz.getSimpleName().toLowerCase(),attr.getName(),minS,maxS));
    }

    public <T> Criteria<T> valueEqual(Class<T> clazz, Attribute<T,?> attr, double value){
        var val = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
        return ()-> List.of(String.format(Locale.ENGLISH," %s.%s = %s",clazz.getSimpleName().toLowerCase(),attr.getName(),val));
    }

    public <T> Criteria<T> stringEqual(Class<T> clazz, Attribute<T,String> attr,String name){
        return ()-> List.of(String.format(" where %s.%s = %s",clazz.getSimpleName().toLowerCase(), attr.getName(), name));
    }

    public <T,K> Criteria<T> join(Attribute<T,K> attr,Class<T> clazz, List<Criteria<K>> criteria,Class<K> kClass){
        return () ->{
            String join = String.format(" join %s.%s %s",
                    clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
            List<String> list =  new LinkedList<String>(criteria.stream().map( v -> v.where()).flatMap(Collection::stream).collect(toList()));
            list.add(join);
            return list;
        };
    }

    public <T,K> Criteria<T> joinCollection(Attribute<T, Collection<K>> attr, Class<T> clazz, Criteria<K> criteria, Class<K> kClass){
        return () ->{
            String join = String.format(" join %s.%s %s",
                    clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
            List<String> list =  new LinkedList<>(criteria.where());
            list.add(join);
            return list;
        };
    }
}
