package adityagaonkar.activityrecognition;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by adityagaonkr on 15/02/17.
 */

public class MyService extends IntentService {
    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e( "ActivityRecogition", "onHandleIntent" );
    }
}
