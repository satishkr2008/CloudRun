package com.satish.cloudrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Create Activity button */
    public void startCreateTrackActivity(View view) {
        Intent intent = new Intent(this, CreateTrackActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Start Mapping button */
    public void startMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
