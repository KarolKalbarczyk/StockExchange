package StockExchange.StockExchange.stringCriteria;

import StockExchange.StockExchange.entities.*;

import java.util.Arrays;

public class CompanyCriteriaBuilder extends GenericCriteriaBuilder {


    public Criteria<Company> valueInBetween(double min, double max) {
        return valueInRange(Company.class, Company_.value, min, max);
    }

    public Criteria<Company> joinShares(Criteria<Share>... criteria) {
        return joinCollection(Company_.shares, Company.class, Arrays.asList(criteria), Share.class);
    }

    @Override
    public Criteria<Company> chooseMethod(Attributes attribute, String name) {
        throw new UnsupportedOperationException(NO_SUCH_METHOD);
    }

    @Override
    public Criteria<Company> chooseMethod(Attributes attribute, double min, double max) {
        return switch (attribute) {
            case Value -> valueInBetween(min, max);
            default -> throw exception;
        };
    }

    @Override
    public Criteria<Company> chooseJoin(Entities entity, Criteria... criteria) {
        return switch (entity) {
            case Share -> joinShares(criteria);
            default -> throw exception;
        };
    }

}
