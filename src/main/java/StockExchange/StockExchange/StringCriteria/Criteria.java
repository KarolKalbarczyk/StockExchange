package StockExchange.StockExchange.StringCriteria;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Criteria<T> {

    private List<String> where = new ArrayList<>();
    private List<String> join = new ArrayList<>();
    private List<String> select = new ArrayList<>();

    void addToWhere(String criteria){
        where.add(criteria);
    }

    void addJoin(String join){
        this.join.add(join);
    }

    void addSelectAll(List<String> select){
        this.select.addAll(select);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Criteria)) return false;
        Criteria<?> criteria = (Criteria<?>) o;
        return Objects.equals(where, criteria.where) &&
                Objects.equals(join, criteria.join) &&
                Objects.equals(select, criteria.select);
    }

    @Override
    public int hashCode() {
        return Objects.hash(where, join, select);
    }

    void addWhereAll(List<String> where){
        this.where.addAll(where);
    }

    void addJoinAll(List<String> join){
        this.join.addAll(join);
    }

    void addSelect(String select){
        this.select.add(select);
    }

    public List<String> getWhere() {
        return where;
    }

    public List<String> getJoin() {
        return join;
    }

    public List<String> getSelect() {
        return select;
    }
}
