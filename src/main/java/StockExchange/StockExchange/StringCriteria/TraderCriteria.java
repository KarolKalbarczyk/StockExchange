package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.List;

public class TraderCriteria extends GenericCriteria {
    public Criteria<Trader> wealthInRange(double min, double max){
        return valueInRange(Trader.class, Trader_.wealth,min,max);
    }

    public Criteria<Trader> joinShare(Criteria<Share> criteria){
        return joinCollection(Trader_.ownedShares,Trader.class,criteria, Share.class);
    }

    @Override
    public <T> Criteria<T> chooseMethod(String attribute, String name) {
        return null;
    }

    @Override
    public <T> Criteria<T> chooseMethod(String attribute, double min, double max) {
        return null;
    }

    @Override
    public <T, K> Criteria<T> chooseJoin(String attribute, List<Criteria<?>> criteria) {
        return null;
    }
}
