package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

public class ShareCriteria extends GenericCriteria {
    public static Criteria<Share> joinCompany(Criteria<Company> criteria){
        return join(Share_.company,Share.class,criteria,Company.class);
    }

    public static Criteria<Share> joinOwner(Criteria<Trader> criteria){
        return join(Share_.owner,Share.class,criteria,Trader.class);
    }

    public static Criteria<Share> testcheck(double min, double max){
        return valueInRange(Share.class, Share_.test,min,max);

    }
}
