package adityagaonkar.locationsave;

/**
 * Created by adityagaonkr on 08/02/17.
 */

public class GeoRecord {


    int _id;
   /* String _name;
    String _phone_number;*/
    String _latitude;
    String _longitude;
    String _dateTimeString;


    // Empty constructor
    public GeoRecord(){

    }
    // constructor
    public GeoRecord(int id,String _latitude,String _longitude){
        this._id = id;
        //this._name = name;
        //this._phone_number = _phone_number;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    // constructor // in use
    public GeoRecord(String _dateTimeString,String _latitude,String _longitude){
       // this._name = name;
       // this._phone_number = _phone_number;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._dateTimeString = _dateTimeString;
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
}