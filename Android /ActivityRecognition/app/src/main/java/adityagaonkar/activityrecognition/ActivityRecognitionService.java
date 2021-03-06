package adityagaonkar.activityrecognition;

/**
 * Created by adityagaonkr on 15/02/17.
 */


import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class ActivityRecognitionService extends IntentService{

    private static final String TAG ="ActivityRecognition";

    public ActivityRecognitionService() {
        super("ActivityRecognitionService");
    }

    /**
     * Google Play Services calls this once it has analysed the sensor data
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
          //  if(Config.TESTER_VERSION){
                Log.d(TAG, "ActivityRecognitionResult: "+getFriendlyName(result.getMostProbableActivity().getType()));
                Log.d(TAG, result.toString());
           // }

// YOUR FUNKY CODE GOES HERE!
        }
    }

    /**
     * When supplied with the integer representation of the activity returns the activity as friendly string
     * @param type the DetectedActivity.getType()
     * @return a friendly string of the
     */
    private static String getFriendlyName(int detected_activity_type){
        switch (detected_activity_type ) {
            case DetectedActivity.IN_VEHICLE:
                return "in vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on bike";
            case DetectedActivity.ON_FOOT:
                return "on foot";
            case DetectedActivity.TILTING:
                return "tilting";
            case DetectedActivity.STILL:
                return "still";
            default:
                return "unknown";
        }
    }
}
