package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.StringCriteria.*;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Filter {
    String primary;
    List<Filter> secondary;
    Map<String,double[]> inRange;
    Map<String,String> equalsString;
    CriteriaBuilder builder;
    List<String> excluded = new LinkedList<>();
    @JsonCreator
    public Filter(String primary, List<Filter> secondary, Map<String, double[]> inRange, Map<String, String> equalsString) {
        this.primary = primary;
        this.secondary = secondary;
        this.inRange = inRange;
        this.equalsString = equalsString;
        excluded.add(primary);
        builder = switch(primary){
            case "offer" -> new OfferCriteria();
            case "share" -> new ShareCriteria();
            case "trader" -> new TraderCriteria();
            case "company" -> new CompanyCriteria();
            default -> throw new IllegalArgumentException();
        };
    }

    public List<Criteria<?>> buildCriteria(){
        var list = new LinkedList<Criteria<?>>();
        inRange.forEach((name,range) -> list.add(builder.chooseMethod(name,range[0],range[1])));
        equalsString.forEach((name,value) -> list.add(builder.chooseMethod(name,value)));
        secondary.stream().filter(Filter::isExcluded).forEach(
                filter -> {filter.addToExcluded(primary);
                list.add(builder.chooseJoin(filter.primary,filter.buildCriteria()));});
        return list;
    }

    boolean isExcluded(){
        return excluded.contains(primary);
    }

    void addToExcluded(String primary){
        excluded.add(primary);
    }


}
