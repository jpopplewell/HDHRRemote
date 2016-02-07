package popplewell.email.hdremote;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by john on 2/6/16.
 */
public class ConnectionErrorFragment  extends DialogFragment {

    public static final String EXTRA_URL = "popplewell.email.hdremote.url";

    public static ConnectionErrorFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);

        ConnectionErrorFragment fragment = new ConnectionErrorFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String url = (String) getArguments().getString("url");


        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_connection_error, null);

        final EditText urlEditText = (EditText) v.findViewById(R.id.editUrl);
        urlEditText.setText(url);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Connection Failed")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Reconnect",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = urlEditText.getText().toString();
                                sendResult(Activity.RESULT_OK, url);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, String url) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_URL, url);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
