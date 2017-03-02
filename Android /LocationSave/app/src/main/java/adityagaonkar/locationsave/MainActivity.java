package adityagaonkar.locationsave;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.app.Activity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Address;
import java.util.List;
import android.util.Log;
import java.sql.Time;
import java.util.Date;
import java.text.DateFormat;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button btnShowLocation;
    public GoogleApiClient mApiClient;
    public PendingIntent pendingIntent;
    public BroadcastReceiver bRec;
    public MyResultReceiver mReceiver;
    Button btnGetActivity;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;


    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation = (Button) findViewById(R.id.getLocationButton);

        mApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();


        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

              gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    double speed = gps.getSpeed();
                    double speed2 = gps.getSpeed2();
                  //  int hours = new Time(System.currentTimeMillis()).getHours();
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude + "\n speed = "+ speed +"\n speed2 = " + speed2 + "\nhours : " + currentDateTimeString, Toast.LENGTH_LONG).show();
                    //save in DB

                    //call Activity detector, get activity, send geo record with GPS info
                    getActivity(currentDateTimeString,String.valueOf(latitude),String.valueOf(longitude));
                    //call brodcast from activity detector service
                    //get Geo record from brodcast receiver
                    //save it in db
                    //display it

                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());


                    Log.d("Insert: ", "Inserting ..");


                   // db.addContact(new GeoRecord(String.valueOf(currentDateTimeString),String.valueOf(latitude),String.valueOf(longitude),String.valueOf(speed)));



                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    Toast.makeText(getApplicationContext(), "Error in getting location", Toast.LENGTH_LONG).show();
                    gps.showSettingsAlert();
                }

            }
        });
    }

    public void getActivity(String time,String latitude,String longitude){


        Toast.makeText(this, "getActivity ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent( getApplicationContext(), ActivityRecognizerService.class );

        intent.putExtra("timeString",time);
        intent.putExtra("latitudeString",latitude);
        intent.putExtra("longitudeString",longitude);

        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_ONE_SHOT );
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 0, pendingIntent );

    }
    public void openRecordsActivity(View view){

        Intent newActivity = new Intent(getApplicationContext(),RecordListActivity.class);
        startActivity(newActivity);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("LocationSave", "onConnected: " );
        Toast.makeText(this, "onConnected  ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
/*


    // Reading all contacts
                 /*   Log.d("Reading: ", "Reading all contacts..");
                    List<GeoRecord> contacts = db.getAllContacts();

                    for (GeoRecord cn : contacts) {
                        String log = "Id: " + cn.getID()  + ",time  : " + cn.getDateTimeString()   + ",latitude : " + cn.getLatituded() + ",longitude : " + cn.getLongitude();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }*/


