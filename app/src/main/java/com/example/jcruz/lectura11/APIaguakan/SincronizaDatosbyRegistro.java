package com.example.jcruz.lectura11.APIaguakan;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jcruz.lectura11.MainActivity;
import com.example.jcruz.lectura11.Model.DBfotoOperation;
import com.example.jcruz.lectura11.Model.MlectOperation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SincronizaDatosbyRegistro{
    private  int idportal=0;
    private  String  oratkn;
    private ActualizaPorRegistro Dataodt = null;
    private setFOTObyRegistro Datafoto = null;

    private MlectOperation lecturas;
    private DBfotoOperation fotos ;
    private Cursor datalec,dataFoto;
    private Context micontexto;

    public SincronizaDatosbyRegistro(String oratkn ,int idportal ,Context mycontexto){
        this.micontexto=mycontexto;
        this.idportal=idportal;
        this.oratkn=oratkn;
    }

    public void sincronizar() {


        //Toast.makeText(getContext(),"Sincronizando...",Toast.LENGTH_LONG).show();
      //  MainActivity myActivity = (MainActivity) getActivity();
       // oratkn = myActivity.gettoken();
       // idportal = myActivity.getidportal();
        // Toast.makeText(getContext(),oratkn,Toast.LENGTH_LONG).show();


        lecturas = new MlectOperation(micontexto);

        lecturas.open();
        //  lecturas.delete();
        datalec = lecturas.getAllRutaEnviarTox7(idportal);

        JSONArray jsonArray_lecturas = new JSONArray();
        JSONArray API_lect_jsonArray_foto = new JSONArray();
        if (datalec.getCount() > 0) {
            while (datalec.moveToNext()) {
                JSONObject datajson = new JSONObject();        //Busqueda por medidor o secuencia

                int Vidtodt, Vidmedidor_idlec, Vlectura;
                String Vfchlec, Vidtanm1, Vidtanm2, Vcomentario, Ffecha, Fnombre, Fcontrato, Fbase64;
                Vidtodt = datalec.getInt(datalec.getColumnIndex("idtodt"));
                Vidmedidor_idlec = datalec.getInt(datalec.getColumnIndex("idlec"));
                Vlectura = datalec.getInt(datalec.getColumnIndex("lectura"));

                Vfchlec = datalec.getString(datalec.getColumnIndex("fchlec"));
                Vidtanm1 = datalec.getString(datalec.getColumnIndex("idtanm1"));
                Vidtanm2 = datalec.getString(datalec.getColumnIndex("idtanm2"));
                Vcomentario = datalec.getString(datalec.getColumnIndex("comentario"));


                try {                    //Nombre de de foto asociado al contrado
                    datajson.put("idtodt", Vidtodt);
                    datajson.put("idlec", Vidmedidor_idlec);
                    datajson.put("lectura", Vlectura);
                    datajson.put("fchlec", Vfchlec);
                    datajson.put("idtanm1", Vidtanm1);
                    datajson.put("idtanm2", Vidtanm2);
                    datajson.put("comentario", Vcomentario);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // jsonArray_lecturas.put(datajson);


                fotos = new DBfotoOperation(micontexto);
                fotos.open();

                dataFoto = fotos.getAllFotoEnviarTox7(Vidtodt, Vidmedidor_idlec);

                //JSONObject fotojson2 = new JSONObject();
                JSONArray jsonArray_foto = new JSONArray();
                //  int vcuenta=0;
                if (dataFoto.getCount() > 0) {
                    // JSONObject fotoObj = new JSONObject();


                    while (dataFoto.moveToNext()) {
                        JSONObject fotojson = new JSONObject();
                        JSONObject fotojson2 = new JSONObject();
                        Ffecha = dataFoto.getString(dataFoto.getColumnIndex("fecha"));
                        Fnombre = dataFoto.getString(dataFoto.getColumnIndex("nombre"));
                        Fcontrato = dataFoto.getString(dataFoto.getColumnIndex("contrato"));
                        Fbase64 = dataFoto.getString(dataFoto.getColumnIndex("base64"));


                        try {
                            fotojson.put("fecha", Ffecha);
                            fotojson.put("nombre", Fnombre);
                            fotojson.put("contrato", Fcontrato);
                            fotojson2.put("odt", Vidtodt);
                            fotojson2.put("nombre", Fnombre);
                            fotojson2.put("base64", Fbase64);
                            jsonArray_foto.put(fotojson);
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

        Log.d("JSON", jsonStr);
        Log.d("fotoAPIlect", APIfotojsonStr);


        Dataodt = new ActualizaPorRegistro(jsonStr, idportal, oratkn, micontexto);
        Dataodt.execute((Void) null);


        Datafoto = new setFOTObyRegistro(APIfotojsonStr, idportal, oratkn, micontexto);
        Datafoto.execute((Void) null);

    }
}
