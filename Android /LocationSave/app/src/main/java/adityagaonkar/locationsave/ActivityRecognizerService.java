package adityagaonkar.locationsave;

/**
 * Created by adityagaonkr on 19/02/17.
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

import java.io.Serializable;
import java.util.List;

import static adityagaonkar.locationsave.GeoRecord.*;


public class ActivityRecognizerService extends IntentService {


    // public  String currentActivity;
    public ActivityRecognizerService() {

        super("ActivityRecognizedService");

    }

    public ActivityRecognizerService(String name) {
        super(name);
        // Toast.makeText(getApplicationContext(), "super ", Toast.LENGTH_LONG).show();
    }


    private void triggerCallback(String activityName, Intent intent) {
        /* ResultReceiver rec = intent.getParcelableExtra("receiverTag");
         Bundle b = new Bundle();
         b.putString("ServiceTag", "callback bundle");
         rec.send(0, b);*/
    }
//(String activityType,String time,String latitude,String longitude)
    private void triggerBroadcast(String activityType) {
        Intent bIntent = new Intent();
        bIntent.addCategory(Intent.CATEGORY_DEFAULT);
        bIntent.setAction("com.LocationPredictor");
        //  bIntent.putExtra("ActivityTyepe", activity.getType());
        bIntent.putExtra("ActivityTyepeString", activityType);

        GeoRecord r = new GeoRecord();
        r.setDateTimeString("Geo time");
        r.setLongitude("Geo long");
        UserActivity activity = new UserActivity("user actiivity name",1, UserActivity.ActivityCode.IN_VEHICLE);
        r.userActivities.add(activity);

        bIntent.putExtra("GeoRecord",r);

        sendBroadcast(bIntent);
        System.out.println("HIT OUTGOING switch");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities(), intent);

        }

        Log.e("ActivityRecogition", "onHandleIntent");
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities, Intent intent) {
       // String  strValue = (String)intent.getSerializableExtra("ActivityTyepeString");
        GeoRecord geoRecord = new GeoRecord();
        String  timeString = (String)intent.getSerializableExtra("timeString");
        String  latitudeString = (String)intent.getSerializableExtra("latitudeString");
        String  longitudeString = (String)intent.getSerializableExtra("longitudeString");

        geoRecord.setDateTimeString(timeString);
        geoRecord.setLatitude(latitudeString);
        geoRecord.setLongitude(longitudeString);

        for (DetectedActivity activity : probableActivities) {

            UserActivity userActivity;
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
                   // triggerCallback("In vehical", intent);

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


