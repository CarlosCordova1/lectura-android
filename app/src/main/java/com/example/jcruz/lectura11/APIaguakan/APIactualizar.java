package com.example.jcruz.lectura11.APIaguakan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.jcruz.lectura11.Config;
import com.example.jcruz.lectura11.Model.DBanomalia;
import com.example.jcruz.lectura11.Model.DBanomaliaoperation;
import com.example.jcruz.lectura11.Model.DBusuario;
import com.example.jcruz.lectura11.Model.DBusuarioOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/*
 public interface AsyncResponse {
    void processFinish(String output);
}
*/

public class APIactualizar extends AsyncTask<Object, Object, String> {


    public interface AsyncResponse {
        void processFinish(String output);
    }


    private final String action;
    private final String vdata;
    private final String mcode;
    private Context mContext, contextmsg;

    public AsyncResponse  delegate = null;


    public void MyCustomTask (Context context){
        mContext = context;
    }


    public APIactualizar(String action, String data2, String code, Context contextmsg,AsyncResponse delegate) {
        this.action = action;
        this.vdata = data2;
        this.mcode = code;
        this.contextmsg = contextmsg;
        this.delegate = delegate;
        //this.api = api;
    }
     @Override
    protected String doInBackground(Object... params) {
        // TODO: attempt authentication against a network service.
        String Cadena ="";
        String data = null;
        Config config = new Config ();
        String api=config.getAPI();

        data = "action="+action+"&data="+vdata+"&imei="+mcode+"&switch=''&auth="+" ";
         Log.d("data",vdata);
        try {

            // Simulate network access.
            Thread.sleep(1);
            /**/
            HttpURLConnection connection=null;
            BufferedReader reader=null;

            try {
                //demo="22f0258b6685684c113bad94d91b8fa02a"
                // String passCifrado=md5(mPassword);
                //String passCifrado=mPassword;
                // Log.d("password",mPassword);
                // String passCifrado="22f0258b6685684c113bad94d91b8fa02a";
                //
                //Cadena =mPassword;
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
         int v =success.length();
        //Toast.makeText(mContext,success,Toast.LENGTH_LONG).show();
        String mgssalida="";
         //Log.d("cadena", "  " + success);
            if (v>4 ) {
            JsonParser parser = new JsonParser();
            JsonElement arrayElement = parser.parse(success);
                JsonElement anomaliaArray, codeAnomalia, usuarioArray,codeUsuario;
            int status = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("status").getAsInt();
                String MSG = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
            if(status==1){
                DBanomalia newregistro = new DBanomalia();
                DBanomaliaoperation AnomaliaData = new DBanomaliaoperation(mContext);
                AnomaliaData.open();
                AnomaliaData.TRUNCATE();


                //Toast.makeText(mContext,"Acceso correcto al API ",Toast.LENGTH_LONG).show();
                anomaliaArray  = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get ("anomalia").getAsJsonObject();
                JsonObject obj = anomaliaArray.getAsJsonObject(); //since you know it's a JsonObject
                Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
                for (Map.Entry<String, JsonElement> entry: entries) {
                   // Log.d("anomaliaArray->Value-> ", "  " + entry.getKey());
                    codeAnomalia  =obj.getAsJsonObject().get(entry.getKey());
                    String Codigo = codeAnomalia.getAsJsonObject().get("codigo").getAsString();
                    String descr = codeAnomalia.getAsJsonObject().get("descripcion").getAsString();
                     //Log.d("Codigo - descr ",Codigo+" - "+descr);
                    newregistro.Setdataanomalia(Codigo ,descr);


                    AnomaliaData.addAnomalia(newregistro);
                }
                AnomaliaData.close();


                 DBusuario newusuario = new DBusuario();
                DBusuarioOperation usuarioData = new DBusuarioOperation(mContext);
                 usuarioData.open();
                 usuarioData.TRUNCATE();

                usuarioArray  = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get ("usuario").getAsJsonObject();
                JsonObject obj2 = usuarioArray.getAsJsonObject(); //since you know it's a JsonObject
                Set<Map.Entry<String, JsonElement>> entries2 = obj2.entrySet();//will return members of your object
                for (Map.Entry<String, JsonElement> entry: entries2) {
                    // Log.d("anomaliaArray->Value-> ", "  " + entry.getKey());
                    codeUsuario  =obj2.getAsJsonObject().get(entry.getKey());
                    int idportal = codeUsuario.getAsJsonObject().get("idportal").getAsInt();
                    String nombre = codeUsuario.getAsJsonObject().get("nombre").getAsString();
                    String token = codeUsuario.getAsJsonObject().get("token").getAsString();
                    String imei = " ";
                    //String imei = codeUsuario.getAsJsonObject().get("imei").getAsString();
                    String userlogin = codeUsuario.getAsJsonObject().get("userlogin").getAsString();
                    String passwlogin = codeUsuario.getAsJsonObject().get("passwlogin").getAsString();
                    //Log.d("Codigo - descr ",Codigo+" - "+descr);
                    newusuario.Setdatusuario(idportal ,nombre,userlogin,passwlogin,imei,token);


                    usuarioData.addUsuario(newusuario);
                } usuarioData.close();
                mgssalida="Actualizado correctamente";

            }else{
                mgssalida="No se Pudo actualizar, verifique su codigo IMEI";
                  }

        } else {
                mgssalida="error API - >" +success;
            Log.d("error API",""+success);
        }

       delegate.processFinish(mgssalida);
    }
    @Override
    protected void onCancelled() {
      //  mAuthTask = null;
       // showProgress(false);
    }

 }