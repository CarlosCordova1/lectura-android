package com.example.jcruz.lectura11;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.jcruz.lectura11.APIaguakan.SincronizaDatosbyRegistro;
import com.example.jcruz.lectura11.Model.DBfotoOperation;
import com.example.jcruz.lectura11.Model.Mlect;
import com.example.jcruz.lectura11.Model.MlectOperation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

public class MedidorActivity extends AppCompatActivity {




    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapter2;




    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager, mViewPager2;
    private int  odtvalue,idportal=0;
    private String  oratoken;
    private MlectOperation lecturas,Searchlecturas;
    private List<Mlect> datalec,Searchdatalec;
    private int[] odtlista;
    private Context mycontexto;
    private Menu menu;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();



         odtvalue = intent.getIntExtra("odt",0);
        idportal=intent.getIntExtra("idportal",0);

        oratoken=intent.getStringExtra("oratoken");
        //Toast.makeText(getBaseContext(), "medidor odt -> "+odtvalue+"  idportal -> "+idportal,
          //      Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_medidor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_buscar);

        setSupportActionBar(toolbar);

        mycontexto=getApplicationContext();
        lecturas = new MlectOperation(getApplicationContext());
        lecturas.open();
        //  lecturas.delete();
        datalec = lecturas.getAllRutabyODT(odtvalue, idportal);
        lecturas.close();
        //odtlista = new int[datalec.size()] ;
        //for (int i = 0; i < datalec.size(); i++) {
            //HashMap<String, String> map = new HashMap<String, String>();
            //map.put("brand_names","  ODT " + datalec.get(i).getidtodt()+"  id  "+datalec.get(i).getid());
           // map.put("brand_images", Integer.toString(brandsImages[0]));
         //   odtlista[i]=(datalec.get(i).getidtodt());
            //aList.add(map);
       // }

        //Log.d("datalec"," "+datalec.size());
        //Log.d("datalec"," "+datalec.size());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.settotal(datalec.size(),datalec,mycontexto,odtvalue,"all","",idportal,oratoken);
        mSectionsPagerAdapter.notifyDataSetChanged();
                      // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
       // mViewPager2 = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("sadsdsda");
        actionBar.setDisplayHomeAsUpEnabled(true);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lectura = (EditText)mViewPager.findViewById(R.id.Lectura);
                String lect = lectura.getText().toString();


                Snackbar.make(view, "Replace -> "+lect, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public void onBackPressed() {
    }
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medidor, menu);

        this.menu = menu;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            //SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

           // SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

            MenuItem menuItem = menu.findItem(R.id.action_search);
             searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    callSearch(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                   // callSearch(newText);
                    if (TextUtils.isEmpty(newText)){
                    // Toast.makeText(getBaseContext(), "Do close1  "+searchView.getQuery(),   Toast.LENGTH_LONG).show();
                    }
//              }
                    return true;
                }

