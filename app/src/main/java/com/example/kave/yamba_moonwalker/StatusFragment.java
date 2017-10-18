package com.example.kave.yamba_moonwalker;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {


    private static final String TAG = "StatusActivity";
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;
    TextView textCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //TODO QUITAR??
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        // Enlazar views
        editStatus = (EditText) view.findViewById(R.id.editStatus);
        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        textCount = (TextView) view.findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("Y5mK9TEpRBcNm9OxSWa3Axytr")
                .setOAuthConsumerSecret("SfUYSAX55wp8VVhuTrFtHqVKckld6at25ULlAEJkDIJZjDbnkg")
                .setOAuthAccessToken("151864405-IsHlUecXE9MNZZRxrnxcDhSdxlAVlMY8G9Fh4XiS")
                .setOAuthAccessTokenSecret("\tHj8FWziaVfARqt1Gm16BLcl3zV9k9kl7ILbe3h8uLybfF");
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();

        return view;
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

            Toast.makeText(StatusFragment.this.getActivity(), "Tweet enviado satisfactoriamente", Toast.LENGTH_LONG).show();
        }

    }
}
