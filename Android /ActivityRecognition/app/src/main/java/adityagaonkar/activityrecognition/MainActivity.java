package adityagaonkar.activityrecognition;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import android.content.BroadcastReceiver;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;
    public PendingIntent pendingIntent;
    public BroadcastReceiver bRec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bRec = new BrodcastManager();


       // Toast.makeText(getApplicationContext(), "onCreate ", Toast.LENGTH_LONG).show();
        mApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();


       // Intent intent = new Intent( getApplicationContext(), MyService.class );
       // startService(intent);



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {



        Toast.makeText(this, "onConnected ", Toast.LENGTH_LONG).show();
        Intent intent = new Intent( getApplicationContext(), ActivityRecognizedService.class );
        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_ONE_SHOT );
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 3000, pendingIntent );



      //  Toast.makeText(this, "onConnected ", Toast.LENGTH_LONG).show();




    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "onConnectionSuspended ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed ", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.LocationPredictor");
        Toast.makeText(this, "onStart ", Toast.LENGTH_LONG).show();
        registerReceiver(bRec,intentFilter);



    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop ", Toast.LENGTH_LONG).show();
        unregisterReceiver(bRec);
    }
}
