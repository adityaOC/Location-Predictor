package adityagaonkar.locationsave;

import android.os.Parcel;
import android.os.Parcelable;

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

    public void setSpeed(String speed){
        this._speed = speed;
    }
    public String getSpeed(){
        return this._speed;
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
        dest.writeList(this.userActivities);
    }

    protected GeoRecord(Parcel in) {
        this._id = in.readInt();
        this._latitude = in.readString();
        this._longitude = in.readString();
        this._dateTimeString = in.readString();
        this._speed = in.readString();
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
