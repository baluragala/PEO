package dpparking.androidapp.peo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by RBK on 2/14/14.
 */
public class Helper {

    public static String calculateTime(long duration) {
        int day = (int) TimeUnit.MINUTES.toDays(duration);
        long hour = TimeUnit.MINUTES.toHours(duration) -
                TimeUnit.DAYS.toHours(day);
        long minute = TimeUnit.MINUTES.toMinutes(duration) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(duration));

        String days = (day+"").length() == 1 ? "0"+day : day+"";
        String hours = (hour+"").length() == 1 ? "0"+hour : hour+"";
        String minutes = (minute+"").length() == 1 ? "0"+minute : minute+"";

        return days +" D "+ hours +" H "+ minutes +" M";

    }

    public static String getBookedTimeFromSavedPreferences(Context ctx,String preferenceKeyName) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return sp.getString(preferenceKeyName, "0");
    }



    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

}
