package com.example.jcruz.lectura11.APIaguakan;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jcruz.lectura11.Config;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class setFOTObyRegistro extends AsyncTask<Object, Object, String> {


    private final String mdata;
    private final int midportal;
    private  String MSG ,moratkn;
    private Context mcontexto;


    public setFOTObyRegistro( String data,int portal, String oratkn, Context mcontexto) {

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
        String api=config.getAPIfoto();
        //data = "action=setodt&oratkn="+oratkn+"&auth="+"&data="+mdata+"&switch=''";
        data = "getimg="+mdata;
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
                connection.setRequestProperty("charset", "utf-8");

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(data.getBytes());
                out.flush();
                out.close();


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
            Log.d("Exito foto","exitooo");
            Log.d("Exitofoto",success);
            JsonParser parser = new JsonParser();
            JsonElement arrayElement = parser.parse(success);
            Log.d("odt arrayElement", success);
            int status = arrayElement.getAsJsonObject().get("estatus").getAsInt();
            String msg = arrayElement.getAsJsonObject().get("msg").getAsString();
            JsonElement odtarray,odtarray2,medidor,medidor2;
            if(status==1) {
                MSG = "Actualizacion de fotos correcta";
            }
            else{
                MSG = "Error al actualizar. "+msg;
            }
            int total;


            // Log.d("mail", mail);
            // JsonElement  cns = servicio.getAsJsonArray().get(0).getAsJsonObject().get("cns");
            //Log.d("cns", cns.toString());

            // finish();
        } else {
            //mPasswordView.setError(success);

            // mEmailView.setError("No se encontro usuario");
            // mPasswordView.setError(getString(R.string.error_incorrect_password));
            //mEmailView.requestFocus();
            //mPasswordView.requestFocus();
            MSG = "Error, servicio no disponible.  "+success;
        }
        Toast.makeText(mcontexto,MSG, Toast.LENGTH_LONG).show();


    }
    @Override
    protected void onCancelled() {
        //   Dataodt = null;
        //showProgress(false);
    }
}