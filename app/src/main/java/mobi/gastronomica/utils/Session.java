package mobi.gastronomica.utils;

import android.app.Activity;

import static mobi.gastronomica.utils.Logs.LOGE;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class Session {

    private static final String TAG = makeLogTag(Session.class);

    public static boolean SessionExists(Activity activity) {
        boolean session = false;
        try {
             session = PreferenceManager.getBoolean(Constants.LOGIN_KEY, activity);
        } catch (Exception e) {
            LOGE(TAG, e.toString());
        }
        return session;
    }       

}//end class
