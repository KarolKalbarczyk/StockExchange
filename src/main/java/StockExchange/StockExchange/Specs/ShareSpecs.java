package StockExchange.StockExchange.Specs;

import StockExchange.StockExchange.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

public class ShareSpecs  extends GenericSpecs{

    public static Specification<Share> companyWealthInRange(int min,int max){
        return valueInBetweenJoin(min,max, Company_.wealth, Share_.company);
    }

    public static Specification<Share> companyValueInRange(int min,int max){
        return valueInBetweenJoin(min,max, Company_.value, Share_.company);
    }

    public static Specification<Share> companyName(String name){
        return equalStringJoin(name, Company_.name, Share_.company);
    }

    public static Specification<Share> ownerWealthInRange(int min,int max){
        return valueInBetweenJoin(min,max, Trader_.wealth, Share_.owner);
    }


    public static Specification<Share> ownerName(String name){
        return equalStringJoin(name, Trader_.name, Share_.owner);
    }


   /* public static  Specification<Share> ValueinaBetween(int min, int max, SingularAttribute attr) {
        Specification<Trader> a = valueInBetween(0,5,Company_.wealth);
        return (From root, Query query, CriteriaBuilder criteriaBuilder) -> {
            root.get(Share_.id);
            criteriaBuilder.between(root.get(Share_.id), min, max);
         }
    }

    public static Specification<Share> initialCostinRange(int min, int max){
        return valueInBetween(min, max,  );
    }

    public static Specification<Share> finalCostinRange(int cost){
        return CostLesser(cost,"finalCost");
    }

    private static Specification<Share> companyCheck(Spec<Company> spec){

        return (root, query, criteriaBuilder) -> {
           Join<Company,Share> a = root.join(Share_.company);
           return spec.toPredicate(a,query,criteriaBuilder);
           valueInBetween(0,5,);
            return criteriaBuilder.equal(a.get())
        };
    }*/
}