package adityagaonkar.activityrecognition;

/**
 * Created by adityagaonkr on 15/02/17.
 */
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ActivityRecognizedService extends IntentService {


    // public  String currentActivity;
    public ActivityRecognizedService() {

        super("ActivityRecognizedService");

    }

    public ActivityRecognizedService(String name) {
        super(name);
        // Toast.makeText(getApplicationContext(), "super ", Toast.LENGTH_LONG).show();
    }


    private void triggerCallback(String activityName, Intent intent) {
        /* ResultReceiver rec = intent.getParcelableExtra("receiverTag");
         Bundle b = new Bundle();
         b.putString("ServiceTag", "callback bundle");
         rec.send(0, b);*/
    }

    private void triggerBroadcast(String activityType) {
        Intent bIntent = new Intent();
        bIntent.addCategory(Intent.CATEGORY_DEFAULT);
        bIntent.setAction("com.LocationPredictor");
        //  bIntent.putExtra("ActivityTyepe", activity.getType());
        bIntent.putExtra("ActivityTyepeString", activityType);

        Person p = new Person();
        p.name = "name of person";
        p.address = "address of person";
        bIntent.putExtra("personObj",p);
        sendBroadcast(bIntent);
        System.out.println("HIT OUTGOING switch");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

       /*  Bundle getBundle = null;
        getBundle = intent.getExtras();
        String name = getBundle.getString("name");
        String id = getBundle.getString("iname");

        Person  person = (Person)getBundle.getParcelable("personObject2");
       // Person  person = (Person)intent.getExtras().getParcelable("personObject");
        if (person == null){
            Log.e("ActivityRecogition", "person value null");
        }else {
            Log.e("ActivityRecogition", "person name = "+ person.name);
        }*/



       if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities(), intent);

        }

        Log.e("ActivityRecogition", "onHandleIntent");
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities, Intent intent) {


        for (DetectedActivity activity : probableActivities) {
            Integer  intValue = (Integer)intent.getSerializableExtra("IDvalue");
            Double  lat = (Double)intent.getSerializableExtra("latitude");
            Double  lng = (Double)intent.getSerializableExtra("longitude");

            NotificationCompat.Builder builderC = new NotificationCompat.Builder(this);
            builderC.setContentText("Common");
            builderC.setSmallIcon(R.mipmap.ic_launcher);
            builderC.setContentTitle(getString(R.string.app_name));
            NotificationManagerCompat.from(this).notify(0, builderC.build());


            String strAct = "";
            switch (activity.getType()) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e("ActivityRecogition", "In Vehicle: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("In Vehicle");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Still";
                    }
                    triggerBroadcast("In vehical");
                    triggerCallback("In vehical", intent);

                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e("ActivityRecogition", "On Bicycle: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("On Bicycle");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Still";
                    }
                    triggerBroadcast("on bicycle");
                    triggerCallback("On Bicycle", intent);

                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e("ActivityRecogition", "On Foot: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("On Foot");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Still";
                    }

                    strAct = "On Foot";
                    triggerBroadcast("on foot");
                    triggerCallback(strAct, intent);
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e("ActivityRecogition", "Running: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Running");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Still";
                    }
                    triggerBroadcast("Running");
                    triggerCallback("", intent);
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e("ActivityRecogition", "Still: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("This is still?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Still";
                    }

                    strAct = "Still";
                    triggerBroadcast("Still");
                    triggerCallback(strAct, intent);
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e("ActivityRecogition", "Tilting: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText(" Tilting");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Walking";
                    }
                    triggerBroadcast("Tilting");
                    triggerCallback(strAct, intent);
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e("ActivityRecogition", "Walking: " + activity.getConfidence());
                    if (activity.getConfidence() >= 75) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText(" you  are walking?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                        // currentActivity = "Walking";
                    }
                    strAct = "Walking";
                    triggerBroadcast("Walking");
                    triggerCallback(strAct, intent);
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e("ActivityRecogition", "Unknown: " + activity.getConfidence());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    builder.setContentText("Unknown");
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle(getString(R.string.app_name));
                    NotificationManagerCompat.from(this).notify(0, builder.build());
                    // currentActivity = "Still";
                    triggerBroadcast("Unknown");
                    triggerCallback(strAct, intent);
                    break;
                }
            }


        }

    }

}

/*
public String getCurrentActivity(){

    return currentActivity;
}
*/

    //  Toast.makeText(getApplicationContext(), "onHandleIntent ", Toast.LENGTH_LONG).show();




          /*  Handler handler = new Handler() {
                @Override
                public void publish(LogRecord logRecord) {

                }

                @Override
                public void flush() {

                }

                @Override
                public void close() throws SecurityException {

                }
            };
            Runnable runnable = new Runnable() {
                public void run() {
                    Intent bIntent = new Intent();
                    bIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    bIntent.setAction("com.LocationPredictor");
                    sendBroadcast(bIntent);
                    System.out.println("HIT OUTGOING");
                }
            };

          //  runnable.run();*/