                public void callSearch(String query) {
                    //Do searching
                   // Toast.makeText(getBaseContext(), "Do searching "+searchView.getQuery(), Toast.LENGTH_LONG).show();
                   String doBusqueda=searchView.getQuery().toString();
                    Intent newAct = new Intent(getApplicationContext(), BusquedaMedidorActivity.class);
                    newAct.putExtra("doBusqueda", doBusqueda);
                    newAct.putExtra("odt", odtvalue);
                    newAct.putExtra("idportal", idportal);

                    startActivity(newAct);

                }

                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // TODO Auto-generated method stub
                   // Toast.makeText(getBaseContext(), "Do close2  "+searchView.getQuery(), Toast.LENGTH_LONG).show();
                    return true;
                }



            });
          //  search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /* if (id == R.id.action_settings) {
            return true;
        }*/
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("oratkn", datausuario.get(0).gettoken());
               // intent.putExtra("name", datausuario.get(0).getnombre());
                intent.putExtra("idportal", idportal);
                startActivity(intent);
               // finish();




                finish();
                onBackPressed();




                return true;
            case R.id.action_porcentaje:
                Intent intent1 = new Intent(this,ActivityPorcentaje.class);
                intent1.putExtra("odt",odtvalue);
                intent1.putExtra("idportal", idportal);
                this.startActivity(intent1);
                return true;

            case R.id.action_Pendientes:
                Intent intent2 = new Intent(this,PendienteMedidorActivity.class);
                intent2.putExtra("odt",odtvalue);
                intent2.putExtra("idportal",idportal);
                this.startActivity(intent2);
                return true;


        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment  {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";
        private EditText lectura;
        private long id;
        private int idtlec,odt, secuencia, consumoprom, lecturaant, limitedown,  limiteup,limitedown2,limiteup2,nruedas, intentos,  nuevosintentos;


        private int idportal, dlectura;
        private String oratoken,fechacorte,idedocliente,edo;
        private String contrato;
        private String idanm1;
        private String idanm2;
        private  SincronizaDatosbyRegistro sindata;

        private ValidaLectura l;




        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,  List<Mlect> datalec_2_2, int total,
                                                      Context Vmycont, int odtnum , String bandera,String Busqueda, int idportal, String oratoken) {
            PlaceholderFragment fragment = new PlaceholderFragment();

             MlectOperation lecturas2;
             List<Mlect> datalec2;

             DBfotoOperation fotos ;
             Cursor dataFoto;

            lecturas2 = new MlectOperation(Vmycont);
            lecturas2.open();
            //Log.d("bandera",bandera);
            //Log.d("Busqueda",Busqueda);

            datalec2 = lecturas2.getAllRutabyODT(odtnum, idportal);

            //Log.d("datalec2.size()",""+datalec2.size());
           // Log.d("sectionNumber",""+sectionNumber);
            //  lecturas.delete();

            lecturas2.close();

            Bundle args=null;
            args = new Bundle();


            for (int i = 0; i < datalec2.size(); i++) {
                if (i==sectionNumber-1){
                    setTestdata();

                    args.putInt("idportal",  idportal);
                    args.putString("oratoken",  oratoken);

                     args.putLong("id",  datalec2.get(i).getid());
                    args.putInt("odt",  datalec2.get(i).getidtodt());
                    args.putInt("idtlec",  datalec2.get(i).getidtlec());
                    args.putString("idanm1",  datalec2.get(i).getidtanm1());
                    args.putString("idanm2",  datalec2.get(i).getidtanm2());
                    args.putInt("lectura",  datalec2.get(i).getlectura());
                    args.putInt("secuencia",  datalec2.get(i).getrng1());
                    //Log.d("idanm1","---> "+datalec2.get(i).getidtanm1());
                    //Log.d("idanm2","---> "+ datalec2.get(i).getidtanm2());

                    args.putString("numcpr",  "numcpr: "+datalec2.get(i).getnumcpr());
                    int anomalicount=0;
                    String A="";
                    String C="";
                    String F="";
                    if(datalec2.get(i).getidtanm1()!=null){
                        anomalicount++;
                    }
                    if(datalec2.get(i).getidtanm2()!=null){
                        anomalicount++;
                    }
                    if(anomalicount!=0){
                        A=" A:"+ anomalicount;
                    }

                    if(datalec2.get(i).getcomentario()!=null){
                        C=" C:1";
                    }
                    fotos = new DBfotoOperation(Vmycont);
                    fotos.open();
                    dataFoto = fotos.getAllFotoEnviarTox7(datalec2.get(i).getidtodt(),datalec2.get(i).getidtlec());

                     if (dataFoto.getCount()!=0){
                         F=" F:"+dataFoto.getCount();
                     }
                    fotos.close();

                    args.putString("ruta",  "Ruta: "+datalec2.get(i).getruta()+"\n"+A+F+C);




                    args.putString("medidor",  ""+datalec2.get(i).getMedidor());
                    args.putString("uso",  "uso: "+datalec2.get(i).getUso());
                    args.putString("Domicilio",  "Domicilio: "+datalec2.get(i).getdomicilio());
                    args.putString("rng1",  "Ruta: "+datalec2.get(i).getruta());
                    args.putString("giro",  "Giro: "+datalec2.get(i).getgironegocio());

                    args.putString("cliente",  "Cliente: "+datalec2.get(i).getidtclt());
                    args.putString("contrato", datalec2.get(i).getidtctr());
                    args.putInt("indice", sectionNumber);
                    args.putInt("total", datalec2.size());

                    args.putInt("consumoprom", datalec2.get(i).getconsumoprom());
                    args.putInt("lecturaant", datalec2.get(i).getlecturaant());
                    args.putInt("limitedown", datalec2.get(i).getlimitedown());
                    args.putInt("limiteup", datalec2.get(i).getlimiteup());

                    args.putInt("limitedown2", datalec2.get(i).getlimitedown2());
                    args.putInt("limiteup2", datalec2.get(i).getlimiteup2());
                    args.putInt("nruedas", datalec2.get(i).getruedas());
                    args.putString("fechacorte", datalec2.get(i).getfechacorte());

                    args.putString("edocliente", datalec2.get(i).getedocliente());
                    args.putString("edo", datalec2.get(i).getedo());

                    args.putString("idedocliente", datalec2.get(i).getidedocliente());
                    args.putInt("intentos", datalec2.get(i).getintentos());



                }

            //HashMap<String, String> map = new HashMap<String, String>();
           // map.put("brand_names","  ODT " + datalec_2.get(i).getidtodt()+"  id  "+datalec_2.get(i).getid());
           //  map.put("brand_images", Integer.toString(brandsImages[0]));
            //   odtlista[i]=(datalec.get(i).getidtodt());
           // aList.add(map);
             }





            fragment.setArguments(args);

            return fragment;
        }

        private static void setTestdata() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_medidor, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.textView2Secuencia);
            TextView textViewmedidor =(TextView) rootView.findViewById(R.id.section_labelr1);
            TextView textViewDomicilio =(TextView) rootView.findViewById(R.id.section_domicilio);
            TextView textViewsection_ruta =(TextView) rootView.findViewById(R.id.section_ruta);
            TextView textViewgiro =(TextView) rootView.findViewById(R.id.section_labelgiro);

            TextView textViewsection_cliente =(TextView) rootView.findViewById(R.id.section_cliente);
            TextView textView_contrato =(TextView) rootView.findViewById(R.id.section_contrato);

            TextView textView_edo =(TextView) rootView.findViewById(R.id.section_edo);

            idportal=getArguments().getInt("idportal");
            oratoken= getArguments().getString("oratoken");



            String cliente =getArguments().getString("cliente");
             edo =getArguments().getString("edo");
            final String edocliente  =getArguments().getString("edocliente");

             contrato =getArguments().getString("contrato");
            String Domicilio =getArguments().getString("Domicilio");
            String ruta =getArguments().getString("ruta");
            String giro =getArguments().getString("giro");
            String medidor =getArguments().getString("medidor");

             idanm1 =getArguments().getString("idanm1");
             idanm2 =getArguments().getString("idanm2");

            consumoprom =getArguments().getInt("consumoprom");
             lecturaant=getArguments().getInt("lecturaant");
            limitedown =getArguments().getInt("limitedown");
             limiteup=getArguments().getInt("limiteup");

            limitedown2 =getArguments().getInt("limitedown2");
            limiteup2=getArguments().getInt("limiteup2");
            nruedas=getArguments().getInt("nruedas");
            fechacorte=getArguments().getString("fechacorte");


            intentos=getArguments().getInt("intentos");
             nuevosintentos=intentos;
            idedocliente=getArguments().getString("idedocliente");

            //Toast.makeText(getContext(), "idedocliente -> "+idedocliente, Toast.LENGTH_SHORT).show();


             secuencia =getArguments().getInt("secuencia");
            int indice =getArguments().getInt("indice");
            int total =getArguments().getInt("total");
             dlectura =getArguments().getInt("lectura");

             id=getArguments().getLong("id");
              idtlec=getArguments().getInt("idtlec");
              odt=getArguments().getInt("odt");

            textViewsection_cliente.setText(cliente );
            textView_contrato.setText("Contrato: "+contrato );
            textView_edo.setText("Edo: "+edocliente );
            textViewDomicilio.setText(Domicilio );
            textViewsection_ruta.setText(ruta );
            textViewgiro.setText(giro);

            textViewmedidor.setText(medidor );
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
           // textView.setText(ARG_SECTION_NUMBER+ "Sec: "+getArguments().getInt(ARG_SECTION_NUMBER));
            textView.setText( "Sec: " +secuencia+"\n"+indice +" de: "+total);

            //getArguments().remove("total");
            //getArguments().remove("secuencia");
            //getArguments().remove("indice");


            //AutoCompleteTextView text = (AutoCompleteTextView)textView.findViewById(R.id.Medidor);

             lectura = (EditText)rootView.findViewById(R.id.Lectura);
             if (dlectura!=0){

                 lectura.setText(""+ dlectura);
             }

            // if (!edo.equals("CAP")){
                // Toast.makeText(getContext(), "edo-> "+edo, Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                nuevosintentos++;
                int lectActual=0;
                String lectActual2 =  lectura.getText().toString();
                if (!lectActual2.isEmpty()){

                    if (lectActual2.length()<=nruedas){
                        lectActual = Integer.parseInt( lectura.getText().toString());
                        l = new ValidaLectura(lectActual,lecturaant,limitedown,limiteup ,id, odt, idtlec,contrato,idanm1,idanm2, getContext(),limitedown2,limiteup2,nruedas,fechacorte,idedocliente,intentos,nuevosintentos,edo);
                        l.init(new ValidaLectura.MyClassListener() {
                            @Override
                            public void onSomeEvent(boolean answer) {
                               // Toast.makeText(getContext(), "onSomeEvent "+answer,  Toast.LENGTH_SHORT).show();

                                if (answer){}
                                else{
                                    Toast.makeText(getContext(), "Accion cancelada", Toast.LENGTH_SHORT).show();
                                    if (dlectura!=0){

                                        lectura.setText(""+ dlectura);
                                    }
                                    else{
                                        lectura.setText("");
                                    }
                                }

                            }
                        });
                    }
                    else{

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Alerta");
       // final EditText input = new EditText(getContext());
          alert.setMessage("La cantidad de digitos no coincide con el numero de ruedas del medidor");
                        if (dlectura!=0){

                            lectura.setText(""+ dlectura);
                        }
                        else{
                            lectura.setText("");
                        }
        //input.setInputType(InputType.TYPE_CLASS_NUMBER);
        //input.setRawInputType(Configuration.KEYBOARD_12KEY);
        //alert.setView(input);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Put actions for OK button here
                Toast.makeText(getContext(), "Accion cancelada",  Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();

                    }


            }

            else{
                //la captura es vacia
            }
            }

        });

            FloatingActionButton botonmas = (FloatingActionButton)rootView.findViewById(R.id.botonmas);
            botonmas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     // Toast.makeText(getContext(), "Menu detalles ", Toast.LENGTH_SHORT).show();
                    Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
                    newAct.putExtra("id",id);
                    newAct.putExtra("odt",odt);
                    newAct.putExtra("idportal",idportal);

                    newAct.putExtra("contrato",contrato);
                    newAct.putExtra("idtlec",idtlec);
                    newAct.putExtra("idanm1",idanm1);
                    newAct.putExtra("idanm2",idanm2);


                    startActivity(newAct);

                }
            });

