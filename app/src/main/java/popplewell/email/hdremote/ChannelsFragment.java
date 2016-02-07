package popplewell.email.hdremote;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by john on 2/3/16.
 */
public class ChannelsFragment extends Fragment {
    private static final String TAG = "ChannelsFragment";

    private RecyclerView mChannelRecyclerView;
    private ChannelAdapter mAdapter;

    Channels channels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        //updateUI();
        new FetchChannelsTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels, container, false);
        mChannelRecyclerView = (RecyclerView) view
                .findViewById(R.id.channel_recycler_view);
        mChannelRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 0) {
            String url = data.getStringExtra(ConnectionErrorFragment.EXTRA_URL);
            SettingsPreferences.setStoredQuery(getActivity(), url);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_channels, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        channels = Channels.get(getActivity());
        List<Station> stations = channels.getStations();

        mAdapter = new ChannelAdapter(stations);
        mChannelRecyclerView.setAdapter(mAdapter);
    }

    private class ChannelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Station mStation;
        public TextView mChannelTextView;

        public ChannelHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mChannelTextView = (TextView) itemView.findViewById(R.id.list_item_channel_text_view);
        }

        @Override
        public void onClick(View itemView) {
            Toast.makeText(getActivity(), mStation.channel + " Changed", Toast.LENGTH_SHORT).show();
            new ChangeChannelsTask().execute(mStation.channel);
        }

        public void bindStation(Station station) {
            mStation = station;
            mChannelTextView.setText(mStation.channel);
        }

    }

    private class ChannelAdapter extends RecyclerView.Adapter<ChannelHolder> {

        private List<Station> mStations;

        public ChannelAdapter(List<Station> stations) {
            mStations = stations;
        }

        @Override
        public ChannelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_station, parent, false);
            return new ChannelHolder(view);
        }

        @Override
        public void onBindViewHolder(ChannelHolder holder, int position) {
            Station station = mStations.get(position);
            holder.bindStation(station);
        }

        @Override
        public int getItemCount() {
            return mStations.size();
        }

    }

    private String getServerSetting() {
        return SettingsPreferences.getStoredQuery(getActivity());

    }

    private void showErrorDialog() {
        FragmentManager manager = getFragmentManager();
        ConnectionErrorFragment dialog = ConnectionErrorFragment.newInstance(getServerSetting());
        dialog.setTargetFragment(ChannelsFragment.this, 0);
        dialog.show(manager, "Title?");

    }

    public class FetchChannelsTask extends AsyncTask<Void,Void,List<Station>> {

        @Override
        protected List<Station> doInBackground(Void... params) {

            try {
                String sUrl = getServerSetting() + "/channels";
                String result = new ChannelFetcher().getUrlString(sUrl);
                Log.i(TAG, result);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL ", ioe);
                showErrorDialog();
            }

            return new ChannelFetcher().getStations();
        }

        @Override
        protected void onPostExecute(List<Station> stations) {
            channels.setStations(stations);
            updateUI();
        }

    }
    public class ChangeChannelsTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... channel) {
            try {
                String sUrl = getServerSetting() + "/channel/" + channel[0];
                String result = new ChannelFetcher().getUrlString(sUrl);
                Log.i(TAG, result);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL ", ioe);
                showErrorDialog();
            }
            return null;
        }

/*        @Override
        protected void onPostExecute() {

        }*/

    }

}

