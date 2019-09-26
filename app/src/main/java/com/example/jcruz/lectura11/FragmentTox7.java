package com.example.jcruz.lectura11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jcruz.lectura11.Model.DBfotoOperation;
import com.example.jcruz.lectura11.Model.Mlect;
import com.example.jcruz.lectura11.Model.MlectOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTox7.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTox7#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTox7 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog nDialog;
    int idportal=0;
    private  String  oratkn;
    private setODT Dataodt = null;
    private setFOTO Datafoto = null;
    private OnFragmentInteractionListener mListener;

    private MlectOperation lecturas;
    private DBfotoOperation fotos ;
    private Cursor datalec,dataFoto;
    private AlertDialog alertDialog2;
    private AlertDialog alertDialog3actualizar;

    public FragmentTox7() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTox7 newInstance(String param1, String param2) {
        FragmentTox7 fragment = new FragmentTox7();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    
    
    
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_x7, container, false);


        //Toast.makeText(getContext(),"Actualizar dato",Toast.LENGTH_LONG).show();


        Button ButtonActualizar = (Button) view.findViewById(R.id.actudb);
        ButtonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"isOnlineNet ok1",Toast.LENGTH_LONG).show();
                /*nDialog = new ProgressDialog(getContext()); //Here I get an error: The constructor ProgressDialog(PFragment) is undefined
                nDialog.setMessage("Actualizando..");
                nDialog.setTitle("Revisando ODT y rutas");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                */
                if (isNetDisponible()) {
                    if (isOnlineNet()) {
                        // attemptLogin();

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog2 = new AlertDialog.Builder(getContext()).create();
                        alertDialog3actualizar = new AlertDialog.Builder(getContext()).create();

                        alertDialog.setTitle("Actualizar a x7");
                        alertDialog.setMessage("¿Esta seguro de realizar esta accion?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(),"Sincronizando...",Toast.LENGTH_LONG).show();
                                        MainActivity myActivity = (MainActivity) getActivity();
                                        oratkn=myActivity.gettoken();
                                        idportal=myActivity.getidportal();
                                       // Toast.makeText(getContext(),oratkn,Toast.LENGTH_LONG).show();



                                        lecturas = new MlectOperation(getContext());

                                        lecturas.open();
                                        //  lecturas.delete();
                                        datalec = lecturas.getAllRutaEnviarTox7(idportal);

                                        JSONArray jsonArray_lecturas = new JSONArray();
                                        JSONArray API_lect_jsonArray_foto= new JSONArray();
                                        if(datalec.getCount() > 0){
                                         while(datalec.moveToNext()){
                                             JSONObject datajson = new JSONObject();		//Busqueda por medidor o secuencia

                                                int Vidtodt,Vidmedidor_idlec,Vlectura,Vintentos;
                                                String Vfchlec,Vidtanm1,Vidtanm2, Vcomentario,Ffecha,Fnombre,Fcontrato,Fbase64,Vedo;
                                             Vidtodt=datalec.getInt(datalec.getColumnIndex("idtodt"));
                                             Vidmedidor_idlec=datalec.getInt(datalec.getColumnIndex("idlec"));
                                             Vlectura=datalec.getInt(datalec.getColumnIndex("lectura"));

                                             Vfchlec=datalec.getString(datalec.getColumnIndex("fchlec"));
                                             Vidtanm1=datalec.getString(datalec.getColumnIndex("idtanm1"));
                                             Vidtanm2=datalec.getString(datalec.getColumnIndex("idtanm2"));
                                             Vcomentario=datalec.getString(datalec.getColumnIndex("comentario"));
                                             Vintentos=datalec.getInt(datalec.getColumnIndex("intentos"));

                                             Vedo=datalec.getString(datalec.getColumnIndex("edo"));




                                             try {					//Nombre de de foto asociado al contrado
                                                 datajson.put("idtodt", Vidtodt);
                                                 datajson.put("idlec", Vidmedidor_idlec);
                                                 datajson.put("lectura", Vlectura);
                                                 datajson.put("fchlec", Vfchlec);
                                                 datajson.put("idtanm1", Vidtanm1);
                                                 datajson.put("idtanm2", Vidtanm2);
                                                 datajson.put("comentario", Vcomentario);
                                                 datajson.put("intentos", Vintentos);
                                                 datajson.put("edo", Vedo);

                                             } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                 e.printStackTrace();
                                             }
                                            // jsonArray_lecturas.put(datajson);




                                             fotos = new DBfotoOperation(getContext());
                                             fotos.open();

                                             dataFoto = fotos.getAllFotoEnviarTox7(Vidtodt,Vidmedidor_idlec);

                                             //JSONObject fotojson2 = new JSONObject();
                                             JSONArray jsonArray_foto= new JSONArray();
                                           //  int vcuenta=0;
                                             if(dataFoto.getCount() > 0) {
                                                // JSONObject fotoObj = new JSONObject();



                                                 while (dataFoto.moveToNext()) {
                                                     JSONObject fotojson = new JSONObject();
                                                     JSONObject fotojson2 = new JSONObject();
                                                     Ffecha=dataFoto.getString(dataFoto.getColumnIndex("fecha"));
                                                     Fnombre=dataFoto.getString(dataFoto.getColumnIndex("nombre"));
                                                     Fcontrato=dataFoto.getString(dataFoto.getColumnIndex("contrato"));
                                                     Fbase64=dataFoto.getString(dataFoto.getColumnIndex("base64"));


                                                     try {
                                                         fotojson.put("fecha", Ffecha);
                                                         fotojson.put("nombre", Fnombre);
                                                         fotojson.put("contrato", Fcontrato);
                                                         fotojson2.put("odt", Vidtodt);
                                                         fotojson2.put("nombre", Fnombre);
                                                          fotojson2.put("base64", Fbase64);
                                                         jsonArray_foto.put( fotojson);
                                                         API_lect_jsonArray_foto.put(fotojson2);


                                                     } catch (JSONException e) {
                                                         // TODO Auto-generated catch block
                                                         e.printStackTrace();
                                                     }






                                                 }



                                                 try {
                                                    // fotoObj.put("foto", fotojson);
                                                     datajson.put("foto", jsonArray_foto);
                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }
                                                 //jsonArray_lecturas.put(fotoObj);


                                             }

                                             jsonArray_lecturas.put(datajson);

                                             fotos.close();

                                        }
                                        }

                                       lecturas.close();




                                        JSONArray jsonArray = new JSONArray();


                                        //jsonArray.put(student2);

                                        JSONObject lecturaObj = new JSONObject();

                                        try {
                                            lecturaObj.put("idusuario", idportal);
                                            lecturaObj.put("oratkn", oratkn);
                                            lecturaObj.put("lectura", jsonArray_lecturas);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        String jsonStr = lecturaObj.toString();
                                        String APIfotojsonStr = API_lect_jsonArray_foto.toString();

                                        Log.d("JSON",jsonStr);
                                        Log.d("fotoAPIlect",APIfotojsonStr);






                                        Dataodt = new setODT(jsonStr,idportal);
                                       Dataodt.execute((Void) null);


                                        Datafoto = new setFOTO(APIfotojsonStr,idportal);
                                        Datafoto.execute((Void) null);


                                    }
                                });

                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(),"Accion cancelada...",Toast.LENGTH_LONG).show();
                                    }
                                });

                        alertDialog.show();



                    } else {
                       // nDialog.hide();
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("Conexion");
                        alertDialog.setMessage("No se pudo establecer la conexion a internet");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }


                } else {
                    //nDialog.hide();
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Red");
                    alertDialog.setMessage("No hay conexion a la red disponible");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });









        //return inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
             //   getSystemService(Context.CONNECTIVITY_SERVICE);
        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public Boolean isOnlineNet() {
/*
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

        */
        return true;
    }


    public class setODT extends AsyncTask<Object, Object, String> {


        private final String mdata;
        private final int midportal;
        private String MSGODT;

        setODT( String data,int portal) {


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
            data = "action=setodt&oratkn="+oratkn+"&auth="+"&data="+mdata;
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
            Dataodt = null;
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

                int total;

/*
                if(status==1){

                    odtarray  = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get ("ODT").getAsJsonObject();

                      Mlect newregistro = new Mlect();
                    MlectOperation lectData = new MlectOperation(getContext());
                   lectData.open();
                        lectData.TRUNCATE();

                    //total =  odtarray.getAsJsonObject().entrySet();

                    JsonObject obj = odtarray.getAsJsonObject(); //since you know it's a JsonObject
                    Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
                    for (Map.Entry<String, JsonElement> entry: entries) {
                       // System.out.println(entry.getKey());

                        Log.d("odt entry ",  "  "+ entry.getKey());
                        odtarray2  =odtarray.getAsJsonObject().get(entry.getKey());
                        Log.d("odt odtarray2",  "  "+ odtarray2);

                        String Ruta = odtarray2.getAsJsonObject().get("Ruta").getAsString();
                        medidor = odtarray2.getAsJsonObject().get("medidor").getAsJsonObject();
                        Log.d("odt Ruta",  " -> "+ Ruta);

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                        String formattedDate = df.format(c);

                        JsonObject objmedidor = medidor.getAsJsonObject(); //since you know it's a JsonObject
                        Set<Map.Entry<String, JsonElement>> entriesmedidor = objmedidor.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry2: entriesmedidor) {
                            medidor2  =medidor.getAsJsonObject().get(entry2.getKey());
                            Log.d("odt entry2",  "  "+ entry2.getKey());
                            Log.d("odt medidor2",  "  "+ medidor2);

                            //Log.d("idtlec",  "  "+ medidor2.getAsJsonObject().get("idtlec").getAsInt());
                            //Log.d("rng1",  "  "+ medidor2.getAsJsonObject().get("rng1").getAsInt());
                            //Log.d("giro",  "  "+ medidor2.getAsJsonObject().get("giro").getAsString());
                            Log.d("cliente",  "  "+ medidor2.getAsJsonObject().get("cliente").getAsString());

                           // newregistro.getidtclt(medidor2.getAsJsonObject().get("idtlec").getAsString());
                             //newregistro.setidtclt(   medidor2.getAsJsonObject().get("idtlec").getAsString());
                            //newregistro.setidtodt(   medidor2.getAsJsonObject().get("rng1").getAsInt());
                            //newregistro.setidtclt(   medidor2.getAsJsonObject().get("cliente").getAsString());
                            //newregistro.setrng1(   medidor2.getAsJsonObject().get("rng1").getAsInt());

                            newregistro.Setdatalect(
                                    medidor2.getAsJsonObject().get("idtlec").getAsInt(),
                                     Integer.parseInt( entry.getKey()),//odt
                                    Ruta,
                                    entry2.getKey(),//idmedidor
                                    medidor2.getAsJsonObject().get("medidor").getAsString(),// numcpr


                                    medidor2.getAsJsonObject().get("secuencia").getAsInt(),//secuencia
                                    medidor2.getAsJsonObject().get("cliente").getAsString(),//cliente
                                    medidor2.getAsJsonObject().get("idtctr").getAsString(),//contrato
                                   // medidor2.getAsJsonObject().get("idtusrlec").getAsInt(),//usuario
                                    5,
                                    formattedDate,//fchuac
                                    medidor2.getAsJsonObject().get("edocliente").getAsString(),//edo,
                                    medidor2.getAsJsonObject().get("giro").getAsString(),//giro
                                    medidor2.getAsJsonObject().get("uso").getAsString(),
                                    medidor2.getAsJsonObject().get("direccion").getAsString(),//domicilioo,
                                    medidor2.getAsJsonObject().get("LECTURAPROM").getAsInt(),//promedio
                                    medidor2.getAsJsonObject().get("LECTURAANT").getAsInt(),//promedio anterior
                                    "NVO",
                                    0




                            );


                            lectData.addLectura(newregistro);



                        }





                    }


                    lectData.close();



                    //Log.d("odt array count",  "  "+ odtarray);

                   // Log.d("odt nad",  "total   "+total);




                    int  cns;
                    try {
                        //cns = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("servicios").getAsJsonObject().get("cns").getAsJsonObject().get("operativo").getAsInt();

                        cns=1;
                        // Log.d("cns", cns + "");
                        if(cns==1){
                            Log.d("estatus ok", status + "");
                            //Toast.makeText(getContext(),"ODT descargada",Toast.LENGTH_LONG).show();

                        }
                        else{
                            //Log.d("acceso negado", cns + "");
                            Toast.makeText(getContext(),"Acceso negado por el administrador de la aplicacion",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e){
                        //Log.d("cns", "Servicio no disponible para este usuario");
                        Toast.makeText(getContext(),"Servicio no disponible para este usuario",Toast.LENGTH_LONG).show();
                    }

                }else{
                    //Log.d("valida", "Usuario no valido");
                    Toast.makeText(getContext(),"odt no valido",Toast.LENGTH_LONG).show();
                    Log.d("odt", success);
                }

                */
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
                MSGODT = "Error en el servicio. "+success;
            }

            alertDialog3actualizar.setTitle("Respuesta ODT");
            alertDialog3actualizar.setMessage(MSGODT);
            alertDialog3actualizar.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog3actualizar.show();

        }
        @Override
        protected void onCancelled() {
            Dataodt = null;
            //showProgress(false);
        }
    }

    public class setFOTO extends AsyncTask<Object, Object, String> {


        private final String mdata;
        private final int midportal;
        private  String MSG;


        setFOTO( String data,int portal) {


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
            Dataodt = null;
            //   showProgress(false);
            Log.d("cadena",success);
            int v =success.length();
            if (v>4 ) {

                //  mPasswordView.setError(success);
                //mPasswordView.requestFocus();
                Log.d("Exito foto","exitooo");
                Log.d("Exitofoto",success);

                // Intent intent = new Intent(getApplicationContext(), VerJon.class);
                // EditText editText = (EditText)findViewById(R.id.Addjson);
                // String mensaje = editText.getText().toString();

                //intent.putExtra("json", success);
                //startActivity(intent);
                //Log.d("jsonrespuesta", success);
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

/*
                if(status==1){

                    odtarray  = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get ("ODT").getAsJsonObject();

                      Mlect newregistro = new Mlect();
                    MlectOperation lectData = new MlectOperation(getContext());
                   lectData.open();
                        lectData.TRUNCATE();

                    //total =  odtarray.getAsJsonObject().entrySet();

                    JsonObject obj = odtarray.getAsJsonObject(); //since you know it's a JsonObject
                    Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
                    for (Map.Entry<String, JsonElement> entry: entries) {
                       // System.out.println(entry.getKey());

                        Log.d("odt entry ",  "  "+ entry.getKey());
                        odtarray2  =odtarray.getAsJsonObject().get(entry.getKey());
                        Log.d("odt odtarray2",  "  "+ odtarray2);

                        String Ruta = odtarray2.getAsJsonObject().get("Ruta").getAsString();
                        medidor = odtarray2.getAsJsonObject().get("medidor").getAsJsonObject();
                        Log.d("odt Ruta",  " -> "+ Ruta);

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                        String formattedDate = df.format(c);

                        JsonObject objmedidor = medidor.getAsJsonObject(); //since you know it's a JsonObject
                        Set<Map.Entry<String, JsonElement>> entriesmedidor = objmedidor.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry2: entriesmedidor) {
                            medidor2  =medidor.getAsJsonObject().get(entry2.getKey());
                            Log.d("odt entry2",  "  "+ entry2.getKey());
                            Log.d("odt medidor2",  "  "+ medidor2);

                            //Log.d("idtlec",  "  "+ medidor2.getAsJsonObject().get("idtlec").getAsInt());
                            //Log.d("rng1",  "  "+ medidor2.getAsJsonObject().get("rng1").getAsInt());
                            //Log.d("giro",  "  "+ medidor2.getAsJsonObject().get("giro").getAsString());
                            Log.d("cliente",  "  "+ medidor2.getAsJsonObject().get("cliente").getAsString());

                           // newregistro.getidtclt(medidor2.getAsJsonObject().get("idtlec").getAsString());
                             //newregistro.setidtclt(   medidor2.getAsJsonObject().get("idtlec").getAsString());
                            //newregistro.setidtodt(   medidor2.getAsJsonObject().get("rng1").getAsInt());
                            //newregistro.setidtclt(   medidor2.getAsJsonObject().get("cliente").getAsString());
                            //newregistro.setrng1(   medidor2.getAsJsonObject().get("rng1").getAsInt());

                            newregistro.Setdatalect(
                                    medidor2.getAsJsonObject().get("idtlec").getAsInt(),
                                     Integer.parseInt( entry.getKey()),//odt
                                    Ruta,
                                    entry2.getKey(),//idmedidor
                                    medidor2.getAsJsonObject().get("medidor").getAsString(),// numcpr


                                    medidor2.getAsJsonObject().get("secuencia").getAsInt(),//secuencia
                                    medidor2.getAsJsonObject().get("cliente").getAsString(),//cliente
                                    medidor2.getAsJsonObject().get("idtctr").getAsString(),//contrato
                                   // medidor2.getAsJsonObject().get("idtusrlec").getAsInt(),//usuario
                                    5,
                                    formattedDate,//fchuac
                                    medidor2.getAsJsonObject().get("edocliente").getAsString(),//edo,
                                    medidor2.getAsJsonObject().get("giro").getAsString(),//giro
                                    medidor2.getAsJsonObject().get("uso").getAsString(),
                                    medidor2.getAsJsonObject().get("direccion").getAsString(),//domicilioo,
                                    medidor2.getAsJsonObject().get("LECTURAPROM").getAsInt(),//promedio
                                    medidor2.getAsJsonObject().get("LECTURAANT").getAsInt(),//promedio anterior
                                    "NVO",
                                    0




                            );


                            lectData.addLectura(newregistro);



                        }





                    }


                    lectData.close();



                    //Log.d("odt array count",  "  "+ odtarray);

                   // Log.d("odt nad",  "total   "+total);




                    int  cns;
                    try {
                        //cns = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("servicios").getAsJsonObject().get("cns").getAsJsonObject().get("operativo").getAsInt();

                        cns=1;
                        // Log.d("cns", cns + "");
                        if(cns==1){
                            Log.d("estatus ok", status + "");
                            //Toast.makeText(getContext(),"ODT descargada",Toast.LENGTH_LONG).show();

                        }
                        else{
                            //Log.d("acceso negado", cns + "");
                            Toast.makeText(getContext(),"Acceso negado por el administrador de la aplicacion",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e){
                        //Log.d("cns", "Servicio no disponible para este usuario");
                        Toast.makeText(getContext(),"Servicio no disponible para este usuario",Toast.LENGTH_LONG).show();
                    }

                }else{
                    //Log.d("valida", "Usuario no valido");
                    Toast.makeText(getContext(),"odt no valido",Toast.LENGTH_LONG).show();
                    Log.d("odt", success);
                }

                */
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
            alertDialog2.setTitle("Respuesta Fotos");
            alertDialog2.setMessage(MSG);
            alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog2.show();


        }
        @Override
        protected void onCancelled() {
            Dataodt = null;
            //showProgress(false);
        }
    }


}