/*
             }
             else{
                 lectura.setEnabled(false);
                 FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
                 fab.setEnabled(false);
                 FloatingActionButton botonmas = (FloatingActionButton)rootView.findViewById(R.id.botonmas);
                 botonmas.setEnabled(false);
             }
             */

           //sindata =new SincronizaDatosbyRegistro(oratoken , idportal , getContext());
             //sindata.sincronizar();



            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int total;
        private int ODTvalor;
        private List<Mlect> datalec_init;
        private Context Vmycont;
         private String band;
        private String busqueda;
        private int  idportal=0;
        private String  oratoken;

        public void  settotal(int vtotal, List<Mlect> dataodt,Context mycont, int odtval ,String bandera ,String vbusqueda, int idportal, String oratoken ){
            this.total=vtotal;
            this.datalec_init=dataodt;
            this.Vmycont=mycont;
            this.ODTvalor=odtval;
            this.band=bandera;
            this.busqueda=vbusqueda;
            this.idportal=idportal;
            this.oratoken=oratoken;
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,datalec_init, total, Vmycont,ODTvalor,band,busqueda, idportal,oratoken);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return total;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

    }


}


  class MyInterface {

    DialogReturn dialogReturn;

    public interface DialogReturn {

        void onDialogCompleted(boolean answer);
        void onDialogCompleted_Esigual(boolean answer);
        void onDialogCompletedEntradaDatos(boolean answer,String input);
        void  onDialogCompleted_confirmaLectura (boolean answer,String input);
        void   onDialogCompletedEntradaDatos_medidorLimitado (boolean answer,String input);
        void  onDialogCompletedEntradaDatos_lecturaEsMenor  (boolean answer,String input);
        void onDialogCompletedLecturaEsmenor (boolean answer);

        void setFinproceso(boolean answer);
    }

     public void setListener(DialogReturn dialogReturn) {
         this.dialogReturn = dialogReturn;
     }
     public void setListenerEntradaDatos(DialogReturn dialogReturn) {
         this.dialogReturn = dialogReturn;
     }

    public DialogReturn getListener() {
        return dialogReturn;

    }
}
 interface MyClassListener {
    // add whatever methods you need here
    public void onSomeEvent(boolean answer);
}

  class ValidaLectura implements MyInterface.DialogReturn{

      public  MyClassListener mListener = null;

      // provide a way for another class to set the listener
      /*public void setMyClassListener(MyClassListener listener) {
          this.mListener = listener;
      }
      */


      public interface MyClassListener {
          public void onSomeEvent(boolean answer);
      }





         MyInterface myInterface;
     MyInterface.DialogReturn dialogReturn;

    private int lectActual,lectAntetior,limitebajo,limitealto ,idtlec,odt,limitebajo2,limitealto2,nruedas,intentos,nuevosintentos;
     private String lecturaConfirmada;
    private long id;
     private String contrato,idedocliente,edocliente, txt;
     private String idanm1;
     private String idanm2,fechacorte;
    private  Context context;

    private boolean _valida,_pasoCero,limitado,validaEntradadato;

     private void setDataConfirma(String vl){
        this.lecturaConfirmada=vl;
    }
     private void setvalidaEntradadato(Boolean vl){
         this.validaEntradadato=vl;
     }

   private boolean res;

     private String  vtend="";
    public  ValidaLectura(
            int lectActual,
            int lectAntetior,
            int limitedown,
            int limiteup,
            long id,
            int odt,
            int idtlec,
            String contrato,
            String idanm1,
            String idanm2,
            Context context,
            int limitedown2, int limiteup2, int nruedas,String fechacorte,String idedocliente,int intentos,int nuevosintentos,String edocliente
    ){
        this.lectActual=lectActual;
        this.lectAntetior=lectAntetior;
        this.limitebajo=limitedown;
        this.limitealto=limiteup;
        this.context=context;
        this.id=id;
        this.odt=odt;
        this.idtlec=idtlec;
        this.contrato=contrato;
        this.idanm1=idanm1;
        this.idanm2=idanm2;

        this.limitebajo2=limitedown2;
        this.limitealto2=limiteup2;
        this.nruedas=nruedas;
        this.fechacorte=fechacorte;

        this.idedocliente=idedocliente;
        this.intentos=intentos;
        this.nuevosintentos=nuevosintentos;
        this.edocliente=edocliente;

    }

public Context getContext(){
        return  this.context;
}

     public void setFinproceso( boolean answer2) {
        // Toast.makeText(getContext(), "termino proceso"+answer2, Toast.LENGTH_SHORT).show();
         //this.txt=answer2;
         //myInterface = new MyInterface();
       //  myInterface.setListener(this);
     //    myInterface.getListener().setFinproceso(true);
         this.mListener.onSomeEvent(false);

     }



     private void cerrarProceso(){


          myInterface = new MyInterface();
         myInterface.setListener(this);
         myInterface.getListener().setFinproceso(true);
        // Toast.makeText(getContext(), "termino proceso "+ this.res, Toast.LENGTH_SHORT).show();

     }


private void validalimitado(){
    switch(this.idedocliente)
    {
        case "LIM":
        case "CORDRE":
        case "CPD":
        case "GD5COR":
        case "GD5SDE":
            this.limitado=true;
            break;
        default:
            this.limitado=false;
            break;

    }
    this._valida = true;
    this._pasoCero = false;
}

private void imprimeMensaje(String title,String msg){
    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
    alert.setTitle(title);
    // final EditText input = new EditText(getContext());
    final boolean[] answer = new boolean[1];
    alert.setMessage(msg);
    //input.setInputType(InputType.TYPE_CLASS_NUMBER);
    //input.setRawInputType(Configuration.KEYBOARD_12KEY);
    //alert.setView(input);
    alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            //Put actions for OK button here
            //Toast.makeText(getContext(), "Accion cancelada",  Toast.LENGTH_SHORT).show();

        }
    });

    alert.show();

}

     private void imprimeEntradadeDatos_medidorLimitado(String title,String msg){
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
         myInterface = new MyInterface();
         myInterface.setListener(this);

         final EditText input = new EditText(getContext());
         alert.setMessage(msg);
         input.setInputType(InputType.TYPE_CLASS_NUMBER);
         input.setRawInputType(Configuration.KEYBOARD_12KEY);
         alert.setView(input);
         alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompletedEntradaDatos_medidorLimitado(true,input.getText().toString());

             }
         });
         alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                // Toast.makeText(getContext(), "Accion cancelada",  Toast.LENGTH_SHORT).show();
                 myInterface.getListener().onDialogCompletedEntradaDatos_medidorLimitado(false,input.getText().toString());

             }
         });

         alert.show();

     }

     public void onDialogCompletedEntradaDatos_medidorLimitado(boolean answer2,String input) {
         // Toast.makeText(getContext(), "validaEntradadato  "+answer2+"  input  "+input,  Toast.LENGTH_SHORT).show();
         //Iguales?
         this.lecturaConfirmada=input;
         if (this.lecturaConfirmada.isEmpty()){
             this.imprimeMensaje("Mensaje","La lectura esta vacia");
         }
         else{
             if (this.lectActual==Integer.parseInt(this.lecturaConfirmada)){
                 this._valida=true;
                 this.solicitaAnomalia();
             }
             else{
                 this.imprimeMensaje("Alerta","Las lecturas no son iguales");
             }

         }




     }


     private void imprimeEntradadeDatos_lecturaEsMenor(String title,String msg){
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
         myInterface = new MyInterface();
         myInterface.setListener(this);

         final EditText input = new EditText(getContext());
         alert.setMessage(msg);
         input.setInputType(InputType.TYPE_CLASS_NUMBER);
         input.setRawInputType(Configuration.KEYBOARD_12KEY);
         alert.setView(input);
         alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompletedEntradaDatos_lecturaEsMenor(true,input.getText().toString());

             }
         });
         alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 Toast.makeText(getContext(), "Accion cancelada",  Toast.LENGTH_SHORT).show();
                 myInterface.getListener().onDialogCompletedEntradaDatos_lecturaEsMenor(false,input.getText().toString());

             }
         });

         alert.show();

     }

     public void onDialogCompletedEntradaDatos_lecturaEsMenor(boolean answer2,String input) {
         // Toast.makeText(getContext(), "validaEntradadato  "+answer2+"  input  "+input,  Toast.LENGTH_SHORT).show();
         //Iguales?

         this.setvalidaEntradadato(answer2);
         this.lecturaConfirmada=input;
/*
         if (this.validaEntradadato){
             this._pasoCero=false;
             this._valida=true;
         }
         else{
             this.solicitaAnomalia();
         }
         */

         this.lecturaConfirmada=input;
         if (this.lecturaConfirmada.isEmpty()){
             this.imprimeMensaje("Mensaje","La lectura esta vacia");
         }
         else{
             if (this.lectActual==Integer.parseInt(this.lecturaConfirmada)){
                 this._valida=true;
                 this.solicitaAnomalia();
             }
             else{
                 this.imprimeMensaje("Alerta","Las lecturas no son iguales");
             }

         }



        }


     private void imprimeEntradadeDatos(String title,String msg){
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
         myInterface = new MyInterface();
         myInterface.setListener(this);
         final EditText input = new EditText(getContext());
         alert.setMessage(msg);
         input.setInputType(InputType.TYPE_CLASS_NUMBER);
         input.setRawInputType(Configuration.KEYBOARD_12KEY);
         alert.setView(input);
         alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompletedEntradaDatos(true,input.getText().toString());

             }
         });
         alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 Toast.makeText(getContext(), "Accion cancelada",  Toast.LENGTH_SHORT).show();
                 myInterface.getListener().onDialogCompletedEntradaDatos(false,input.getText().toString());

             }
         });

         alert.show();

     }

     public void onDialogCompletedEntradaDatos(boolean answer2,String input) {
        // Toast.makeText(getContext(), "validaEntradadato  "+answer2+"  input  "+input,  Toast.LENGTH_SHORT).show();
         //Iguales?
         this.lecturaConfirmada=input;
         if (!input.isEmpty()) {
             if (!this.ConfirmacionLecturaIgual()){
                 this.imprimeMensaje("Alerta","Las lecturas no son Iguales");}
             else{
                 this.solicitaAnomalia();
             }
         }

     }


     private void imprimeMensajeValidarConAnomalia(String title,String msg){
         myInterface = new MyInterface();
         myInterface.setListener(this);
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
          final EditText input = new EditText(getContext());
         final boolean[] answer = new boolean[1];
         alert.setMessage(msg);
         //input.setInputType(InputType.TYPE_CLASS_NUMBER);
         //input.setRawInputType(Configuration.KEYBOARD_12KEY);
         //alert.setView(input);
          alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompleted(true);
             }
         });
         alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompleted(false);
             }
         });
         alert.show();
         }



     private void imprimeMensajeValidarConAnomalia_Esigual(String title,String msg){
         myInterface = new MyInterface();
         myInterface.setListener(this);
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
         final EditText input = new EditText(getContext());
         final boolean[] answer = new boolean[1];
         alert.setMessage(msg);
         //input.setInputType(InputType.TYPE_CLASS_NUMBER);
         //input.setRawInputType(Configuration.KEYBOARD_12KEY);
         //alert.setView(input);
         alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompleted_Esigual(true);
             }
         });
         alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompleted_Esigual(false);
             }
         });
         alert.show();
     }

     public void onDialogCompleted_Esigual(boolean answer2) {
         //  Toast.makeText(getContext(), "onDialogCompleted  " +answer2, Toast.LENGTH_LONG).show();
         if(answer2){
            // this.setvalidaEntradadato(true);
             this.solicitaAnomalia();
         }
         else{
            // this.setvalidaEntradadato(false);
             //Toast.makeText(getContext(),  " Accion cancelada",  Toast.LENGTH_SHORT).show();
             this.cerrarProceso();
         }

     }



       public void onDialogCompleted(boolean answer2) {
       //  Toast.makeText(getContext(), "onDialogCompleted  " +answer2, Toast.LENGTH_LONG).show();
         if(answer2){
             this.setvalidaEntradadato(true);
         }
         else{
             this.setvalidaEntradadato(false);
         }
          if (this.validaEntradadato)
                {
                    //Toast.makeText(getContext(), "validaEntradadato",  Toast.LENGTH_SHORT).show();
                    //Solicitar lectura nuevamente
                    this.imprimeEntradadeDatos ("Alerta","Consumo fuera de los Limites\nPara validarla capture nuevamente la lectura\nConfirmar la Lectura:");
               }
                else{
                   // Toast.makeText(getContext(),  " Accion cancelada",  Toast.LENGTH_SHORT).show();
                    this.cerrarProceso();
                }
     }
     private void imprimeEntradadeDatos_confirmaLectura(String title,String msg){
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
         myInterface = new MyInterface();
         myInterface.setListener(this);
         final EditText input = new EditText(getContext());
         alert.setMessage(msg);
         input.setInputType(InputType.TYPE_CLASS_NUMBER);
         input.setRawInputType(Configuration.KEYBOARD_12KEY);
         alert.setView(input);
         alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompleted_confirmaLectura(true,input.getText().toString());

             }
         });
         alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 Toast.makeText(getContext(), "Accion cancelada",  Toast.LENGTH_SHORT).show();
                 myInterface.getListener().onDialogCompleted_confirmaLectura(false,input.getText().toString());

             }
         });

         alert.show();

     }

     public void onDialogCompleted_confirmaLectura(boolean answer2, String input) {
         this.lecturaConfirmada=input;
         if (!this.lecturaConfirmada.isEmpty()) {
             if (!this.ConfirmacionLecturaIgual()) {
                 this.imprimeMensaje("Alerta", "Las lecturas no son Iguales");
             }
         }
     }

     private void imprimeMensajeLecturaEsmenor(String title,String msg){
         myInterface = new MyInterface();
         myInterface.setListener(this);
         AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
         alert.setTitle(title);
         final EditText input = new EditText(getContext());
         final boolean[] answer = new boolean[1];
         alert.setMessage(msg);
         //input.setInputType(InputType.TYPE_CLASS_NUMBER);
         //input.setRawInputType(Configuration.KEYBOARD_12KEY);
         //alert.setView(input);
         alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompletedLecturaEsmenor(true);
             }
         });
         alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 //Put actions for OK button here
                 myInterface.getListener().onDialogCompletedLecturaEsmenor(false);
             }
         });
         alert.show();
     }


     public void onDialogCompletedLecturaEsmenor(boolean answer2) {
         //  Toast.makeText(getContext(), "onDialogCompleted  " +answer2, Toast.LENGTH_LONG).show();
         if(answer2){
             this._pasoCero=false;
             this._valida=true;
             //this.setvalidaEntradadato(true);
            this.imprimeEntradadeDatos_lecturaEsMenor("Alerta","Confirmar la lectura");
           // this.solicitaAnomalia();
         }
         else{

            // this.solicitaAnomalia();

             this.validaOtravez();

         }

     }

