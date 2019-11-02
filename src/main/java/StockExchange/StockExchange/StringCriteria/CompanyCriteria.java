package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Company_;
import StockExchange.StockExchange.Entities.Trader;
import StockExchange.StockExchange.Entities.Trader_;

import javax.persistence.metamodel.Attribute;
import java.util.List;

public class CompanyCriteria extends GenericCriteria {


    public Criteria<Company> valueInBetween(double min, double max){
        return valueInRange(Company.class, Company_.value,min,max);
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
