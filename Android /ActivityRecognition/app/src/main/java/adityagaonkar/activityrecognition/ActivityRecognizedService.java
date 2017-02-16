package adityagaonkar.activityrecognition;

/**
 * Created by adityagaonkr on 15/02/17.
 */
import android.app.IntentService;
import android.content.Intent;
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

    @Override
    protected void onHandleIntent(Intent intent) {

        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
            Toast.makeText(getApplicationContext(), "onHandleIntent ", Toast.LENGTH_LONG).show();




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
        }

        Log.e( "ActivityRecogition", "onHandleIntent" );
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {



        for( DetectedActivity activity : probableActivities ) {

            Intent bIntent = new Intent();
            bIntent.addCategory(Intent.CATEGORY_DEFAULT);
            bIntent.setAction("com.LocationPredictor");
            bIntent.putExtra("ActivityTyepe",activity.getType());
            sendBroadcast(bIntent);
            System.out.println("HIT OUTGOING switch");
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e( "ActivityRecogition", "In Vehicle: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e( "ActivityRecogition", "On Bicycle: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e( "ActivityRecogition", "On Foot: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e( "ActivityRecogition", "Running: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e( "ActivityRecogition", "Still: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText( "Are you still?" );
                        builder.setSmallIcon( R.mipmap.ic_launcher );
                        builder.setContentTitle( getString( R.string.app_name ) );
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                       // currentActivity = "Still";
                    }


                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e( "ActivityRecogition", "Tilting: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e( "ActivityRecogition", "Walking: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText( "Are you walking?" );
                        builder.setSmallIcon( R.mipmap.ic_launcher );
                        builder.setContentTitle( getString( R.string.app_name ) );
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                       // currentActivity = "Walking";
                    }
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e( "ActivityRecogition", "Unknown: " + activity.getConfidence() );
                    break;
                }
            }
        }

    }
/*
public String getCurrentActivity(){

    return currentActivity;
}
*/
}

