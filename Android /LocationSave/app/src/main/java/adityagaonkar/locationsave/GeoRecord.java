package adityagaonkar.locationsave;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adityagaonkr on 08/02/17.
 */

public class GeoRecord implements Parcelable {


    int _id;
    String _latitude;
    String _longitude;
    String _dateTimeString;
    String _speed;
    String _objectInfoJSONString;
    String _objectInfoJSON_DBString;
    String _userName;
    ArrayList<UserActivity> userActivities = new ArrayList<UserActivity>();


    // Empty constructor
    public GeoRecord(){

    }
    // constructor
    public GeoRecord(int id,String _latitude,String _longitude,String _speed){
        this._id = id;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._speed = _speed;
    }

    // constructor // in use
    public GeoRecord(String _dateTimeString,String _latitude,String _longitude,String _speed){
       // this._name = name;
       // this._phone_number = _phone_number;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._dateTimeString = _dateTimeString;
        this._speed = _speed;
    }
    // constructor // in use //DB
    public GeoRecord(String _dateTimeString,String _latitude,String _longitude,String _speed,String _objectInfoJSON_DBString,String _userName){
        // this._name = name;
        // this._phone_number = _phone_number;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._dateTimeString = _dateTimeString;
        this._speed = _speed;
        this._objectInfoJSON_DBString = _objectInfoJSON_DBString;
        this._userName = _userName;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }


    public String getLatituded(){
        return this._latitude;
    }


    public void setLatitude(String latitude){
        this._latitude = latitude;
    }

    public String getLongitude(){
        return this._longitude;
    }


    public void setLongitude(String longitude){
        this._longitude = longitude;
    }

    public String getDateTimeString(){
        return this._dateTimeString;
    }

    public void setDateTimeString(String dateTimeString){
        this._dateTimeString = dateTimeString;
    }

    public void setUserName(String userName){this._userName = userName;}
    public String getUserName(){return this._userName;}

    public void setSpeed(String speed){
        this._speed = speed;
    }
    public String getSpeed(){
        return this._speed;
    }

    public void set_objectInfoJSON_DBString(String str){this._objectInfoJSON_DBString = str;}
    public  String get_objectInfoJSON_DBString(){return  this._objectInfoJSON_DBString;}


    public String get_objectInfoJSONString() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (UserActivity uActivity  : userActivities) {
            JSONObject uAct = new JSONObject();
            try {
               // uAct.put("key", "value");
                uAct.put("activityName",uActivity.activityName);
                uAct.put("activityConfidence",uActivity.activityConfidence.toString());
                uAct.put("activityCode",uActivity.activityCode.toString());

                jsonArray.put(uAct);



               /* object.put("name", "Jack Hack");
                object.put("score", new Integer(200));
                object.put("current", new Double(152.32));
                object.put("nickname", "Hacker");*/
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        JSONObject activitiesJson = new JSONObject();
        activitiesJson.put("activitiesJsonArray", jsonArray);


        return activitiesJson.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this._latitude);
        dest.writeString(this._longitude);
        dest.writeString(this._dateTimeString);
        dest.writeString(this._speed);
        dest.writeString(this._objectInfoJSONString);
        dest.writeString(this._objectInfoJSON_DBString);
        dest.writeString(this._userName);
        dest.writeList(this.userActivities);

    }

    protected GeoRecord(Parcel in) {
        this._id = in.readInt();
        this._latitude = in.readString();
        this._longitude = in.readString();
        this._dateTimeString = in.readString();
        this._speed = in.readString();
        this._objectInfoJSONString = in.readString();
        this._objectInfoJSON_DBString = in.readString();
        this._userName = in.readString();
        this.userActivities = new ArrayList<UserActivity>();
        in.readList(this.userActivities, UserActivity.class.getClassLoader());
    }

    public static final Creator<GeoRecord> CREATOR = new Creator<GeoRecord>() {
        @Override
        public GeoRecord createFromParcel(Parcel source) {
            return new GeoRecord(source);
        }

        @Override
        public GeoRecord[] newArray(int size) {
            return new GeoRecord[size];
        }
    };
}