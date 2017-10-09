package com.example.kave.yamba_moonwalker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "StatusActivity";
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // Enlazar views
        editStatus = (EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("zTBBdWM6NXRiIYAyuDbAUDKHF")
                .setOAuthConsumerSecret("ua40LRpcIWsAP9Iw1rN3ZXFxwYxPpkKROgpmojSm9unWN1CKhf")
                .setOAuthAccessTokenSecret("ERITq9d37QRMKRMo0teblMhoRnRKpxhkFLgXHhwv4G9kU");
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();
    }

    // Función llamada al pulsar el botón
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked");

        try {
            twitter.updateStatus(status);
            Log.d(TAG, "Enviado correctamente: ");
        }catch (Exception e) {
            Log.e(TAG, "Fallo en el envío");
            e.printStackTrace();
        }
    }
}
