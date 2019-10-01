package StockExchange.StockExchange.Specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

public interface Spec<T,K> {
    public Predicate predicate(From<T, K> from, CriteriaBuilder builder);
}

