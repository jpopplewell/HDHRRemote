package popplewell.email.hdremote;

import android.support.v4.app.Fragment;

/**
 * Created by john on 2/3/16.
 */
public class ChannelsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChannelsFragment();
    }
}
