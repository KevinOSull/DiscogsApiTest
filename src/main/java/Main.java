import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private static final String API_TOKEN = System.getenv("DISCOGS_TOKEN");
    private static final String SEARCH_URL = "https://api.discogs.com/artists/22217/releases";
    private static String url;
    private static String year;
    private static ArrayList<ReleaseData> releaseData = new ArrayList<>();
    private static final String FOLDER_PATH = "src/main/resources";
    private static final String FILE_PATH = "src/main/resources/releaseData.csv";
    public static void main(String[]args) throws Exception{
        checkIfFileExists();
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
            //System.out.println(response.body());


            JsonObject json =
                    JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray releases = json.getAsJsonArray("releases");

            for(int i = 0; i < releases.size(); i++){
                JsonObject rel = releases.get(i).getAsJsonObject();
                String artist = rel.get("artist").getAsString();
                String title = rel.get("title").getAsString();
                if(rel.has("year")){
                  year = rel.get("year").getAsString();
                }else{
                    year = "Unknown";
                }
                ReleaseData r = new ReleaseData(artist,title,year);
                releaseData.add(r);
            }
        }
        releaseData.sort((r1,r2)->r1.getYear().compareTo(
                r2.getYear()));
        writeDataToFile();
        for(ReleaseData obj : releaseData){
            System.out.println("Artist: " + obj.getArtist() +  " Title: " + obj.getTitle()  + " Year: " + obj.getYear() );
        }

    }

    private static void checkIfFileExists() throws IOException {
        File file = new File(FILE_PATH);
        File folder = new File(FOLDER_PATH);
        if(!folder.exists()){
            folder.mkdir();
        }

        if(!file.exists()){
            FileWriter createNewFile = new FileWriter(file);
            createNewFile.write("Artist,Title,Year\n");
            createNewFile.close();
        }
    }

    private static void writeDataToFile()throws IOException{
        File file = new File(FILE_PATH);
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < releaseData.size(); i++){
            bw.write("\"");
            bw.write(releaseData.get(i).getArtist());
            bw.write("\"");
            bw.write(",");
            bw.write("\"");
            bw.write(releaseData.get(i).getTitle());
            bw.write("\"");
            bw.write(",");
            bw.write("\"");
            bw.write(releaseData.get(i).getYear());
            bw.write("\"");
            bw.newLine();
        }
        bw.close();
    }
}
