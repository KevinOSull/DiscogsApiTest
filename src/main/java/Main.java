import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Main {
    private static final String API_TOKEN = System.getenv("DISCOGS_TOKEN");
    private static final String SEARCH_URL = "https://api.discogs.com/artists/22217/releases";
    private static String url;
    private static String year;
    public static void main(String[]args) throws Exception{

        for(int j = 1; j <= 28; j++){
            url = SEARCH_URL + "?page=" + j;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Discogs token=" + API_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());


            JsonObject json =
                    JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray releases = json.getAsJsonArray("releases");

            for(int i = 0; i < releases.size(); i++){
                JsonObject rel = releases.get(i).getAsJsonObject();
                String title = rel.get("title").getAsString();
                if(rel.has("year")){
                  year = rel.get("year").getAsString();
                }else{
                    year = "Unknown";
                }
                System.out.println("Title: " + title  + " Year: " + year );
            }
        }






    }
}
