package StockExchange.StockExchange.Specs;

import StockExchange.StockExchange.Company;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Component
public class GenericSpecs {



   /* public <T> List<T> getResultList(Class<T> clazz) {
        var builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        Join<T,T> f = root.join("a");
        root = (Root<T>) f;
        query.select(f);
        SingularAttribute singularAttribute;
        Object y;
        Predicate pred = builder.equal(root.get(singularAttribute), y);
        buildPredicates(root);
        return em.createQuery(query.select(root).where(predicates)).getResultList();
    }*/


    public static <T> Specification<T> valueInBetween(double min, double max,
                                                        SingularAttribute<T,? extends Number> attr) {
        var attrname = attr.getName();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(attrname), min, max);
    }

    public static <K,T> Specification<K> valueInBetweenJoin(int min, int max, SingularAttribute<T,? extends Number> attr,
                                                            Attribute<K,? extends T> join) {
        var attrname = attr.getName();
        var joinname = join.getName();
        return (root, query, criteriaBuilder) -> {
            Fetch<K,T> fetch = root.fetch(joinname);
            Join<K,T> joined = (Join<K,T>) fetch;
            return criteriaBuilder.between(joined.get(attrname), min, max);
        };
    }

    protected static <K,T> Specification<K> equalStringJoin(String name, SingularAttribute<T,String> attr,
                                                         Attribute<K,? extends T> join) {
        var attrname = attr.getName();
        var joinname = join.getName();
        return (root, query, criteriaBuilder) -> {
            Fetch<K,T> fetch = root.fetch(joinname);
            Join<K,T> joined = (Join<K,T>) fetch;
            return criteriaBuilder.equal(joined.get(attrname), name);
        };
    }

    protected static <T> Specification<T> equalString(String name, SingularAttribute<T,String> attr) {
        var attrname = attr.getName();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attrname), name);
    }
}
