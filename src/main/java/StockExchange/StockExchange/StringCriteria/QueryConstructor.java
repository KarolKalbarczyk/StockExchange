package StockExchange.StockExchange.StringCriteria;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class QueryConstructor {

    @PersistenceContext
    EntityManager manager;

    public <T> List<T> createQuery(Class<T> clazz, Criteria<T>... criterias){
        var classname = clazz.getSimpleName();
        var lowercase = classname.toLowerCase();
        String select = String.format("select %s from %s %s" ,lowercase,classname,lowercase);
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder(" where");
        var whereUsed = false;
        for(var criteria:criterias){
            analyzeList(criteria.where(),join,where,whereUsed);
        }
        where.setLength(where.length()-3);
        String query = select+join.toString()+where.toString();
        //var query = "select s from Share s join s.company b where b.value = 10";
        //var query = "select share from Share share join Share.company company where share.test between 5.000000 and 15.000000 and value between 5.000000 and 15.000000";
        return manager.createQuery(query).getResultList();
    }

    private void analyzeList(List<String> list,StringBuilder join,StringBuilder where,boolean whereUsed){
        for(var str: list){
            if(str.charAt(1) == 'j') join.append(str);
            else {
                where.append(str).append(" and");
            }
        }
    }
}
