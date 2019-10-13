package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import java.util.Collection;

public class TraderCriteria extends GenericCriteria {
    public static Criteria<Trader> wealthInRange(double min, double max){
        return valueInRange(Trader.class, Trader_.wealth,min,max);
    }

    public static Criteria<Trader> joinShare(Criteria<Share> criteria){
        return joinCollection(Trader_.ownedShares,Trader.class,criteria, Share.class);
    }

}
