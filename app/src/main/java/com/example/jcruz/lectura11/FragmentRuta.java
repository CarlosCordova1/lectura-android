package com.example.jcruz.lectura11;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.jcruz.lectura11.Model.Mlect;
import com.example.jcruz.lectura11.Model.MlectOperation;
import com.google.android.gms.plus.PlusOneButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link FragmentRuta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRuta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRuta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlusOneButton mPlusOneButton;
      private OnFragmentInteractionListener mListener;



    private String[] brandsNames = new String[] {
            "ODT 1"
          };
/*
    private int[] brandsImages = new int[] { R.drawable.ic_help_black_24dp_1x,
            R.drawable.ic_help_black_24dp_1x, R.drawable.ic_autorenew_black_24dp_1x,
            R.drawable.ic_exit_to_app_black_24dp_1x, R.drawable.ic_directions_run_black,
            R.drawable.ic_help_black_24dp_1x, R.drawable.ic_autorenew_black_24dp_1x,
            R.drawable.ic_exit_to_app_black_24dp_1x, R.drawable.ic_directions_run_black,
            R.drawable.ic_help_black_24dp_1x,R.drawable.ic_help_black_24dp_1x,
            R.drawable.ic_help_black_24dp_1x, R.drawable.ic_autorenew_black_24dp_1x,
            R.drawable.ic_exit_to_app_black_24dp_1x, R.drawable.ic_directions_run_black,
            R.drawable.ic_help_black_24dp_1x, R.drawable.ic_autorenew_black_24dp_1x,
            R.drawable.ic_exit_to_app_black_24dp_1x, R.drawable.ic_directions_run_black,
            R.drawable.ic_help_black_24dp_1x  };
*/
    private int[] brandsImages = new int[] {
            R.drawable.icons8_road_30 };
    private int[] odtlista;
    private  int  idportal;
    private  String  oratoken;
    private MlectOperation lecturas;
    private Cursor datalec;



    public FragmentRuta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRuta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRuta newInstance(String param1, String param2) {
        FragmentRuta fragment = new FragmentRuta();
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

        final MainActivity myActivity = (MainActivity) getActivity();
        idportal=myActivity.getidportal();

        oratoken=myActivity.gettoken();


           //Toast.makeText(getActivity(), "idportal  " + idportal,
             // Toast.LENGTH_LONG).show();


        Mlect newregistro = new Mlect();
        lecturas = new MlectOperation(getContext());
        lecturas.open();
      //  lecturas.delete();
        datalec = lecturas.getAllRuta(idportal);




        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
/*
        for (int i = 0; i < brandsNames.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("brand_names", brandsNames[i]);
            map.put("brand_images", Integer.toString(brandsImages[0]));
            aList.add(map);
        }
*/
        odtlista = new int[datalec.getCount()] ;

       // "select d.idtodt as odt, d.idtlec,d.ruta, count(*) as total,\n" +
        //        "(select count(*) as t from lectura as v where v.idtodt=d.idtodt  and v.lectura is not null ) as capturado\n" +
          //      " from lectura as d group by d.idtodt"

        int i=0;
        if(datalec.getCount() > 0){
           while(datalec.moveToNext()){
               // Mlect employee = new Mlect();
               HashMap<String, String> map = new HashMap<String, String>();
                int odt= datalec.getInt(datalec.getColumnIndex("odt"));
               int total= datalec.getInt(datalec.getColumnIndex("total"));
                String ruta= datalec.getString(datalec.getColumnIndex("ruta"));
               int capturado= datalec.getInt(datalec.getColumnIndex("capturado"));

               //map.put("brand_names","  ODT " + datalec.get(i).getidtodt()+"  id  "+datalec.get(i).getid());
               double     resultcapturado=((double)capturado/(double)total)*100;
               DecimalFormat Vresultcapturado = new DecimalFormat("#0.00");

               map.put("brand_names","" + ruta+"  ("+capturado+"/"+total+") "+Vresultcapturado.format(resultcapturado)+"%");
               map.put("brand_images", Integer.toString(brandsImages[0]));
               odtlista[i]=(odt);
               aList.add(map);
               i++;

            }
        }


        lecturas.close();




// Keys used in Hashmap
        String[] from = { "brand_images", "brand_names"};

        // Ids of views in listview_layout
        int[] to = { R.id.brand_image, R.id.brand_name};




        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_ruta, container, false);
        //TextView textView = (TextView) view.findViewById(R.id.textView2);
        //textView.setText("nuevo texto");
        // A. Creamos el arreglo de Strings para llenar la lista
       /* String[] cosasPorHacer = new String[] { "Ruta 1",
                "Ruta 2","Ruta 3","Ruta 4","Ruta 5","Ruta 6","Ruta 1",
                "Ruta 2","Ruta 3","Ruta 4","Ruta 5","Ruta 6","Ruta 7","Ruta 8"};
        */

       /* // B. Creamos un nuevo ArrayAdapter con nuestra lista de cosasPorHacer
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, cosasPorHacer);
*/
        // C. Seleccionamos la lista de nuestro layout
        ListView miLista = (ListView) view.findViewById(R.id.milista);

        SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity()
                .getBaseContext(), aList, R.layout.mylist, from, to);

        // D. Asignamos el adaptador a nuestra lista

        miLista.setAdapter(arrayAdapter);
       miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getActivity(), "Hiciste click en el número " + position+" odt -> " + odtlista[position],
                //        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MedidorActivity.class);
                intent.putExtra("odt", odtlista[position]);
                intent.putExtra("idportal", idportal);
                intent.putExtra("oratoken", oratoken);
                startActivity(intent);
                getActivity().finish();




            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
     //   mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
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

}
