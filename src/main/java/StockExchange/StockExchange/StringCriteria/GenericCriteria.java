package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Entities.Trader_;

import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GenericCriteria {


    public  static <T> Criteria<T> valueInRange(Class<T> clazz, Attribute<T,? extends Number> attr, double min, double max){
        return ()-> List.of(String.format(Locale.ENGLISH," %s.%s between %f and %f",clazz.getSimpleName().toLowerCase(),attr.getName(),min,max));
    }

    public static  <T> Criteria<T> valueEqual(Class<T> clazz, Attribute<T,? extends Number> attr, double value){
        return ()-> List.of(String.format(Locale.ENGLISH," %s.%s = %f",clazz.getSimpleName().toLowerCase(),attr.getName(),value));
    }

    public static  <T> Criteria<T> stringEqual(Class<T> clazz, Attribute<T,String> attr,String name){
        return ()-> List.of(String.format(" where %s.%s = %s",clazz.getSimpleName().toLowerCase(), attr.getName(), name));
    }

    public static  <T,K> Criteria<T> join(Attribute<T,K> attr,Class<T> clazz, Criteria<K> criteria,Class<K> kClass){
        return () ->{
            String join = String.format(" join %s.%s %s",
                    clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
            List<String> list =  new LinkedList<>(criteria.where());
            list.add(join);
            return list;
        };
    }

    public static  <T,K> Criteria<T> joinCollection(Attribute<T, Collection<K>> attr, Class<T> clazz, Criteria<K> criteria, Class<K> kClass){
        return () ->{
            String join = String.format(" join %s.%s %s",
                    clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
            List<String> list =  new LinkedList<>(criteria.where());
            list.add(join);
            return list;
        };
    }
}
