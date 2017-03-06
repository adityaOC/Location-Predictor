package adityagaonkar.locationsave;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adityagaonkr on 20/02/17.
 */


public class UserActivity implements Parcelable {

    public static final String InVehicleActivityDisplayText = "In Vehicle";
    public static final String OnBicycleActivityDisplayText = "On Bicycle";
    public static final String OnFootActivityDisplayText = "On Foot";
    public static final String StillActivityDisplayText = "Still";
    public static final String UnknownActivityDisplayText = "Unknown";
    public static final String TiltingActivityDisplayText = "Tilting";
    public static final String WalkingActivityDisplayText = "Walking";
    public static final String RunningActivityDisplayText = "Running";


    public enum ActivityCode {

        IN_VEHICLE,
        ON_BICYCLE,
        ON_FOOT,
        STILL,
        UNKNOWN,
        TILTING,
        WALKING,
        RUNNING,

    }
    String activityName;
    Integer activityConfidence;
    ActivityCode activityCode;

    public UserActivity(String activityName, Integer activityConfidence,ActivityCode activityCode){

        this.activityName = activityName;
        this.activityConfidence = activityConfidence;
        this.activityCode = activityCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityName);
        dest.writeValue(this.activityConfidence);
        dest.writeInt(this.activityCode == null ? -1 : this.activityCode.ordinal());
    }

    protected UserActivity(Parcel in) {
        this.activityName = in.readString();
        this.activityConfidence = (Integer) in.readValue(Integer.class.getClassLoader());
        int tmpActivityCode = in.readInt();
        this.activityCode = tmpActivityCode == -1 ? null : ActivityCode.values()[tmpActivityCode];
    }

    public static final Parcelable.Creator<UserActivity> CREATOR = new Parcelable.Creator<UserActivity>() {
        @Override
        public UserActivity createFromParcel(Parcel source) {
            return new UserActivity(source);
        }

        @Override
        public UserActivity[] newArray(int size) {
            return new UserActivity[size];
        }
    };
}
