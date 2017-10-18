package com.example.kave.yamba_moonwalker;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusActivity extends AppCompatActivity {


    private static final String TAG = "StatusActivity";
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;
    TextView textCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // Comprobar si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crear un fragment
            StatusFragment fragment = new StatusFragment();
            getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
        }

    }
}
