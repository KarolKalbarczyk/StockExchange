package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import javax.persistence.metamodel.Attribute;
import java.util.Arrays;
import java.util.List;

public class CompanyCriteria extends GenericCriteria {


    public Criteria<Company> valueInBetween(double min, double max){
        return valueInRange(Company.class, Company_.value,min,max);
    }

    public Criteria<Company> joinShares(Criteria<Share>... criteria){
        return joinCollection(Company_.shares,Company.class, Arrays.asList(criteria),Share.class);
    }

    @Override
    public Criteria<Company> chooseMethod(String attribute, String name) {
        throw new UnsupportedOperationException(NO_SUCH_METHOD);
    }

    @Override
    public  Criteria<Company> chooseMethod(String attribute, double min, double max) {

        return switch (attribute.toLowerCase()){
            case "value" -> valueInBetween(min,max);
            default -> throw exception;
        };
    }

    @Override
    public Criteria<Company> chooseJoin(String attribute, Criteria... criteria) {
        return switch (attribute.toLowerCase()){
            case "shares" -> joinShares(criteria);
            default -> throw exception;
        };
    }

}
