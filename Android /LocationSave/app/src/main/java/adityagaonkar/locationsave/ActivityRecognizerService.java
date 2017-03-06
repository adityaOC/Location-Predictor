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
import static adityagaonkar.locationsave.UserActivity.InVehicleActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.OnBicycleActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.OnFootActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.RunningActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.StillActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.TiltingActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.UnknownActivityDisplayText;
import static adityagaonkar.locationsave.UserActivity.WalkingActivityDisplayText;


public class ActivityRecognizerService extends IntentService {


    // public  String currentActivity;
    public ActivityRecognizerService() {

        super("ActivityRecognizedService");

    }

    public ActivityRecognizerService(String name) {
        super(name);
        // Toast.makeText(getApplicationContext(), "super ", Toast.LENGTH_LONG).show();
    }



    private void triggerBroadcast(GeoRecord geoRecord) {
        Intent bIntent = new Intent();
        bIntent.addCategory(Intent.CATEGORY_DEFAULT);
        bIntent.setAction("com.LocationPredictor");
        
        bIntent.putExtra("GeoRecord",geoRecord);

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

                  //add activity to geo record
                    UserActivity userActivity = new UserActivity(InVehicleActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.IN_VEHICLE);
                    geoRecord.userActivities.add(userActivity);

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
                    UserActivity userActivity = new UserActivity(OnBicycleActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.ON_BICYCLE);
                    geoRecord.userActivities.add(userActivity);

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

                    UserActivity userActivity = new UserActivity(OnFootActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.ON_FOOT);
                    geoRecord.userActivities.add(userActivity);

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
                    UserActivity userActivity = new UserActivity(RunningActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.RUNNING);
                    geoRecord.userActivities.add(userActivity);


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
                    UserActivity userActivity = new UserActivity(StillActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.STILL);
                    geoRecord.userActivities.add(userActivity);


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
                    UserActivity userActivity = new UserActivity(TiltingActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.TILTING);
                    geoRecord.userActivities.add(userActivity);

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
                    UserActivity userActivity = new UserActivity(WalkingActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.WALKING);
                    geoRecord.userActivities.add(userActivity);

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


                    UserActivity userActivity = new UserActivity(UnknownActivityDisplayText,activity.getConfidence(), UserActivity.ActivityCode.UNKNOWN);
                    geoRecord.userActivities.add(userActivity);
                    break;
                }
            }


        }

        //TODO : trigger broadcast when all activities are added to Geo record

            triggerBroadcast(geoRecord);
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


