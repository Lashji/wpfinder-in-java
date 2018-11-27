package logic;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Settings;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ImgurHandler {

    private final static List<String> ALBUM_HASHES = new ArrayList(Settings.GET_ALBUM_HASHES());

    private List<String> links;

    public ImgurHandler() {

        this.links = new ArrayList<>();

        addAlbumLinks();

    }

    public List<String> getImageLinks() {
        return this.links;
    }

    private void addAlbumLinks() {

        for (String s : ALBUM_HASHES) {
            try {
                addAlbum(s);
            } catch (IOException io) {
                System.out.println(io.getMessage());
            }

        }

    }

    private void addAlbum(String s) throws IOException {
        URL albumUrl = new URL("https://api.imgur.com/3/album/" + s);
        HttpsURLConnection connection = (HttpsURLConnection) albumUrl.openConnection();

        setHttpsConnectionSettings(connection);


        String content = getReq(connection);

        JSONObject obj = new JSONObject(content);

        JSONObject data = obj.getJSONObject("data");
        JSONArray images = data.getJSONArray("images");


        for (Object o : images) {

            if (o instanceof JSONObject) {

                String link = ((JSONObject) o).getString("link");
                links.add(link);

            }
        }


        connection.disconnect();
    }

    private void setHttpsConnectionSettings(HttpsURLConnection connection) throws IOException {

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Client-ID 4abb3979dc91311");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setInstanceFollowRedirects(false);


    }

    private String getReq(HttpsURLConnection connection) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }


}
