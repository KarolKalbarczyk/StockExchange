package StockExchange.StockExchange.StringCriteria;

import java.util.LinkedList;
import java.util.List;

@FunctionalInterface
public interface Criteria<T> {
    List<String> where();
}