private void validaOtravez(){

    if (this.lecturaEsIgual()){
        this.imprimeMensajeValidarConAnomalia("Mensaje","Lectura igual a la anterior\n¿Validarla con Anomalia?");

    }
    else if (this.ConsumoEsMayorLimite2())
    {
        this.imprimeMensajeValidarConAnomalia("Mensaje", "Consumo fuera de los Limites\n¿Validarlo con Anomalia?");
    }
    else{

        if (this.ConsumoEsMayorLimite1())
        {
            this.imprimeEntradadeDatos_confirmaLectura ("Alerta", "Consumo fuera de los Limites\r\nPara validarla capture nuevamente la lectura\r\nConfirmar la Lectura:");

        }

    }
}


private void forzavalida(){
    this._valida=true;
}
private void solicitaAnomalia(){

    MlectOperation lectura;
    int  respons;
    lectura = new MlectOperation(getContext());
    lectura.open();
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    String formattedDate = df.format(c);

            String nuevoStado="";
    if (this.edocliente.equals("NVO")){
        nuevoStado="CAP";
    }
    else
    {
        nuevoStado="MOD";
    }

    respons = lectura.updatelectura(id,idtlec,odt,lectActual+"",formattedDate,nuevoStado,this.nuevosintentos);
    lectura.close();
    if (respons==1){
        Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
    }
    else{
        Toast.makeText(getContext(), "Error al agregar",  Toast.LENGTH_SHORT).show();
    }


    //Toast.makeText(getContext(), "Menu detalles this.idanm1 "+this.idanm1+" this.idanm2 "+this.idanm2, Toast.LENGTH_SHORT).show();
       if (this.idanm1=="" &&  this.idanm1==null ||  this.idanm2=="" &&  this.idanm2==null){
           //Toast.makeText(getContext(), "Menu detalles ", Toast.LENGTH_SHORT).show();
           Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
           newAct.putExtra("id",id);
           newAct.putExtra("odt",odt);
           newAct.putExtra("contrato",contrato);
           newAct.putExtra("idtlec",idtlec);
           newAct.putExtra("idanm1",idanm1);
           newAct.putExtra("idanm2",idanm2);
           getContext().startActivity(newAct);
       }
       else if( this.idanm1==null &&   this.idanm2==null){
           Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
           newAct.putExtra("id",id);
           newAct.putExtra("odt",odt);
           newAct.putExtra("contrato",contrato);
           newAct.putExtra("idtlec",idtlec);
           newAct.putExtra("idanm1",idanm1);
           newAct.putExtra("idanm2",idanm2);
           getContext().startActivity(newAct);
    }
       else{
this._valida=false;
       }
}

     private boolean lecturaEsMenor()
     {
         if (this.lectActual < this.lectAntetior)
         {
             this._valida = false;
             return true;
         }else{
             return false;
         }

     }

     private boolean lecturaEsIgual()
     {
         if (this.lectActual == this.lectAntetior)
         {
             this._valida = false;
             return true;
         }else{
             return false;
         }

        }

        private boolean ConsumoEsMayorLimite2(){
            int consumo = this.ObtenerConsumo();
            if (consumo == 0)
            { return false;}
            if (consumo >= this.limitebajo2 && consumo <= this.limitealto2)
            { return false;}
            else
            {
                this._valida = false;
                return true;
            }

        }

     private int ObtenerConsumo()
     {
         int consumo;
         if (this._pasoCero)
         {
            // double expR= Math.Pow(10,(double)_lecturaPrevia.numeroRuedas);
             double expR =(Math.pow(10,this.nruedas));
           //  double expR =(Math.pow(10,nruedas)) - 1 - lectAntetior + lectActual;
             consumo = (int)expR - 1 - this.lectAntetior + this.lectActual;
         }
         else
         {
             consumo = this.lectActual - this.lectAntetior;
         }
         return consumo;
     }

     private Boolean ConfirmacionLecturaIgual()
     {
         if (this.lectActual == Integer.parseInt(this.lecturaConfirmada))
         {
             this._valida = true;
             return true;
         }
         return false;
     }

     private Boolean ConsumoEsMayorLimite1(){
         int consumo = ObtenerConsumo();
         if (consumo == 0)
             return false;
         if (consumo >= this.limitebajo && consumo <= this.limitealto)
             return false;
         this._valida = false;
         return true;
     }



     public void init(MyClassListener listener ){

         this.mListener = listener;

        this.res=true;
        this.validalimitado();
         Toast.makeText(getContext(), "Numero de intentos "+this.nuevosintentos, Toast.LENGTH_SHORT).show();
        if (this.intentos>=10 || this.nuevosintentos>=10){
            this.imprimeMensaje("Mensaje","Numero de intentos superados");
        }else
        if (this.limitado){
            if (this.lectActual==this.lectAntetior){
        this._valida=false;
        this.forzavalida();
            }else{
                this.imprimeEntradadeDatos_medidorLimitado("Mensaje","Medidor limitado, Confirma la lectura");

            }
        }
        else{
            if(this.lecturaEsMenor()){

                this.imprimeMensajeLecturaEsmenor("Alerta","La lectura es menor a la anterior\n Si no ha pasado por Cero\n debe agregar una anomalia\n ¿El medidor ha pasado por Cero?");
                }

                else if (this.lecturaEsIgual()){
                this.imprimeMensajeValidarConAnomalia_Esigual("Mensaje","Lectura igual a la anterior\n¿Validarla con Anomalia?");

            }

            else if (this.ConsumoEsMayorLimite2())
            {
                this.imprimeMensajeValidarConAnomalia("Mensaje", "Consumo fuera de los Limites\n¿Validarlo con Anomalia?");
            }
            else{

                if (this.ConsumoEsMayorLimite1())
                {
                    //Solicitar lectura nuevamente
                    this.imprimeEntradadeDatos_confirmaLectura ("Alerta", "Consumo fuera de los Limites\r\nPara validarla capture nuevamente la lectura\r\nConfirmar la Lectura:");

                }
                else{
                    //this.imprimeMensaje("Mensaje","Lectura valida");
                    MlectOperation lectura;
                    int  respons;
                    lectura = new MlectOperation(getContext());
                    lectura.open();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                    String formattedDate = df.format(c);

                    String nuevoStado="";
                    if (this.edocliente.equals("NVO")){
                        nuevoStado="CAP";
                    }
                    else
                    {
                        nuevoStado="MOD";
                    }
                    respons = lectura.updatelectura(id,idtlec,odt,lectActual+"",formattedDate,nuevoStado,this.nuevosintentos);
                    lectura.close();
                    if (respons==1){
                        Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Error al agregar",  Toast.LENGTH_SHORT).show();
                    }


                }

            }



        }
         if (!this._valida){
            // this.imprimeMensaje("Alerta","Lectura no valida");
         }

     }



    public void confirma (){

        int consumo=lectActual-lectAntetior;


        if( consumo < limitebajo) { vtend="BAJO";
            //pedir confirmacion
        }
        if( consumo > limitealto) {
            vtend="ALTO";
        }
        if( consumo == 0 ) {
            vtend="NULO";
        }





/*
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirmar");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        alert.setView(input);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Put actions for OK button here
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Put actions for CANCEL button here, or leave in blank
            }
        });
        alert.show();
*/
        Log.d("nruedas",""+nruedas);
        //Log.d("limitedown2",""+limitedown2);
        //Log.d("limiteup2",""+limiteup2);
        Log.d("fechacorte","--"+fechacorte);



        if (lectActual<lectAntetior){

            Log.d("lectActual","si eentra en lectActual<lectAntetior");
            //------------------
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Confirmacion");
            alertDialog.setMessage("la lectura es menor a la anterior, si no ha pasado por cero, debe agregar una anomalia, ¿El medidor ha pasado por Cero?");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                           // Toast.makeText(getContext(),"Accion ok... "+limitedown,Toast.LENGTH_LONG).show();
/*
                            if (vtend=="ALTO" || vtend=="BAJO")
                            {


                                //---------------------
                                    AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
                                alertDialog2.setTitle("Confirmacion");
                                alertDialog2.setMessage("¿Validarlo con anomalia?");
                                alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Si",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();


                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Confirmar lectura");
                                final EditText input = new EditText(getContext());
                                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                                alert.setView(input);
                                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                       // Toast.makeText(getContext(), "nueva lectura "+input.getText(),  Toast.LENGTH_SHORT).show();

                                        if (Integer.parseInt( input.getText().toString())==lectActual){
                                            MlectOperation lectura;
                                            int  respons;
                                            lectura = new MlectOperation(getContext());
                                            lectura.open();
                                            Date c = Calendar.getInstance().getTime();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                                            String formattedDate = df.format(c);

                                            respons = lectura.updatelectura(id,idtlec,odt,lectActual+"",formattedDate);
                                            lectura.close();



                                            if (respons==1){
                                                Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Error",  Toast.LENGTH_SHORT).show();
                                            }
                                            //Toast.makeText(getContext(), "Menu detalles ", Toast.LENGTH_SHORT).show();
                                            Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
                                            newAct.putExtra("id",id);
                                            newAct.putExtra("odt",odt);
                                            newAct.putExtra("contrato",contrato);
                                            newAct.putExtra("idtlec",idtlec);
                                            newAct.putExtra("idanm1",idanm1);
                                            newAct.putExtra("idanm2",idanm2);
                                            getContext().startActivity(newAct);
                                        }
                                        else{
                                            Toast.makeText(getContext(), "la nueva lectura no coincide",  Toast.LENGTH_SHORT).show();
                                        }





                                    }
                                });
                                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(getContext(), "confirmacion cancelada ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                alert.show();









                                                 }});
                                 alertDialog2.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Toast.makeText(getContext(),"Accion cancelada..." ,Toast.LENGTH_LONG).show();
                                            }
                                        });


                                alertDialog2.show();
                                //---------------------


                            }else{
                                Log.d("lectActual","NO eentra en lectActual<lectAntetior");

                            }
                            */

/*
                            MlectOperation lectura;
                            int  respons;
                            lectura = new MlectOperation(getContext());
                            lectura.open();
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                            String formattedDate = df.format(c);


                            double consumoexpo =(Math.pow(10,nruedas)) - 1 - lectAntetior + lectActual;






                            respons = lectura.updatelectura(id,idtlec,odt,lectActual+"",formattedDate);
                            lectura.close();



                            if (respons==1){
                                Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Error",  Toast.LENGTH_SHORT).show();
                            }
                            */




                        }
                    });
            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            /*
                            //Toast.makeText(getContext(),"Accion cancelada..." ,Toast.LENGTH_LONG).show();
                            //------------------
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle("Confirmacion");
                            alertDialog.setMessage("¿Validarlo con anomalia");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Si",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                  //  Toast.makeText(getContext(),"confirmar anomalia y mostrar actividad de anomalia ",Toast.LENGTH_LONG).show();




        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirmar Lecttura");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);

        alert.setView(input);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(getContext(), "nueva lectura "+input.getText(),  Toast.LENGTH_SHORT).show();

                if (Integer.parseInt( input.getText().toString())==lectActual) {
                    MlectOperation lectura;
                    int respons;
                    lectura = new MlectOperation(getContext());
                    lectura.open();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                    String formattedDate = df.format(c);

                    respons = lectura.updatelectura(id, idtlec, odt, lectActual + "", formattedDate);
                    lectura.close();


                    if (respons == 1) {
                        Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error ", Toast.LENGTH_SHORT).show();
                    }


                    // Toast.makeText(getContext(), "Menu detalles2 ", Toast.LENGTH_SHORT).show();
                    Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
                    newAct.putExtra("id", id);
                    newAct.putExtra("odt", odt);
                    newAct.putExtra("contrato", contrato);
                    newAct.putExtra("idtlec", idtlec);
                    newAct.putExtra("idanm1", idanm1);
                    newAct.putExtra("idanm2", idanm2);
                    getContext().startActivity(newAct);

                }
                else{
                    Toast.makeText(getContext(), "la nueva lectura no coincide",  Toast.LENGTH_SHORT).show();
                }



            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(getContext(),"Confirmacion cancelada...",Toast.LENGTH_LONG).show();
            }
        });
        alert.show();









                                        }
                                    });
                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Toast.makeText(getContext(),"Accion cancelada...",Toast.LENGTH_LONG).show();
                                        }
                                    });

                            alertDialog.show();
                            //---------------------





*/
                          /*  MlectOperation lectura;
                            int  respons;
                            lectura = new MlectOperation(getContext());
                            lectura.open();
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                            String formattedDate = df.format(c);

                            respons = lectura.updatelectura(id,idtlec,odt,lectActual+"",formattedDate);
                            lectura.close();



                            if (respons==1){
                                Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Error",  Toast.LENGTH_SHORT).show();
                            }
                            */
                            //Toast.makeText(getContext(), "Menu detalles ", Toast.LENGTH_SHORT).show();
                            Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
                            newAct.putExtra("id",id);
                            newAct.putExtra("odt",odt);
                            newAct.putExtra("contrato",contrato);
                            newAct.putExtra("idtlec",idtlec);
                            newAct.putExtra("idanm1",idanm1);
                            newAct.putExtra("idanm2",idanm2);
                            getContext().startActivity(newAct);
                        }
                    });

            alertDialog.show();
            //---------------------
        }else  if (lectActual==lectAntetior) {

            //------------------
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Confirmacion");
            alertDialog.setMessage("¿Validarlo con anomalia");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //  Toast.makeText(getContext(),"confirmar anomalia y mostrar actividad de anomalia ",Toast.LENGTH_LONG).show();

                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Confirmar lectura");
                            final EditText input = new EditText(getContext());
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            input.setRawInputType(Configuration.KEYBOARD_12KEY);
                            alert.setView(input);
                            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    //Toast.makeText(getContext(), "nueva lectura "+input.getText(),  Toast.LENGTH_SHORT).show();

                                    if (Integer.parseInt( input.getText().toString())==lectActual) {
                                        MlectOperation lectura;
                                        int respons;
                                        lectura = new MlectOperation(getContext());
                                        lectura.open();
                                        Date c = Calendar.getInstance().getTime();
                                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                                        String formattedDate = df.format(c);
/*
                                        respons = lectura.updatelectura(id, idtlec, odt, lectActual + "", formattedDate);
                                        lectura.close();


                                        if (respons == 1) {
                                            Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                        */
                                        //Toast.makeText(getContext(), "Menu detalles ", Toast.LENGTH_SHORT).show();
                                        Intent newAct = new Intent(getContext(), MenuDetalleActivity.class);
                                        newAct.putExtra("id", id);
                                        newAct.putExtra("odt", odt);
                                        newAct.putExtra("contrato", contrato);
                                        newAct.putExtra("idtlec", idtlec);
                                        newAct.putExtra("idanm1", idanm1);
                                        newAct.putExtra("idanm2", idanm2);
                                        getContext().startActivity(newAct);

                                    }
                                    else{
                                        Toast.makeText(getContext(), "la nueva lectura no coincide",  Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(getContext(), "confirmacion cancelada ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            alert.show();





                        }
                    });
            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(getContext(),"Accion cancelada...",Toast.LENGTH_LONG).show();
                        }
                    });

            alertDialog.show();
            //---------------------



        }else{

            //Log.d("lectActual","else no  entra en lectActuallectAntetior");

            MlectOperation lectura;
            int respons;
            lectura = new MlectOperation(getContext());
            lectura.open();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
            String formattedDate = df.format(c);
/*
            respons = lectura.updatelectura(id, idtlec, odt, lectActual + "", formattedDate);
            lectura.close();


            if (respons == 1) {
                Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error ", Toast.LENGTH_SHORT).show();
            }
            */

        }




/*
                if (consumo<limitedown){
                    //pedir confirmacion
                }
                else if (consumo>limiteup){

                }
                else{

                }
*/







        //Toast.makeText(getContext(), "Guardar "+lect, Toast.LENGTH_SHORT).show();
       // Snackbar.make(view, "Guardar -> "+lectActual, Snackbar.LENGTH_LONG)
          //      .setAction("Action", null).show();
    }



 }
