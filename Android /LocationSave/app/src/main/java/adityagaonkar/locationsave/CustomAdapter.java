package adityagaonkar.locationsave;

/**
 * Created by adityagaonkr on 09/02/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends BaseAdapter {
    Context context;
   // String countryList[];
    int flags[];
    LayoutInflater inflter;
    List<GeoRecord> recordsList;

    public CustomAdapter(Context applicationContext, List<GeoRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return recordsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView country = (TextView) view.findViewById(R.id.textViewLatitude);
       // ImageView icon = (ImageView) view.findViewById(R.id.icon);
        GeoRecord r = recordsList.get(i);

        String display = r.getLatituded() + " , " + r.getLongitude() + " \n " + r.getDateTimeString()
                +"\n speed = " +r.getSpeed() + " km/hr" + "\n Activities : "+ r.get_objectInfoJSON_DBString() + "user : "+r.getUserName();
                ;
        country.setText(display);
       // icon.setImageResource(flags[i]);
        return view;
    }
}