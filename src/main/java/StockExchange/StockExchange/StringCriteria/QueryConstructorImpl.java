package StockExchange.StockExchange.StringCriteria;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class QueryConstructorImpl implements QueryConstructor {

    private final String AND = " and ";
    private final String WHERE = " where";
    private final String EMPTY = "";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public <T> String createQuery(Class<T> clazz, Criteria<T>... criterias) {
        var classname = clazz.getSimpleName();
        var lowercase = classname.toLowerCase();
        var select = String.format("select %s from %s %s", lowercase, classname, lowercase);
        var join = new StringBuilder();
        var where = new StringBuilder(WHERE);
        analyzeCriteria(criterias, join, where);
        String query = select + join.toString() + where.toString();
        return query;
    }

    @Override
    public <T> List<T> executeQuery(String query, int offset, int limit) {
        Query query1 = switch (limit) {
            case 0 -> manager.createQuery(query);
            default -> manager.createQuery(query).setFirstResult(offset).setMaxResults(limit);
        };
        return query1.getResultList();
    }

    private void analyzeCriteria(Criteria[] criterias, StringBuilder join, StringBuilder where) {
        for (var criteria : criterias) {
            analyzeList(criteria.getJoin(), join, EMPTY);
            analyzeList(criteria.getWhere(), where, AND);
        }
        cutTheLastAnd(where);
    }

    private void analyzeList(List<String> list, StringBuilder builder, String interspace) {
        for (var str : list) {
            builder.append(str);
            builder.append(interspace);
        }
    }

    private void cutTheLastAnd(StringBuilder where) {
        final var LENGTH_OF_AND = 4;
        where.setLength(where.length() - LENGTH_OF_AND);
    }
}
