package adityagaonkar.activityrecognition;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import android.content.BroadcastReceiver;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,MyResultReceiver.Receiver,ActivityCallback {

    public GoogleApiClient mApiClient;
    public PendingIntent pendingIntent;
    public BroadcastReceiver bRec;
    public MyResultReceiver mReceiver;
    public Person p;
    Button btnGetActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetActivity = (Button) findViewById(R.id.getActivity);
        p =new Person();
        bRec = new BrodcastManager();

        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);


       // Toast.makeText(getApplicationContext(), "onCreate ", Toast.LENGTH_LONG).show();
        mApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();


        btnGetActivity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(), "On click  ", Toast.LENGTH_SHORT).show();
                getActivity();
            }


        });


       // Intent intent = new Intent( getApplicationContext(), MyService.class );
       // startService(intent);



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {



            //getActivity();

      //  Toast.makeText(this, "onConnected ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "onConnectionSuspended ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.LocationPredictor");
        Toast.makeText(this, "onStart ", Toast.LENGTH_SHORT).show();
        registerReceiver(bRec,intentFilter);



    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop ", Toast.LENGTH_SHORT).show();
        unregisterReceiver(bRec);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Toast.makeText(this, "Callback "+ resultData.getString("ServiceTag"), Toast.LENGTH_LONG).show();
    }

    @Override
    public Void interestingEvent(String s) {


        return null;
    }

    public void getActivity(){
        Person p = new Person();
        p.address = "this is adress";
        p.name = "this is name";

        Toast.makeText(this, "onConnected ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent( getApplicationContext(), ActivityRecognizedService.class );

        intent.putExtra("IDvalue",100);
        intent.putExtra("latitude",13.948953948);
        intent.putExtra("longitude",-85.948953948);

        //intent.putExtra("personObject",p);


       /* Bundle myBundle = new Bundle();

        bundle = new Bundle();

        bundle.putParcelable("personObject2",p);
        intent.putExtras(bundle);*/

        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_ONE_SHOT );
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 0, pendingIntent );


    }
}
