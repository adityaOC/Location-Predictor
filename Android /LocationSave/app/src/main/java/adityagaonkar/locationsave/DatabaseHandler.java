package adityagaonkar.locationsave;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by adityagaonkr on 08/02/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GeoLocationsDB";

    // Contacts table name
    private static final String TABLE_LOCATIONS = "GeoLocations";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
  //  private static final String KEY_NAME = "name";
   // private static final String KEY_PH_NO = "phone_number";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String DATETIME = "dateTime";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("

                + KEY_ID + " INTEGER PRIMARY KEY," +
              //  KEY_NAME + " TEXT,"
               // + KEY_PH_NO + " TEXT," +
                DATETIME + " TEXT," +
                LATITUDE + " TEXT," +
                LONGITUDE + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(GeoRecord geoRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       // values.put(KEY_NAME, geoRecord.getName()); // Contact Name
       // values.put(KEY_PH_NO, geoRecord.getPhoneNumber()); // Contact Phone
        values.put(DATETIME,geoRecord.getDateTimeString());
        values.put(LATITUDE,geoRecord.getLatituded());
        values.put(LONGITUDE,geoRecord.getLongitude());
        // Inserting Row
        db.insert(TABLE_LOCATIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    GeoRecord getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] {
                KEY_ID,
                DATETIME,
                LATITUDE,
                LONGITUDE
        }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        GeoRecord geoRecord = new GeoRecord(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));


        // return contact
        return geoRecord;
    }

    // Getting All Contacts
    public List<GeoRecord> getAllContacts() {
        List<GeoRecord> contactList = new ArrayList<GeoRecord>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GeoRecord geoRecord = new GeoRecord();
                geoRecord.setID(Integer.parseInt(cursor.getString(0)));
                geoRecord.setDateTimeString(cursor.getString(1));
                geoRecord.setLatitude(cursor.getString(2));
                geoRecord.setLongitude(cursor.getString(3));
                // Adding contact to list
                contactList.add(geoRecord);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(GeoRecord geoRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       // values.put(KEY_NAME, geoRecord.getName());
       // values.put(KEY_PH_NO, geoRecord.getPhoneNumber());

        // updating row
        return db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(geoRecord.getID()) });
    }

    // Deleting single contact
    public void deleteContact(GeoRecord geoRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(geoRecord.getID()) });

    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
