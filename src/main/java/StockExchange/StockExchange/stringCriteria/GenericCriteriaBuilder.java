package StockExchange.StockExchange.stringCriteria;

//import StockExchange.StockExchange.StockExchange.StockExchange.StockExchange.Entities.Entities.Trader_;

import com.company.Attribute;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class GenericCriteriaBuilder implements CriteriaBuilder {

    protected String NO_SUCH_METHOD = "NoSuchMethod";
    protected UnsupportedOperationException exception = new UnsupportedOperationException(NO_SUCH_METHOD);
    private double EPSILON = 0.001;

    public <T> Criteria<T> valueInRange(Attribute<T, ?> attr, double min, double max) {
        var query = String.format(Locale.ENGLISH, " %s.%s between %s and %s",
                attr.gettClass().getSimpleName().toLowerCase(), attr.getName(), min, max);
        return buildCriteria(query, attr.gettClass());
    }

    public <T> Criteria<T> valueEqual(Attribute<T, ?> attr, double value) {
        var min = value - EPSILON;
        var max = value + EPSILON;
        return valueInRange(attr,min,max);
    }

    public <T> Criteria<T> stringEqual(Attribute<T, String> attr, String name) {
        var query = String.format(" %s.%s = '%s'", attr.gettClass().getSimpleName().toLowerCase(),
                attr.getName(), name);
        return buildCriteria(query, attr.gettClass());
    }

    public <T> Criteria<T>[] count(Criteria<T>... criterias){
        if(criterias.length > 0)
            criterias[0].getSelect().set(0,"count(*)");
        return criterias;
    }

    private <T> Criteria<T> buildCriteria(String query, Class<T> clazz) {
        var criteria = new Criteria<T>();
        criteria.addToWhere(query);
        criteria.addSelect(clazz.getSimpleName());
        return criteria;
    }

    public <T, K> Criteria<T> join(Attribute<T, K> attr,
                                   List<Criteria<K>> criterias) {
        var criteria = new Criteria<T>();
        var query = String.format(" join %s.%s %s",
                attr.gettClass().getSimpleName().toLowerCase(),
                attr.getName(),
                attr.getkClass().getSimpleName().toLowerCase());
        criteria.addJoin(query);
        criteria.addJoinAll(getFieldFromCriterias(criterias, Criteria::getJoin));
        criteria.addWhereAll(getFieldFromCriterias(criterias, Criteria::getWhere));
        criteria.addSelectAll(getFieldFromCriterias(criterias, Criteria::getSelect));
        criteria.addSelect(String.format("%s"));
        return criteria;
    }

    private <T> List<String> getFieldFromCriterias(
            List<Criteria<T>> criteria,
            Function<Criteria<T>, List<String>> fieldSelect) {
        return criteria.stream()
                .map(fieldSelect)
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
