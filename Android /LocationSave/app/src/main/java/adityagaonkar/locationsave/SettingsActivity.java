package adityagaonkar.locationsave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    EditText editTextUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView usernameTextView = (TextView)findViewById(R.id.currentUsernameDisplay);
        usernameTextView.setText(MyApplication.getUsername());
        editTextUsername = (EditText)findViewById(R.id.editText_Username);

    }



    public void onClickSet(View view){


        String username = editTextUsername.getText().toString();
        if(username.isEmpty()){

            Toast.makeText(this, "Settings: Username empty ", Toast.LENGTH_SHORT).show();

        }else {

            MyApplication.setUsername(username);
            Toast.makeText(this, "Settings: New Username set", Toast.LENGTH_SHORT).show();
        }

    }
}
