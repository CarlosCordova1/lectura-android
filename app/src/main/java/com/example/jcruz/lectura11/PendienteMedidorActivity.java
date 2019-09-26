package com.example.jcruz.lectura11;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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

import com.example.jcruz.lectura11.Model.DBfotoOperation;
import com.example.jcruz.lectura11.Model.Mlect;
import com.example.jcruz.lectura11.Model.MlectOperation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PendienteMedidorActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager, mViewPager2;
    private int  odtvalue,idportal;
    private MlectOperation lecturas,Searchlecturas;
    private List<Mlect> datalec,Searchdatalec;
    private int[] odtlista;
    private Context mycontexto;
    private Menu menu;
    private SearchView searchView;
    //private  String doBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
         odtvalue = intent.getIntExtra("odt",0);

        idportal=intent.getIntExtra("idportal",0);
       // doBusqueda = intent.getStringExtra("doBusqueda");
       // Toast.makeText(getBaseContext(), "odt -> "+odtvalue+  "doBusqueda  -> "+doBusqueda,  Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_medidor);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_buscar);

        setSupportActionBar(toolbar);

        mycontexto=getApplicationContext();
        lecturas = new MlectOperation(getApplicationContext());
        lecturas.open();
        //  lecturas.delete();
        datalec = lecturas.getAllRutaPendiente(odtvalue, idportal);
        lecturas.close();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.settotal(datalec.size(),datalec,mycontexto,odtvalue,"all","",idportal);
        mSectionsPagerAdapter.notifyDataSetChanged();
                      // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
       // mViewPager2 = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("sadsdsda");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
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
                finish();
                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";
        private EditText lectura;
        private long id;
        private int idtlec,odt, secuencia, consumoprom, lecturaant, limitedown,  limiteup ,limitedown2,limiteup2,nruedas, intentos,  nuevosintentos, dlectura;

        private String contrato,fechacorte ,idedocliente,edo;
        private String idanm1;
        private String idanm2;

        private ValidaLectura l;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given seclared this activity in your AndroidManifest.xml?ection
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,  List<Mlect> datalec_2_2, int total,
                                                      Context Vmycont, int odtnum , String bandera,String Busqueda, int idportal) {
            PlaceholderFragment fragment = new PlaceholderFragment();

             MlectOperation lecturas2;
             List<Mlect> datalec2;

            DBfotoOperation fotos ;
            Cursor dataFoto;
            lecturas2 = new MlectOperation(Vmycont);
            lecturas2.open();
            //Log.d("bandera",bandera);
            //Log.d("Busqueda",Busqueda);

                 datalec2 = lecturas2.getAllRutaPendiente(odtnum, idportal);

           // Log.d("datalec2.size()",""+datalec2.size());
           // Log.d("sectionNumber",""+sectionNumber);
            ////  lecturas.delete();

            lecturas2.close();

            Bundle args=null;
            args = new Bundle();


            for (int i = 0; i < datalec2.size(); i++) {
                if (i==sectionNumber-1){
                    setTestdata();
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

                    //args.putString("ruta",  "Ruta: "+datalec2.get(i).getruta());


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

            String cliente =getArguments().getString("cliente");

             edo =getArguments().getString("edo");
            String edocliente  =getArguments().getString("edocliente");

            contrato =getArguments().getString("contrato");



            String Domicilio =getArguments().getString("Domicilio");
            String ruta =getArguments().getString("ruta");
            String giro =getArguments().getString("giro");
            String medidor =getArguments().getString("medidor");

             idanm1 =getArguments().getString("idanm1");
             idanm2 =getArguments().getString("idanm2");

             secuencia =getArguments().getInt("secuencia");
            int indice =getArguments().getInt("indice");
            int total =getArguments().getInt("total");
             dlectura =getArguments().getInt("lectura");

             id=getArguments().getLong("id");
              idtlec=getArguments().getInt("idtlec");
              odt=getArguments().getInt("odt");


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

            //if (!edo.equals("CAP")){
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
                               // l = new ValidaLectura(lectActual,lecturaant,limitedown,limiteup ,id, odt, idtlec,contrato,idanm1,idanm2, getContext(),limitedown2,limiteup2,nruedas,fechacorte);
                              //  l.confirma();

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
                                //input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                if (dlectura!=0){

                                    lectura.setText(""+ dlectura);
                                }
                                else{
                                    lectura.setText("");
                                }
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
        private int idportal;
        private List<Mlect> datalec_init;
        private Context Vmycont;
         private String band;
        private String busqueda;

        public void  settotal(int vtotal, List<Mlect> dataodt,Context mycont, int odtval ,String bandera ,String vbusqueda , int idportal){
            this.total=vtotal;
            this.datalec_init=dataodt;
            this.Vmycont=mycont;
            this.ODTvalor=odtval;
            this.band=bandera;
            this.busqueda=vbusqueda;
            this.idportal=idportal;
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,datalec_init, total, Vmycont,ODTvalor,band,busqueda, idportal);
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
