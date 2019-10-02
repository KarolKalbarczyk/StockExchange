package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Entities.Trader_;

public class TraderCriteria extends GenericCriteria {
    public static Criteria<Trader> wealthInRange(double min, double max){
        return valueInRange(Trader.class, Trader_.wealth,min,max);
    }
}
