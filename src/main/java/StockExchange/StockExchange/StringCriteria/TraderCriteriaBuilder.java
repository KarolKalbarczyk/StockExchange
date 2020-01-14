package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.*;

import java.util.Arrays;

public class TraderCriteriaBuilder extends GenericCriteriaBuilder {
    CompanyCriteriaBuilder comp = new CompanyCriteriaBuilder();

    public Criteria<Trader> wealthInRange(double min, double max) {
        return valueInRange(Trader.class, Trader_.wealth, min, max);
    }

    public Criteria<Trader> nameEquals(String name) {
        return stringEqual(Trader.class, Trader_.name, name);
    }


    public Criteria<Trader> joinShares(Criteria<Share>... criteria) {
        return joinCollection(Trader_.ownedShares, Trader.class, Arrays.asList(criteria), Share.class);
    }

    public Criteria<Trader> joinOffer(Criteria<Offer>... criteria) {
        return joinCollection(Trader_.offers, Trader.class, Arrays.asList(criteria), Offer.class);
    }

    @Override
    public Criteria<? extends Trader> chooseMethod(Attributes attribute, String name) {
        return switch (attribute) {
            case Name -> nameEquals(name);
            default -> throw exception;
        };
    }

    @Override
    public Criteria<? extends Trader> chooseMethod(Attributes attribute, double min, double max) {
        return switch (attribute) {
            case Wealth -> wealthInRange(min, max);
            case Value -> comp.valueInBetween(min, max);
            default -> throw exception;
        };
    }

    @Override
    public Criteria<? extends Trader> chooseJoin(Entities entity, Criteria... criteria) {
        return switch (entity) {
            case OwnedShares -> joinShares(criteria);
            case Offer -> joinOffer(criteria);
            case Share -> comp.joinShares(criteria);
            default -> throw exception;
        };
    }

    public Criteria<Trader> joinAccount(Criteria<Account>... criteria) {
        return join(Trader_.account, Trader.class, Arrays.asList(criteria), Account.class);
    }
}
