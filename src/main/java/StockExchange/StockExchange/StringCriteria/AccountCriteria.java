package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Account;
//import StockExchange.StockExchange.StockExchange.StockExchange.StockExchange.Entities.Entities.Account_;
import StockExchange.StockExchange.Entities.Account_;
import StockExchange.StockExchange.Entities.Attributes;
import StockExchange.StockExchange.Entities.Entities;

public class AccountCriteria extends GenericCriteria {

    public Criteria<Account> loginEquals(String login){
        return stringEqual(Account.class, Account_.login,login);
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
