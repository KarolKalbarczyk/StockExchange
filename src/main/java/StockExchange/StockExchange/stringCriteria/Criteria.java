package StockExchange.StockExchange.stringCriteria;

import java.util.*;

public class Criteria<T> {

    private final List<String> where = new ArrayList<>();
    private final List<String> join = new ArrayList<>();
    private final List<String> select = new ArrayList<>();

    public Map<String, String> getParamsToInject() {
        return paramsToInject;
    }

    private final Map<String,String> paramsToInject = new HashMap<>();

    void addToWhere(String criteria) {
        where.add(criteria);
    }

    void addJoin(String join) {
        this.join.add(join);
    }

    void addSelectAll(List<String> select) {
        this.select.addAll(select);
    }

    void addToParam(String key, String value){
        paramsToInject.put(key, value);
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

    void addWhereAll(List<String> where) {
        this.where.addAll(where);
    }

    void addJoinAll(List<String> join) {
        this.join.addAll(join);
    }

    void addSelect(String select) {
        this.select.add(select);
    }

    void addParamAll(Map<String, String> paramsToInject){this.paramsToInject.putAll(paramsToInject);}

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
