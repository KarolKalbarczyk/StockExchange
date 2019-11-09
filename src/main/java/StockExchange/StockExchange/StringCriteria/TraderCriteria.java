package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import javax.persistence.metamodel.Attribute;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TraderCriteria extends GenericCriteria {
    CompanyCriteria comp = new CompanyCriteria();

    public Criteria<Trader> wealthInRange(double min, double max){
        return valueInRange(Trader.class, Trader_.wealth,min,max);
    }

    public Criteria<Trader> nameEquals(String name){
        return stringEqual(Trader.class,Trader_.name,name);
    }


    public Criteria<Trader> joinShare(Criteria<Share>... criteria){
        return joinCollection(Trader_.ownedShares,Trader.class,Arrays.asList(criteria), Share.class);
    }

    public Criteria<Trader> joinOffer(Criteria<Offer>... criteria){
        return joinCollection(Trader_.offers,Trader.class, Arrays.asList(criteria), Offer.class);
    }

    @Override
    public Criteria<? extends Trader> chooseMethod(String attribute, String name) {
        return switch (attribute.toLowerCase()){
            case "name" -> nameEquals(name);
            default -> throw exception;
        };
    }

    @Override
    public  Criteria<? extends Trader> chooseMethod(String attribute, double min, double max) {
        return switch (attribute.toLowerCase()){
            case "wealth" -> wealthInRange(min,max);
            case "value" -> comp.valueInBetween(min,max);
            default -> throw exception;
        };
    }

    @Override
    public Criteria<? extends Trader> chooseJoin(String attribute, Criteria... criteria) {
        return switch (attribute.toLowerCase()){
            case "ownedShares" -> joinShare(criteria);
            case "offer" -> joinOffer(criteria);
            case "shares" -> comp.joinShares(criteria);
            default -> throw exception;
        };
    }
}
