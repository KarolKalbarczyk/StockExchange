package StockExchange.StockExchange.Money;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.j2objc.annotations.J2ObjCIncompatible;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public enum Currency {
    USA("USD"),
    PL("PLN");


    Currency(String name){this.name = name;};

    private final String name;
    private final String HTTP_ADRESS = "https://api.exchangerate-api.com/v4/latest/USD";

    public double getRate() {
        try{
            URL url = new URL(HTTP_ADRESS);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), Charset.defaultCharset()))) {
                var jsonText = readAll(br);
                var json = new JSONObject(jsonText);
                var rates = json.get("rates");
                return ((JSONObject)rates).getDouble(name);
            }
        }
        catch (IOException ignored){}
        return 0;

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
