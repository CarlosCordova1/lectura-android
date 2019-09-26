package com.example.jcruz.lectura11.APIaguakan;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.jcruz.lectura11.Config;
import com.example.jcruz.lectura11.FragmentTox7;
import com.example.jcruz.lectura11.MainActivity;
import com.example.jcruz.lectura11.Model.DBfotoOperation;
import com.example.jcruz.lectura11.Model.MlectOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.security.AccessController.getContext;

public class ActualizaPorRegistro extends AsyncTask<Object, Object, String> {


    private final String mdata;
    private final int midportal;
    private String MSGODT, moratkn;
    private Context mcontexto;

     public ActualizaPorRegistro(String data, int portal, String oratkn, Context mcontexto) {
         this.mcontexto=mcontexto;
         this.moratkn=oratkn;
        mdata = data;
        midportal = portal;

    }

    @Override
    protected String doInBackground(Object... params) {
        // TODO: attempt authentication against a network service.
        String Cadena ="";
        String data = null;
        Config config = new Config ();
        String api=config.getAPI();
        //data = "action=setodt&oratkn="+oratkn+"&auth="+"&data="+mdata+"&switch=''";
        data = "action=setodt&oratkn="+moratkn+"&auth="+"&data="+mdata;
        try {

            // Simulate network access.
            Thread.sleep(1);
            /**/
            HttpURLConnection connection=null;
            BufferedReader reader=null;

            try {
                // String passCifrado=md5(mPassword);

                URL url= new URL(api);
                connection =(HttpURLConnection)url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setFixedLengthStreamingMode(data.getBytes().length);
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(data.getBytes());
                out.flush();
                out.close();

                    /*
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("firstParam", 1));
                    params.add(new BasicNameValuePair("secondParam", 2));
                    params.add(new BasicNameValuePair("thirdParam", 2));
                    */

                connection.connect();



                InputStream stream=connection.getInputStream();

                StringBuffer buffer=new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(stream));
                String line="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                // Log.d("respuesta", buffer.toString());
                Cadena=buffer.toString();
                //tvData.setText(buffer.toString());

            }  catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }

                try {
                    if(reader!=null){
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /**/
        } catch (InterruptedException e) {
            return Cadena;
        }
/*
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return Cadena;// pieces[1].equals(mPassword);
                }
            }
            */

        // TODO: register the new account here.

        return Cadena;
    }

    @Override
    protected void onPostExecute(final String success) {
       // Dataodt = null;
        //   showProgress(false);
        Log.d("cadena",success);
        int v =success.length();
        if (v>4 ) {
            //  mPasswordView.setError(success);
            //mPasswordView.requestFocus();
            Log.d("Exito","exitooo");
            Log.d("Exito",success);

            // Intent intent = new Intent(getApplicationContext(), VerJon.class);
            // EditText editText = (EditText)findViewById(R.id.Addjson);
            // String mensaje = editText.getText().toString();

            //intent.putExtra("json", success);
            //startActivity(intent);
            //Log.d("jsonrespuesta", success);
            JsonParser parser = new JsonParser();
            JsonElement arrayElement = parser.parse(success);
            Log.d("odt arrayElement", success);
            int status = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("status").getAsInt();
            String msg = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
            JsonElement odtarray,odtarray2,medidor,medidor2;
            if(status==1) {
                MSGODT = "Actualizacion ODT correcta";
            }
            else{
                MSGODT = "Error al actualizar. "+msg;
            }
        } else {

            MSGODT = "Error en el servicio. "+success;
        }
        Toast.makeText(mcontexto,MSGODT, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCancelled() {
       // Dataodt = null;
        //showProgress(false);
    }
}
