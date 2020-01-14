package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;
import StockExchange.StockExchange.Validators.KeysAreArraysOfSize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;


public class Filter {
    private final Entities primary;
    private final List<Filter> secondary;
    @KeysAreArraysOfSize
    private final EnumMap<Attributes, double[]> inRange;
    private final EnumMap<Attributes, String> equalsString;
    private final CriteriaBuilder builder;

    @JsonCreator
    public Filter(@JsonProperty("primary") Entities primary,
                  @JsonProperty("secondary") List<Filter> secondary,
                  @JsonProperty("inRange") EnumMap<Attributes, double[]> inRange,
                  @JsonProperty("equalsString") EnumMap<Attributes, String> equalsString) {
        this.primary = primary;
        this.secondary = secondary;
        this.inRange = inRange;
        this.equalsString = equalsString;
        builder = chooseBuilder();
    }

    private CriteriaBuilder chooseBuilder() {
        return switch (primary) {
            case Offer -> new OfferCriteriaBuilder();
            case Share -> new ShareCriteriaBuilder();
            case Trader -> new TraderCriteriaBuilder();
            case Company -> new CompanyCriteriaBuilder();
            default -> throw new IllegalArgumentException();
        };
    }

    private List<Criteria> getCriteria(EnumSet<Entities> excluded) {
        var list = new LinkedList<Criteria>();
        excluded.add(primary);
        inRange.forEach((name, range) -> list.add(builder.chooseMethod(name, range[0], range[1])));
        equalsString.forEach((name, value) -> list.add(builder.chooseMethod(name, value)));
        secondary.stream()
                .filter(filter -> !excluded.contains(filter.primary))
                .forEach(filter ->
                        list.add(builder.chooseJoin(filter.primary,
                                filter.getCriteria(excluded).toArray(new Criteria[0]))
                        )
                );
        return list;
    }

    public List<Criteria> buildCriteria() {
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
