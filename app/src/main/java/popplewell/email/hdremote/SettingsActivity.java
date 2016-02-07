package popplewell.email.hdremote;

import android.support.v4.app.Fragment;

/**
 * Created by john on 2/6/16.
 */
public class SettingsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SettingsFragment();
    }
}