package StockExchange.StockExchange.stringCriteria;

//import StockExchange.StockExchange.StockExchange.StockExchange.StockExchange.Entities.Entities.Trader_;

import javax.persistence.metamodel.Attribute;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public abstract class GenericCriteriaBuilder implements CriteriaBuilder {

    protected String NO_SUCH_METHOD = "NoSuchMethod";
    protected UnsupportedOperationException exception = new UnsupportedOperationException(NO_SUCH_METHOD);

    public <T> Criteria<T> valueInRange(Class<T> clazz, Attribute<T, ?> attr, double min, double max) {
        var query = String.format(Locale.ENGLISH, " %s.%s between %s and %s",
                clazz.getSimpleName().toLowerCase(), attr.getName(), min, max);
        return buildCriteria(query, clazz);
    }

    public <T> Criteria<T> valueEqual(Class<T> clazz, Attribute<T, ?> attr, double value) {
        var query = String.format(Locale.ENGLISH, " %s.%s = %s",
                clazz.getSimpleName().toLowerCase(), attr.getName(), value);
        return buildCriteria(query, clazz);
    }

    public <T> Criteria<T> stringEqual(Class<T> clazz, Attribute<T, String> attr, String name) {
        var query = String.format(" %s.%s = '%s'", clazz.getSimpleName().toLowerCase(),
                attr.getName(), name);
        return buildCriteria(query, clazz);
    }

    private <T> Criteria<T> buildCriteria(String query, Class<T> clazz) {
        var criteria = new Criteria<T>();
        criteria.addToWhere(query);
        criteria.addSelect(clazz.getSimpleName());
        return criteria;
    }

    public <T, K> Criteria<T> join(Attribute<T, K> attr,
                                   Class<T> clazz,
                                   List<Criteria<K>> criterias,
                                   Class<K> kClass) {
        return joinBasedOnAttrName(attr.getName(), clazz, criterias, kClass);
    }

    public <T, K> Criteria<T> joinCollection(Attribute<T, Collection<K>> attr,
                                             Class<T> clazz,
                                             List<Criteria<K>> criterias,
                                             Class<K> kClass) {
        return joinBasedOnAttrName(attr.getName(), clazz, criterias, kClass);
    }

    private <T, K> Criteria<T> joinBasedOnAttrName(String attrName,
                                                   Class<T> clazz,
                                                   List<Criteria<K>> criterias,
                                                   Class<K> kClass) {
        var criteria = new Criteria<T>();
        var query = String.format(" join %s.%s %s",
                clazz.getSimpleName().toLowerCase(), attrName, kClass.getSimpleName().toLowerCase());
        criteria.addJoin(query);
        criteria.addJoinAll(getJoinsFromCriterias(criterias));
        criteria.addWhereAll(getWheresFromCriterias(criterias));
        criteria.addSelectAll(getSelectsFromCriterias(criterias));
        return criteria;
    }

    private <T> List<String> getWheresFromCriterias(List<Criteria<T>> criteria) {
        return getFieldFromCriterias(criteria, Criteria::getWhere);
    }

    private <T> List<String> getJoinsFromCriterias(List<Criteria<T>> criteria) {
        return getFieldFromCriterias(criteria, Criteria::getJoin);
    }

    private <T> List<String> getSelectsFromCriterias(List<Criteria<T>> criteria) {
        return getFieldFromCriterias(criteria, Criteria::getSelect);
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
