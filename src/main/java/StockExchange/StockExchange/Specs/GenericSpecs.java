package StockExchange.StockExchange.Specs;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Share;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.*;

@Component
public class GenericSpecs {


    public static List<Share> a(EntityManager manager){
        var builder = manager.getCriteriaBuilder();
        CriteriaQuery<Share> query = builder.createQuery(Share.class);
        Root<Share> root = query.from(Share.class);
        Predicate predicate = builder.equal(root.get("test"), 12);
        CriteriaQuery<Company> query2 = builder.createQuery(Company.class);
        Root<Company> root2 = query2.from(Company.class);
        Predicate predicate2 = builder.equal(root2.get("value"), 6);
        query.multiselect(predicate,predicate2);
        return manager.createQuery(query).getResultList();
    }

    public static <T> Specification<T> valueInBetween(double min, double max,
                                                      SingularAttribute<T,? extends Number> attr) {
        var attrname = attr.getName();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(attrname), min, max);
    }

    public static <K,T> Specification<K> valueInBetweenJoin(double min, double max, SingularAttribute<T,? extends Number> attr,
                                                            Attribute<K,? extends T> join) {
        var attrname = attr.getName();
        var joinname = join.getName();
        return (root, query, criteriaBuilder) -> {
            Fetch<K,T> fetch = root.fetch(joinname);
            Join<K,T> joined = (Join<K,T>) fetch;
            return criteriaBuilder.between(joined.get(attrname), min, max);
        };
    }

    public static <T> Specification<T> dateInBetween(Calendar min, Calendar max,
                                                     SingularAttribute<T,? extends Calendar> attr) {
        var attrname = attr.getName();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(attrname), min, max);
    }

    public static <T> Specification<T> dateInBetweenJoin(Calendar min, Calendar max,
                                                     SingularAttribute<T,? extends Calendar> attr) {
        var attrname = attr.getName();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(attrname), min, max);
    }

    protected static <K,T> Specification<K> equalStringJoin(String name, SingularAttribute<T,String> attr,
                                                         Attribute<K,? extends T> join) {
        var attrname = attr.getName();
        var joinname = join.getName();
        return (root, query, criteriaBuilder) -> {
            Fetch<T,K> fetch = root.fetch(joinname);
            Join<T,K> joined = (Join<T,K>) fetch;
            return criteriaBuilder.equal(joined.get(attrname), name);
        };
    }

    public static <K,T> Specification<K> dateInBetweenJoin(double min, double max, SingularAttribute<T,? extends Calendar> attr,
                                                            Attribute<K,? extends T> join) {
        var attrname = attr.getName();
        var joinname = join.getName();
        return (root, query, criteriaBuilder) -> {
            Fetch<K,T> fetch = root.fetch(joinname);
            Join<K,T> joined = (Join<K,T>) fetch;
            return criteriaBuilder.between(joined.get(attrname), min, max);
        };
    }

    public static <T,K> Specification<T> joinvalue(Spec<K,T> spec,Attribute<T,K> join){
        var joinname = join.getName();
        return (root, query, criteriaBuilder) -> {
            Fetch<K,T> fetch = root.fetch(joinname);
            Join<K,T> joined = (Join<K,T>) fetch;
            return spec.predicate(joined,criteriaBuilder);
        };
    }

    protected static <T> Specification<T> equalString(String name, SingularAttribute<T,String> attr) {
        var attrname = attr.getName();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attrname), name);
    }
}

