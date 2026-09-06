import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Main {
    private static final String API_TOKEN = System.getenv("DISCOGS_TOKEN");
    private static final String SEARCH_URL = "https://api.discogs.com/database/search?q=Nirvana&type=artist";
    public static void main(String[]args) throws Exception{
        //System.out.println(API_TOKEN != null);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SEARCH_URL))
                .header("Authorization", "Discogs token=" + API_TOKEN)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());


        JsonObject json =
                JsonParser.parseString(response.body()).getAsJsonObject();
        System.out.println(response.body());


    }
}
