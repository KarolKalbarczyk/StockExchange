package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.StringCriteria.*;
import StockExchange.StockExchange.Validators.KeysAreArraysOfSize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter {
    private final String primary;
    private final List<Filter> secondary;
    @KeysAreArraysOfSize
    private final Map<String,double[]> inRange;
    private final Map<String,String> equalsString;
    private final CriteriaBuilder builder;
    @JsonCreator
    public Filter(@JsonProperty("primary") String primary,
                  @JsonProperty("secondary") List<Filter> secondary,
                  @JsonProperty("inRange")Map<String, double[]> inRange,
                  @JsonProperty("equalsString")Map<String, String> equalsString) {
        this.primary = primary;
        this.secondary = secondary;
        this.inRange = inRange;
        this.equalsString = equalsString;
        builder = switch(primary.toLowerCase()){
            case "offer" -> new OfferCriteria();
            case "share" -> new ShareCriteria();
            case "trader" -> new TraderCriteria();
            case "company" -> new CompanyCriteria();
            default -> throw new IllegalArgumentException();
        };
    }

    private List<Criteria> getCriteria(Set<String> excluded){
        var list = new LinkedList<Criteria>();
        inRange.forEach((name,range) -> list.add(builder.chooseMethod(name,range[0],range[1])));
        equalsString.forEach((name,value) -> list.add(builder.chooseMethod(name,value)));
        excluded.add(primary);
        secondary.stream().filter(f->f.isExcluded(excluded)).
                forEach(filter ->
                        list.add(builder.chooseJoin(filter.primary,filter.getCriteria(excluded).toArray(new Criteria[0]))));
        return list;
    }

    public List<Criteria> buildCriteria(){
        return getCriteria(new HashSet<>());
    }

    boolean isExcluded(Set<String> excluded){
        return !excluded.contains(primary);
    }

    public String getPrimary() {
        return primary;
    }

    public List<Filter> getSecondary() {
        return secondary;
    }

    public Map<String, double[]> getInRange() {
        return inRange;
    }

    public Map<String, String> getEqualsString() {
        return equalsString;
    }
}
