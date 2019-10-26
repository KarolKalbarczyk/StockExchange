package StockExchange.StockExchange.Money;

public class MoneyFactory {
    private MoneyFactory(){}

    public static Money getMoney(long amount){
        return new MoneyImpl(amount);
    }
    public static Money getMoney(String amount){
        return new MoneyImpl(amount);
    }
}
