package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;
import StockExchange.StockExchange.StringCriteria.*;
import StockExchange.StockExchange.Validators.KeysAreArraysOfSize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

public class Filter {
    private final Entities primary;
    private final List<Filter> secondary;
    @KeysAreArraysOfSize
    private final Map<Attributes,double[]> inRange;
    private final Map<Attributes,String> equalsString;
    private final CriteriaBuilder builder;
    @JsonCreator
    public Filter(@JsonProperty("primary") Entities primary,
                  @JsonProperty("secondary") List<Filter> secondary,
                  @JsonProperty("inRange")Map<Attributes, double[]> inRange,
                  @JsonProperty("equalsString")Map<Attributes, String> equalsString) {
        this.primary = primary;
        this.secondary = secondary;
        this.inRange = inRange;
        this.equalsString = equalsString;
        builder = switch(primary){
            case Offer -> new OfferCriteria();
            case Share -> new ShareCriteria();
            case Trader -> new TraderCriteria();
            case Company -> new CompanyCriteria();
            default -> throw new IllegalArgumentException();
        };
    }

    private List<Criteria> getCriteria(EnumSet<Entities> excluded){
        var list = new LinkedList<Criteria>();
        inRange.forEach((name,range) -> list.add(builder.chooseMethod(name,range[0],range[1])));
        equalsString.forEach((name,value) -> list.add(builder.chooseMethod(name,value)));
        excluded.add(primary);
        var list2 = secondary.stream().filter(f->!excluded.contains(f.primary)).collect(Collectors.toList());
        secondary.stream().filter(f->!excluded.contains(f.primary)).
                forEach(filter ->
                        list.add(builder.chooseJoin(filter.primary,filter.getCriteria(excluded).toArray(new Criteria[0]))));
        return list;
    }

    public List<Criteria> buildCriteria(){
        return getCriteria(EnumSet.noneOf(Entities.class));
    }

    public Entities getPrimary() {
        return primary;
    }

    public List<Filter> getSecondary() {
        return secondary;
    }

    public Map<Attributes, double[]> getInRange() {
        return inRange;
    }

    public Map<Attributes, String> getEqualsString() {
        return equalsString;
    }
}
