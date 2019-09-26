package StockExchange.StockExchange.Specs;

import StockExchange.StockExchange.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecs {
    private static Specification<Company> ValueGreater(int cost){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.gt(root.get("value"), cost);
    }

    private static Specification<Company> ValueLesser(int cost){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lt(root.get("value"), cost);
    }
}
