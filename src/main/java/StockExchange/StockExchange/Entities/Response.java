package StockExchange.StockExchange.Entities;



import javax.persistence.Entity;
import java.util.Locale;

@Entity
public class Response extends BasicEntity {
    private String name;
    private Locale locale;
    private String text;

    public String getText() {
        return text;
    }
}