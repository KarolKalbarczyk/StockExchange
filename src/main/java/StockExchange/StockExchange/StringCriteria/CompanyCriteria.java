package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Company_;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Entities.Trader_;

public class CompanyCriteria extends GenericCriteria {


    public static Criteria<Company> valueInBetween(double min, double max){
        return valueInRange(Company.class, Company_.value,min,max);
    }
}
