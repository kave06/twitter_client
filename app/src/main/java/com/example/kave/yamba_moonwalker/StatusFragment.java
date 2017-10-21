package com.example.kave.yamba_moonwalker;

import android.support.design.widget.Snackbar;
import android.widget.ProgressBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
// import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private static final String TAG = "StatusActivity";
    private ProgressBar prg;
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;
    TextView textCount;
    String mensajeToast;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_status, container, false);

        prg = (ProgressBar)view.findViewById(R.id.progressBar_cyclic);
        prg.setVisibility(View.GONE);

        // Enlazar views
        editStatus = (EditText) view.findViewById(R.id.editStatus);
        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        textCount = (TextView) view.findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("zTBBdWM6NXRiIYAyuDbAUDKHF")
                .setOAuthConsumerSecret("ua40LRpcIWsAP9Iw1rN3ZXFxwYxPpkKROgpmojSm9unWN1CKhf")
                .setOAuthAccessToken("1172481186-kEx3lOCT5I1zT3R7iIBDigUC0VwKXc0i4bvZGpE")
                .setOAuthAccessTokenSecret("msc0imLZwmWq89toNoyouRmwn9CqZ3jWE5AXZUJrE3thw");

            TwitterFactory factory = new TwitterFactory(builder.build());
            Log.e(TAG, "Fallo en la conexión con la cuenta, probablemente por el token");

        twitter = factory.getInstance();

        return view;
    }

    // Función llamada al pulsar el botón
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        prg.setVisibility(View.VISIBLE);

        Log.d(TAG, "onClicked");
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
                mensajeToast="Tweet enviado correctamente";
                return "Tweet enviado correctamente";
            }catch (TwitterException e) {
                Log.e(TAG, "Fallo en el envío");
                e.printStackTrace();
                // mensajeToast = e.getErrorCode() + ": " + aEspanol(e.getErrorCode());
                mensajeToast = "ERROR " + e.getErrorCode() + ": " + aEspanol(e.getErrorCode());
                return "Fallo en el envío del tweet: " + e.getErrorMessage();
            }
        }
        // Llamada cuando la acitvidad en background ha terminado
        @Override
        protected void onPostExecute(String result) {
            // Acción al completar la actualización del estado
            super.onPostExecute(result);
            // Toast.makeText(StatusFragment.this.getActivity(), mensajeToast, Toast.LENGTH_LONG).show(); //Antiguo TOAST

            Snackbar.make(view, mensajeToast, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            prg.setVisibility(View.GONE);
        }

    }

    public String aEspanol (int errorID) {
        String errorString;
        switch (errorID) {
            case 32:  errorString = "No se le pudo autenticar";
                break;
            case 34:  errorString = "Lo sentimos, esa pagina no existe";
                break;
            case 36:  errorString = "No puede informarse por correo no deseado";
                break;
            case 44:  errorString = "El parámetro attachment_url no es válido";
                break;
            case 50:  errorString = "Usuario no encontrado";
                break;
            case 63:  errorString = "El usuario ha sido suspendido";
                break;
            case 64:  errorString = "Su cuenta está suspendida y no tiene permiso para acceder a esta función";
                break;
            case 68:  errorString = "La API REST de Twitter v1 ya no está activa. Migra a la API v1.1.";
                break;
            case 87:  errorString = "El cliente no tiene permitido realizar esta acción";
                break;
            case 88: errorString = "Excede el límite de velocidad. Se alcanzó el límite de solicitud para este recurso para la ventana de límite de velocidad actual";
                break;
            case 89: errorString = "Token inválido o vencido El token de acceso utilizado en la solicitud es incorrecto o ha expirado";
                break;
            case 92: errorString = "SSL es requerido. Solo se permiten conexiones SSL en la API. Actualice la solicitud a una conexión segura. Vea cómo conectarse usando TLS";
                break;
            case 93: errorString = "Esta aplicación no tiene permiso para acceder o eliminar tus mensajes directos";
                break;
            case 99: errorString = "Incapaz de verificar tus credenciales";
                break;
            case 120: errorString = "Falló la actualización de la cuenta: el valor es demasiado largo (el máximo es nn caracteres)";
                break;
            case 130: errorString = "Por encima de la capacidad";
                break;
            case 131: errorString = "Error interno. HTTP 500. Ocurrió un error interno desconocido";
                break;
            case 135: errorString = "No se pudo autenticar";
                break;
            case 144: errorString = "No se encontró estado con esa ID";
                break;
            case 150: errorString = "No puedes enviar mensajes a usuarios que no te están siguiendo";
                break;
            case 151: errorString = "Hubo un error al enviar su mensaje";
                break;
            case 160: errorString = "Ya has solicitado seguir al usuario";
                break;
            case 161: errorString = "No puedes seguir a más personas en este momento";
                break;
            case 170: errorString = "Falta el parámetro requerido: estado";
                break;
            case 179: errorString = "Lo sentimos, no estás autorizado para ver este estado";
                break;
            case 185: errorString = "El usuario está por encima del límite de actualización de estado diario";
                break;
            case 186: errorString = "El estado tiene más de 140 caracteres";
                break;
            case 187: errorString = "El estado es un duplicado. El texto de estado ya ha sido tuiteado por la cuenta autenticada";
                break;
            case 205: errorString = "Usted está por encima del límite para los informes de spam";
                break;
            case 215: errorString = "Datos de autenticación incorrectos";
                break;
            case 220: errorString = "Sus credenciales no permiten el acceso a este recurso";
                break;
            case 226: errorString = "Esta solicitud parece que podría ser automática. Para proteger a nuestros usuarios contra el spam y otras actividades maliciosas, no podemos completar esta acción en este momento. Supervisamos y ajustamos constantemente nuestros filtros para bloquear el correo no deseado y la actividad maliciosa en la plataforma de Twitter";
                break;
            case 231: errorString = "El usuario debe verificar el inicio de sesión";
                break;
            case 251: errorString = "Este endpoint ha sido retirado y no debe ser utilizado";
                break;
            case 261: errorString = "La aplicación no puede realizar acciones de escritura";
                break;
            case 271: errorString = "No puedes silenciarte a tí mismo";
                break;
            case 272: errorString = "No estás silenciando al usuario especificado";
                break;
            case 323: errorString = "Los GIF animados no están permitidos cuando se cargan varias imágenes";
                break;
            case 324: errorString = "La validación de los identificadores de medios falló";
                break;
            case 325: errorString = "No se encontró un identificador de medios";
                break;
            case 326: errorString = "Para proteger a nuestros usuarios contra el spam y otras actividades maliciosas, esta cuenta está bloqueada temporalmente";
                break;
            case 354: errorString = "El texto de su mensaje directo está por encima del límite máximo de caracteres";
                break;
            case 385: errorString = "Intentó responder a un tweet borrado o no visible";
                break;
            case 386: errorString = "El Tweet excede la cantidad de tipos de archivos adjuntos permitidos";
                break;
            default: errorString = "Algo falló, un error inesperado.";
                break;
        }
        return(errorString);
    }

}