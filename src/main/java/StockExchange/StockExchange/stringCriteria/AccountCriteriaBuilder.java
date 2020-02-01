package StockExchange.StockExchange.stringCriteria;

import StockExchange.StockExchange.entities.Account;
//import StockExchange.StockExchange.StockExchange.StockExchange.StockExchange.Entities.Entities.Account_;
import StockExchange.StockExchange.entities.Account_;
import StockExchange.StockExchange.entities.Attributes;
import StockExchange.StockExchange.entities.Entities;

public class AccountCriteriaBuilder extends GenericCriteriaBuilder {

    public Criteria<Account> loginEquals(String login) {
        return stringEqual(Account.class, Account_.login, login);
    }

    @Override
    public <T> Criteria<T> chooseMethod(Attributes attribute, String name) {
        return null;
    }

    @Override
    public <T> Criteria<T> chooseMethod(Attributes attribute, double min, double max) {
        return null;
    }

    @Override
    public <T> Criteria<T> chooseJoin(Entities entitiy, Criteria... criteria) {
        return null;
    }
}
