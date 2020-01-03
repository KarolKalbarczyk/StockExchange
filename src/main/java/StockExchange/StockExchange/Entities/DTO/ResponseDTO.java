package StockExchange.StockExchange.Entities.DTO;

import StockExchange.StockExchange.Entities.Response;
import java.lang.String;
import java.util.Locale;

public class ResponseDTO {
    private String name;

    private String text;

    private Locale locale;

    ResponseDTO(Response Response) {
        this.name = Response.getName();
        this.text = Response.getText();
        this.locale = Response.getLocale();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    Response getEntity() {
        var Response = new Response();
        Response.setName(name);
        Response.setText(text);
        Response.setLocale(locale);
        return Response;
    }
}
