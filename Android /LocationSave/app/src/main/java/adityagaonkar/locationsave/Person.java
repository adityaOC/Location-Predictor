package adityagaonkar.locationsave;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adityagaonkr on 17/02/17.
 */

public class Person implements Parcelable {

    String name;
    String address;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}

