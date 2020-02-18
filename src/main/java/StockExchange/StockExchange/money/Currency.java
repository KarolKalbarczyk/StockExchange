package StockExchange.StockExchange.money;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public enum Currency {
    USA("USD"),
    PL("PLN");

    private final String name;
    private final String HTTP_ADRESS = "https://api.exchangerate-api.com/v4/latest/USD";

    Currency(String name) {
        this.name = name;
    }

    public double getRate() {
        try {
            URL url = new URL(HTTP_ADRESS);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), Charset.defaultCharset()))) {
                var jsonText = readAll(br);
                var json = new JSONObject(jsonText);
                var rates = json.get("rates");
                return ((JSONObject) rates).getDouble(name);
            }
        } catch (IOException ignored) {}
        return 0;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
