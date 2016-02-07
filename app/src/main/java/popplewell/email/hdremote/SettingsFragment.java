package popplewell.email.hdremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by john on 2/6/16.
 */
public class SettingsFragment extends Fragment{
    public static final String TAG = "SettingsFragment";

    private EditText mHostEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mHostEditText = (EditText) view.findViewById(R.id.editText);
        mHostEditText.setText(SettingsPreferences.getStoredQuery(getActivity()));
        mHostEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        mHostEditText.setImeActionLabel("Save Setting", KeyEvent.KEYCODE_ENTER);

        mHostEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER) {
                    SettingsPreferences.setStoredQuery(getActivity(), mHostEditText.getText().toString());
                    Toast.makeText(getActivity(), mHostEditText.getText(), Toast.LENGTH_SHORT).show();
                    handled = true;
                }
                return handled;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_settings, menu);
    }


}
