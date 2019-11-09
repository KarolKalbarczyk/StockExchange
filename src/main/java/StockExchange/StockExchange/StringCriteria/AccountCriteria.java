package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Account;
import StockExchange.StockExchange.Entities.Account_;

public class AccountCriteria extends GenericCriteria {

    public Criteria<Account> loginEquals(String login){
        return stringEqual(Account.class, Account_.login,login);
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
    public <T> Criteria<T> chooseJoin(String attribute, Criteria... criteria) {
        return null;
    }
}
