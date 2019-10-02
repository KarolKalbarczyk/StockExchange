package StockExchange.StockExchange.Specs;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Company_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;

public class CompanySpecs extends GenericSpecs {
    private static Specification<Company> valueInRange(double min, double max){
        return valueInBetween(min,max, Company_.value);
    }

    private static Specification<Company> ValueLesser(int cost){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lt(root.get("value"), cost);
    }

    public static <T,K> Spec<T,K> predicate(){
        return (From<T,K> from,CriteriaBuilder builder) ->
        builder.between(from.get("test"),10,15);
    }
}

