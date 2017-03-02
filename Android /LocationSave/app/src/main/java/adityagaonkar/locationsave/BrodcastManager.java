package adityagaonkar.locationsave;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by adityagaonkr on 16/02/17.
 */

public class BrodcastManager extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("GOT THE INTENT");

        if (intent.getAction().equals("com.LocationPredictor")) {

          //  Integer  intValue = (Integer)intent.getSerializableExtra("ActivityTyepe");
            String  strValue = (String)intent.getSerializableExtra("ActivityTyepeString");
            Person  p = (Person)intent.getExtras().getParcelable("personObj");
            GeoRecord  gr = (GeoRecord)intent.getExtras().getParcelable("GeoRecord");
            UserActivity userActivity = gr.userActivities.get(0);
            Toast.makeText(context,strValue +"Geo time = "+  userActivity.activityName , Toast.LENGTH_SHORT).show();

        }
    }

}
