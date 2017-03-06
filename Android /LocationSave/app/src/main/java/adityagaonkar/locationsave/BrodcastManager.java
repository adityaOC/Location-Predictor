package adityagaonkar.locationsave;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by adityagaonkr on 16/02/17.
 */

public class BrodcastManager extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("GOT THE INTENT");

        if (intent.getAction().equals("com.LocationPredictor")) {

          //  Integer  intValue = (Integer)intent.getSerializableExtra("ActivityTyepe");
            String  strValue = (String)intent.getSerializableExtra("ActivityTyepeString");
            Person  p = (Person)intent.getExtras().getParcelable("personObj");
            GeoRecord  geoRecord = (GeoRecord)intent.getExtras().getParcelable("GeoRecord");
            UserActivity userActivity = geoRecord.userActivities.get(0);
            Toast.makeText(context,strValue +"Geo time = "+  userActivity.activityName , Toast.LENGTH_SHORT).show();


            if (geoRecord !=null){

            //save data in DB
                try {
                    saveRecordInDB(geoRecord);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void saveRecordInDB(GeoRecord record) throws JSONException {
        if (record !=null){

            //save data in DB
            System.out.println("LocERR :BrodcastManager: "+ record.get_objectInfoJSONString());
            DatabaseHandler db = new DatabaseHandler(MyApplication.getContext());
            db.addNewGeoRecord(record);
           // db.addContact(record);

        }else {
            System.out.println("LocERR :BrodcastManager: GeoRecord object null while saving in DB");
        }

    }

}
