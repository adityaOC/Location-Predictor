package adityagaonkar.locationsave;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;
    TextView locationTextView;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;


    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnShowLocation = (Button) findViewById(R.id.getLocationButton);


        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

              //  Toast.makeText(getApplicationContext(), "Button clicked ", Toast.LENGTH_LONG).show();
                // create class object
              gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                  //  int hours = new Time(System.currentTimeMillis()).getHours();
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude + "\nhours : " + currentDateTimeString, Toast.LENGTH_LONG).show();
                    //save in DB


                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());


                    Log.d("Insert: ", "Inserting ..");


                    db.addContact(new GeoRecord(String.valueOf(currentDateTimeString),String.valueOf(latitude),String.valueOf(longitude)));


                    // Reading all contacts
                 /*   Log.d("Reading: ", "Reading all contacts..");
                    List<GeoRecord> contacts = db.getAllContacts();

                    for (GeoRecord cn : contacts) {
                        String log = "Id: " + cn.getID()  + ",time  : " + cn.getDateTimeString()   + ",latitude : " + cn.getLatituded() + ",longitude : " + cn.getLongitude();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }*/

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

    public void openRecordsActivity(View view){

      //  Toast.makeText(getApplicationContext(), "openRecordsActivity clicked ", Toast.LENGTH_LONG).show();

        Intent newActivity = new Intent(getApplicationContext(),RecordListActivity.class);

        startActivity(newActivity);

    }



}

