package com.example.jcruz.lectura11;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentODT.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentODT#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentODT extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        R.drawable.ic_help_black_24dp_1x  };

    public FragmentODT() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentODT.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentODT newInstance(String param1, String param2) {
        FragmentODT fragment = new FragmentODT();
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

      //  lecturas = new MlectOperation(getContext());
    //    lecturas.open();
  //      datalec = lecturas.getAllLectura();
//        lecturas.close();



        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < brandsNames.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("brand_names", brandsNames[i]);
            map.put("brand_images", Integer.toString(brandsImages[0]));
            aList.add(map);
        }

/*
        for (int i = 0; i < datalec.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("brand_names","  getidtclt " + datalec.get(i).getidtclt()+"  getidtclt  "+datalec.get(i).getidtclt());
            map.put("brand_images", Integer.toString(brandsImages[0]));
            aList.add(map);
        }
*/

// Keys used in Hashmap
        String[] from = { "brand_images", "brand_names"};

        // Ids of views in listview_layout
        int[] to = { R.id.brand_image,
                R.id.brand_name};




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
                /*Toast.makeText(getActivity(), "Hiciste click en el número " + position,
                        Toast.LENGTH_LONG).show();
                */
               Intent intent = new Intent(getActivity(), MedidorActivity.class);
               //intent.putExtra("json", success);
              startActivity(intent);


            }
        });

        return view;
        // Inflate the layout for this fragment
        /// return inflater.inflate(R.layout.fragment_fragment_odt, container, false);
        //
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
