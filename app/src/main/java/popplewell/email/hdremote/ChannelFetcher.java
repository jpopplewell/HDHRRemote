package popplewell.email.hdremote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by john on 2/3/16.
 */
public class ChannelFetcher {

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Station> getStations() {

        try {
            String jsonString = getUrlString("http://192.168.0.180:8000/channels");
            Gson gson = new Gson();

            List<Station> stations = new ArrayList<Station>();
            List<String> strStations = gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
            for (Iterator<String> i = strStations.iterator(); i.hasNext();) {
                Station Sta = new Station();
                Sta.channel = i.next();
                stations.add(Sta);

            }

            return stations;

    } catch (IOException ioe){}
        return null;




} }
