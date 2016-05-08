package com.study.joomagg.dbconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "how nice", Toast.LENGTH_SHORT).show();
        String url = getResources().getString(R.string.URL);

        DBConnection con = new DBConnection(url);
        con.getFriendship();
    }
}
