package adityagaonkar.locationsave;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by adityagaonkr on 02/03/17.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static String username;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SharedPreferences preferences = mContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String str = preferences.getString("com.LocationSave.Username",null);
        if (str == null){
            username = "unknown";

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("com.LocationSave.Username", username);
            editor.commit();
        }



    }

    public  static void setUsername(String _username){




//--SAVE Data
        SharedPreferences preferences = mContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("com.LocationSave.Username", _username);
        editor.commit();


//--READ data



        username  = _username;
    }

    public static String getUsername(){

        String username = "";
        SharedPreferences preferences = mContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        username = preferences.getString("com.LocationSave.Username",null);
        return username;
    }
    public static Context getContext() {

        return mContext;
    }


}
