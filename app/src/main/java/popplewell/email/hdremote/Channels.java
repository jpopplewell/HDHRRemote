package popplewell.email.hdremote;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2/3/16.
 */
public class Channels {
    private static Channels sChannels;

    private List<Station> mStations;

    public static Channels get(Context context) {
        if (sChannels == null) {
            sChannels = new Channels(context);
        }
        return sChannels;
    }

    private Channels(Context context) {




mStations = new ArrayList<>();

/*
        for (int i = 0; i<35; i++) {
            Station station = new Station();
            station.channel = Integer.toString(i);
            mStations.add(station);
        }*/
    }

    public void setStations(List<Station> station) {
        this.mStations = station;
    }

    public List<Station> getStations() {
        return mStations;
    }

    public Station getStation(String channel) {
        for (Station station : mStations) {
            if (station.channel.equals(channel)) {
                return station;
            }
        }
        return null;
    }




}
