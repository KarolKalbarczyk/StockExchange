package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Entities.Share;

import javax.persistence.metamodel.Attribute;
import java.util.Arrays;
import java.util.List;

public class OfferCriteria extends GenericCriteria {


    public Criteria<Offer> costInRange(double min,double max){
        return valueInRange(Offer.class, Offer_.cost,min,max);
    }

    @Override
    public <T> Criteria<T> chooseMethod(String attribute, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T, K> Criteria<T> chooseJoin(String attribute, List<Criteria<?>> criteria) {
        return null;
    }

    public Criteria<Offer> joinShare(Criteria<Share>... criteria){
        return join(Offer_.share,Offer.class, Arrays.asList(criteria),Share.class);
    }



    @Override
    public  Criteria<Offer> chooseMethod(String attribute, double min, double max) {
        return costInRange(min,max);
    }
}
