package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Entities.BasicEntity;

import javax.persistence.Entity;
import java.util.Locale;

@Entity
public class Response extends BasicEntity {
    private String name;
    private Locale locale;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
