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

public class StatusActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {


    private static final String TAG = "StatusActivity";
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;
    TextView textCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // Enlazar views
        editStatus = (EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("zTBBdWM6NXRiIYAyuDbAUDKHF")
                .setOAuthConsumerSecret("ua40LRpcIWsAP9Iw1rN3ZXFxwYxPpkKROgpmojSm9unWN1CKhf")
                .setOAuthAccessToken("1172481186-kEx3lOCT5I1zT3R7iIBDigUC0VwKXc0i4bvZGpE")
                .setOAuthAccessTokenSecret("msc0imLZwmWq89toNoyouRmwn9CqZ3jWE5AXZUJrE3thw");
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();
    }

    // Función llamada al pulsar el botón
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked");

        /*
        try {
            twitter.updateStatus(status);
            Log.d(TAG, "Enviado correctamente: ");
        }catch (Exception e) {
            Log.e(TAG, "Fallo en el envío");
            e.printStackTrace();
        }
        */
        new PostTask().execute(status);
    }

    @Override
    public void afterTextChanged(Editable statusText) {
        int count = 140 - statusText.length();
        textCount.setText(Integer.toString(count));
        textCount.setTextColor(Color.GREEN);
        if (count < 10)
            textCount.setTextColor(Color.YELLOW);
        if (count < 0)
            textCount.setTextColor(Color.RED);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    private final class PostTask extends AsyncTask<String, Void, String> {
        // Llamada al empezar
        @Override
        protected String doInBackground(String... params) {
            try{
                twitter.updateStatus(params[0]);
                return "Tweet enviado correctamente";
            }catch (TwitterException e) {
                Log.e(TAG, "Fallo en el envío");
                e.printStackTrace();
                return "Fallo en el envío del tweet";
            }
        }
        // Llamada cuando la acitvidad en background ha terminado
        @Override
        protected void onPostExecute(String result) {
            // Acción al completar la actualización del estado
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this, "Tweet enviado satisfactoriamente",
                    Toast.LENGTH_LONG).show();
        }

    }
}
