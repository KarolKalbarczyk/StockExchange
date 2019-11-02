package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.StringCriteria.Criteria;
import StockExchange.StockExchange.StringCriteria.OfferCriteria;
import org.hibernate.metamodel.internal.SingularAttributeImpl;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.metamodel.SingularAttribute;
import java.util.LinkedList;
import java.util.List;

public class StringToOfferFilters implements Converter<String, List<Criteria<Offer>>> {
    @Override
    public List<Criteria<Offer>> convert(String s) {
        var data = s.split(",");
        var list = new LinkedList<Criteria<Offer>>();
        list.add(OfferCriteria.costInRange(Double.parseDouble(data[0]),Double.parseDouble(data[1])));
        return list;
    }
}
