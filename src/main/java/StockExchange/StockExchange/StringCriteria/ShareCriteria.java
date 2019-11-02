package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import javax.persistence.metamodel.Attribute;
import java.util.List;

public class ShareCriteria extends GenericCriteria {
    public Criteria<Share> joinCompany(List<Criteria<Company>> criteria){
        return join(Share_.company,Share.class,criteria,Company.class);
    }

    public Criteria<Share> joinOwner(List<Criteria<Trader>> criteria){
        return join(Share_.owner,Share.class,criteria,Trader.class);
    }

    public Criteria<Share> testcheck(double min, double max){
        return valueInRange(Share.class, Share_.test,min,max);

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
