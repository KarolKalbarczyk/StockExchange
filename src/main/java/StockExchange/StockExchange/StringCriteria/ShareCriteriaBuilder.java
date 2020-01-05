package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import java.util.Arrays;

public class ShareCriteriaBuilder extends GenericCriteriaBuilder {
    @SafeVarargs
    public final Criteria<Share> joinCompany(Criteria<Company>... criteria){
        return join(Share_.company,Share.class,Arrays.asList(criteria),Company.class);
    }

    @SafeVarargs
    public final Criteria<Share> joinOwner(Criteria<Trader>... criteria){
        return join(Share_.owner,Share.class,Arrays.asList(criteria),Trader.class);
    }

    @SafeVarargs
    public final Criteria<Share> joinOffer(Criteria<Offer>... criteria){
        return join(Share_.offer,Share.class,Arrays.asList(criteria),Offer.class);

    }

    public Criteria<Share> testcheck(double min, double max){
        return valueInRange(Share.class, Share_.test,min,max);

    }

    @Override
    public  Criteria<Share> chooseMethod(Attributes attribute, String name) {
        throw exception;
    }

    @Override
    public  Criteria<Share> chooseMethod(Attributes attribute, double min, double max) {
        return testcheck(min,max);
    }

    @Override
    public  Criteria<Share> chooseJoin(Entities entity, Criteria... criteria) {
        return switch (entity){
            case Offer -> joinOffer(criteria);
            case Trader -> joinOwner(criteria);
            case Company -> joinCompany(criteria);
            default -> throw exception;
        };
    }


}
