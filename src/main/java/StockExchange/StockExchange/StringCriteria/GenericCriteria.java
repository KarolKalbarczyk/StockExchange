package StockExchange.StockExchange.StringCriteria;

//import StockExchange.StockExchange.StockExchange.StockExchange.StockExchange.Entities.Entities.Trader_;

import javax.persistence.metamodel.Attribute;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

        import static java.util.stream.Collectors.toList;

public abstract class GenericCriteria implements CriteriaBuilder {

    protected String NO_SUCH_METHOD = "NoSuchMethod";
    protected UnsupportedOperationException exception = new UnsupportedOperationException(NO_SUCH_METHOD);

    public  <T> Criteria<T> valueInRange(Class<T> clazz, Attribute<T,?> attr, double min, double max){
        var maxS = new BigDecimal(max).setScale(2, RoundingMode.HALF_UP).toString();
        var minS = new BigDecimal(min).setScale(2, RoundingMode.HALF_UP).toString();
        var query = String.format(Locale.ENGLISH," %s.%s between %s and %s",clazz.getSimpleName().toLowerCase(),attr.getName(),minS,maxS);
        var criteria = new Criteria<T>();
        criteria.addToWhere(query);
        criteria.addSelect(clazz.getSimpleName());
        return criteria;
    }

    public <T> Criteria<T> valueEqual(Class<T> clazz, Attribute<T,?> attr, double value){
        var val = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
        var query = String.format(Locale.ENGLISH," %s.%s = %s",clazz.getSimpleName().toLowerCase(),attr.getName(),val);
        var criteria = new Criteria<T>();
        criteria.addToWhere(query);
        criteria.addSelect(clazz.getSimpleName());
        return criteria;
    }

    public <T> Criteria<T> stringEqual(Class<T> clazz, Attribute<T,String> attr,String name){
        var query = String.format(" %s.%s = '%s'",clazz.getSimpleName().toLowerCase(), attr.getName(), name);
        var criteria = new Criteria<T>();
        criteria.addToWhere(query);
        criteria.addSelect(clazz.getSimpleName());
        return criteria;
    }

    public <T,K> Criteria<T> join(Attribute<T,K> attr,Class<T> clazz, List<Criteria<K>> criterias,Class<K> kClass){
        var criteria = new Criteria();
        var query = String.format(" join %s.%s %s",
                clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
        criteria.addJoin(query);
        criteria.addJoinAll(getJoinsFromCriterias(criterias));
        criteria.addWhereAll(getConditionsFromCriterias(criterias));
        criteria.addSelectAll(getSelectsFromCriterias(criterias));
        return criteria;
    }

    private <K> void appendJoin(List<String> criteria,String join){
        int i = 0;
        while (criteria.get(i++).charAt(0) == 'j');
        criteria.add(i,join);
    }

    public <T,K> Criteria<T> joinCollection(Attribute<T, Collection<K>> attr, Class<T> clazz, List<Criteria<K>> criterias, Class<K> kClass){
        /*return () ->{
            String join = String.format(" join %s.%s %s",
                    clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
            List<String> list =  new LinkedList<>(getConditionsFromCriterias(criteria));
            appendJoin(list,join);
            return "list";
        };*/
        var criteria = new Criteria();
        var query = String.format(" join %s.%s %s",
                clazz.getSimpleName().toLowerCase(),attr.getName(),kClass.getSimpleName().toLowerCase());
        criteria.addJoin(query);
        criteria.addJoinAll(getJoinsFromCriterias(criterias));
        criteria.addWhereAll(getConditionsFromCriterias(criterias));
        criteria.addSelectAll(getSelectsFromCriterias(criterias));
        return criteria;
    }

    private <T> List<String> getConditionsFromCriterias(List<Criteria<T>> criteria){
        return criteria.stream().map(Criteria::getWhere)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private <T> List<String> getJoinsFromCriterias(List<Criteria<T>> criteria){
        return criteria.stream().map(Criteria::getJoin)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private <T> List<String> getSelectsFromCriterias(List<Criteria<T>> criteria){
        return criteria.stream().map(Criteria::getSelect)
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
