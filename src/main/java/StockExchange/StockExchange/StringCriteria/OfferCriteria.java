package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import java.util.Arrays;

public class OfferCriteria extends GenericCriteria {


    public Criteria<Offer> costInRange(double min,double max){
        return valueInRange(Offer.class, Offer_.cost,min,max);
    }

    @Override
    public <T> Criteria<T> chooseMethod(Attributes attribute, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public  Criteria<Offer> chooseJoin(Entities entity, Criteria... criteria) {
        return switch(entity){
            case Share -> joinShare(criteria);
            case Trader -> joinTrader(criteria);
            default -> throw exception;
        };
    }

    public Criteria<Offer> joinShare(Criteria<Share>... criteria){
        return join(Offer_.share,Offer.class, Arrays.asList(criteria),Share.class);
    }

    public Criteria<Offer> joinTrader(Criteria<Trader>... criteria){
        return join(Offer_.owner,Offer.class, Arrays.asList(criteria),Trader.class);
    }

    @Override
    public  Criteria<Offer> chooseMethod(Attributes attribute, double min, double max) {
        return switch (attribute){
            case Cost -> costInRange(min,max);
            default -> throw exception;
        };
    }
}
