package adityagaonkar.activityrecognition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by adityagaonkr on 16/02/17.
 */

public class BrodcastManager  extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("GOT THE INTENT");

        if (intent.getAction().equals("com.LocationPredictor")) {

            Integer  intValue = (Integer)intent.getSerializableExtra("ActivityTyepe");
            Toast.makeText(context, "Intent recieved in manin activity: "+intValue.toString() , Toast.LENGTH_LONG).show();
        }
    }
}
