package com.example.jcruz.lectura11;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcruz.lectura11.Model.DBanomalia;
import com.example.jcruz.lectura11.Model.DBanomaliaoperation;
import com.example.jcruz.lectura11.Model.DBfoto;
import com.example.jcruz.lectura11.Model.DBfotoOperation;
import com.example.jcruz.lectura11.Model.Mlect;
import com.example.jcruz.lectura11.Model.MlectOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuDetalleActivity extends AppCompatActivity {

    private TextView mTextMessage=null;
    private  EditText txtComentario;
 private  LinearLayout contenedor=null;
    private Spinner spinner1=null, spinner2=null,spinnercomentario=null;;
    private String valorcomen=null;
    private Button btnSubmit=null;
    DBanomaliaoperation anomalia=null;
    List<DBanomalia> dataAnomalia=null;
    private  String idanm1=null,idanm2=null,text2=null,text=null,contrato=null; private String mCurrentPhotoPath;
    private  int idtlec=0,odt=0, tomafoto=0;
    private long id=0;
    private Bitmap mImageBitmap;

    private ImageView mImageView;

    private static final int CAMERA_PIC_REQUEST = 1888;
    private ImageView mImage,mImage2 ,mImage3  ,mImage4, mImage5,mImage6 ,mImage7;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Log.d("getItemId","--- > "+item.getItemId());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTextMessage.setText(R.string.title_home);
                    showanomalia();
                    return true;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    showopcioncamara();
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    //addItemsOnSpinner2();
                    //addListenerOnButton();
                    //addListenerOnSpinnerItemSelection();
                    showaddcomentario();
                    return true;

            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detalle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
         contenedor = (LinearLayout) findViewById(R.id.replaced);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
         //idanm1 = intent.getStringExtra("idanm1");
        //idanm2 = intent.getStringExtra("idanm2");
        id = intent.getLongExtra("id",0);
        odt=intent.getIntExtra("odt",0);
        idtlec=intent.getIntExtra("idtlec",0);
        contrato=intent.getStringExtra("contrato");
        /*getIntent().removeExtra("idanm1");
        getIntent().removeExtra("idanm2");
        getIntent().removeExtra("id");
        getIntent().removeExtra("odt");
        getIntent().removeExtra("idtlec");
        */




        //Log.d("idanm1","-> "+ idanm1);
        //Log.d("idanm2","-> "+ idanm2);
        //Log.d("id", "-> "+id);
        //Log.d("odt", "-> "+odt);
       // Log.d("idtlec", "-> "+idtlec);

        //Toast.makeText(getApplicationContext(), "Menu detalles ", Toast.LENGTH_SHORT).show();


        showanomalia();
    }

    @Override
    public void onBackPressed() {
        }

    //agregar anomalia
    public void showanomalia(){
        anomalia = new DBanomaliaoperation(getApplicationContext());
        anomalia.open();
        //  lecturas.delete();
        dataAnomalia = anomalia.getAllAnomalia();
        anomalia.close();
        MlectOperation lecturas2;
        List<Mlect> datalec2;
        lecturas2 = new MlectOperation(getApplicationContext());
        lecturas2.open();
        //  lecturas.delete();
        datalec2 = lecturas2.getAllRutabyODT_lect_id(id,idtlec,odt);
        lecturas2.close();
        for (int i = 0; i < datalec2.size(); i++) {
                     idanm1= datalec2.get(i).getidtanm1();
                    idanm2=datalec2.get(i).getidtanm2();
        }




        //Crea contenedor
        //LinearLayout contenedor = new LinearLayout(getApplicationContext());

        contenedor.removeAllViews();
        //contenedor.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        //contenedor.setOrientation(LinearLayout.VERTICAL);
        //contenedor.setGravity(Gravity.CENTER);
        //Crea ImageView y TextView
        //ImageView miImageView = new ImageView(getApplicationContext());
        TextView miTextView = new TextView(getApplicationContext());
        //Agrega propiedades al TextView.
        miTextView.setText("");
        //miTextView.setBackgroundColor(Color.RED);
        //Agrega imagen al ImageView.
        //miImageView.setImageResource(R.mipmap.ic_launcher);
        //Toast.makeText(getApplicationContext(), "Anomalia idtlec -> "+idtlec, Toast.LENGTH_SHORT).show();
         String[]  anllista = new String[dataAnomalia.size()+1] ;
        anllista[0]="------";
        int posicion1=0, posicion2=0;

        if (idanm1 ==null){idanm1="0";}if (idanm2 ==null){idanm2="0";}

        for (int i = 0; i < dataAnomalia.size(); i++) {
            anllista[i+1]=(dataAnomalia.get(i).getcodigo()+" "+dataAnomalia.get(i).getDescripcion());
            if (Integer.parseInt(idanm1) == Integer.parseInt(dataAnomalia.get(i).getcodigo())){
                posicion1=i+1;
               // Log.d("posicion1","-> "+ posicion1);
            }
            if (Integer.parseInt(idanm2) == Integer.parseInt(dataAnomalia.get(i).getcodigo())){
                posicion2=i+1;
            }
        }
        //Log.d("idanm1","-> "+ idanm1);
        //Log.d("idanm2","-> "+ idanm2);

        //LinearLayout layout=new LinearLayout(this);
        //String[] strings={"1","2","3","Anomalia 4", "Anomalia 5"};
        //String[] strings2={"22 2 Anomalia 1"," 2 22Anomalia 2"," 2 2 Anomalia 3", " 2 2Anomalia 4", " 2 2Anomalia 5"};
         spinner1=new Spinner(this);
         spinner2=new Spinner(this);

         spinner1.setTop(50);
        spinner2.setTop(30);
        spinner2.setBottom(60);
        spinner1.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,anllista));
        spinner2.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,anllista));
        if (idanm1 == null){
            Log.d("idanm1null","-> "+ idanm1);
            spinner1.setSelection(0);
        }else{
            spinner1.setSelection(posicion1);
            Log.d("idanm1NOTnull","-> "+ idanm1);
        }
        if (idanm2 == null){
            spinner2.setSelection(0);
        }else{
            spinner2.setSelection(posicion2);
        }

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String text = spinner1.getSelectedItem().toString();
                if (text.equals("06 SL Impos Disgno")){
                    Toast.makeText(getApplicationContext(), "Esta anomalia requiere comentario", Toast.LENGTH_SHORT).show();
                }
                if (text.equals("21 NL No coincide")){
                    Toast.makeText(getApplicationContext(), "Esta anomalia requiere fotografia", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String text = spinner2.getSelectedItem().toString();
                if (text.equals("06 SL Impos Disgno")){
                    Toast.makeText(getApplicationContext(), "Esta anomalia requiere comentario", Toast.LENGTH_SHORT).show();
                }
                if (text.equals("21 NL No coincide")){
                    Toast.makeText(getApplicationContext(), "Esta anomalia requiere fotografia", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

/*
        Log.d("idanm1","-> "+ idanm1);
        Log.d("idanm2","-> "+ idanm2);
        Log.d("id", "-> "+id);
        Log.d("odt", "-> "+odt);
        Log.d("idtlec", "-> "+idtlec);
        */






        Button buyButton = new Button(this);
        buyButton.setText("Guardar");
        buyButton.setTextColor(Color.WHITE);
        buyButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        buyButton.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));



buyButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //text = spinner1.getSelectedItem().toString();
        //text2 = spinner2.getSelectedItem().toString();


        MlectOperation lectura;
        int  respons,spinnerPosicion1,spinnerPosicion2;
        lectura = new MlectOperation(getApplicationContext());
        lectura.open();
        //  lecturas.delete();

        spinnerPosicion1=spinner1.getSelectedItemPosition();
        spinnerPosicion2=spinner2.getSelectedItemPosition();

        if (spinnerPosicion1==0){
            text=null;
        }
        else{
            text=dataAnomalia.get(spinnerPosicion1-1).getcodigo();

            if (dataAnomalia.get(spinnerPosicion1-1).getcodigo().equals("06")){
               // Toast.makeText(getApplicationContext(), "Requiere comentario", Toast.LENGTH_SHORT).show();
            }

        }
        if (spinnerPosicion2==0){
            text2=null;
        }
        else{
            text2=dataAnomalia.get(spinnerPosicion2-1).getcodigo();
            if (dataAnomalia.get(spinnerPosicion2-1).getcodigo().equals("06")){
               // Toast.makeText(getApplicationContext(), "Requiere comentario", Toast.LENGTH_SHORT).show();
            }
        }



        respons = lectura.updateAnomalia(id,idtlec,odt,text,text2);
        lectura.close();
        if (respons==1){


                showanomalia();

            Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Error Al agregar"+ text+"  "+text2,  Toast.LENGTH_SHORT).show();
        }


    }
});

        contenedor.addView(spinner1);
        contenedor.addView(spinner2);
        contenedor.addView(buyButton);
        /**
         * Comment this line when you use the below mentioned solution.
         */
       // setContentView(layout);



        //Agrega vistas al contenedor.
        contenedor.addView(miTextView);
        //contenedor.addView(miImageView);
        //contenedor.removeAllViews();

        //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(400, 1500, Gravity.CENTER);
        //Agrega contenedor con botones.
       // addContentView(contenedor, params);
        //setContentView(contenedor,params);
    }

    //agregar showopcioncamara
    public void showopcioncamara(){
        contenedor.removeAllViews();
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        //Crea contenedor
       // LinearLayout contenedor = new LinearLayout(getApplicationContext());
       // contenedor.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        //contenedor.setOrientation(LinearLayout.VERTICAL);
       // contenedor.setGravity(Gravity.CENTER);
        //Crea ImageView y TextView
        //ImageView miImageView = new ImageView(getApplicationContext());
        //TextView miTextView = new TextView(getApplicationContext());
        //Agrega propiedades al TextView.
        //miTextView.setText("mi showopcioncamara");
        //miTextView.setBackgroundColor(Color.WHITE);
        //Agrega imagen al ImageView.
        //miImageView.setImageResource(R.mipmap.ic_launcher);
        Button buyButton = new Button(this);
        buyButton.setText("Tomar Foto");
        buyButton.setTextColor(Color.WHITE);
        buyButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
       // buyButton.setBackgroundResource( R.drawable.ic_menu_camera);

        buyButton.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,

                ViewGroup.LayoutParams.WRAP_CONTENT));
        mImage = new ImageView (getApplicationContext());
        mImage2 = new ImageView (getApplicationContext());
        mImage3 = new ImageView (getApplicationContext());
        mImage4 = new ImageView (getApplicationContext());
        mImage5 = new ImageView (getApplicationContext());
        mImage6 = new ImageView (getApplicationContext());
        mImage7 = new ImageView (getApplicationContext());
/*
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mImage2.setImageBitmap(thumbnail);
        */

        DBfotoOperation dfoto;
        List<DBfoto> datafoto;
        //DBfoto newfoto = new DBfoto();
        dfoto = new DBfotoOperation(getApplicationContext());
        dfoto.open();
        datafoto=dfoto.getFotobyodtandidlec(odt,idtlec);
        //dfoto.addFoto(newfoto);
        dfoto.close();
        String cadenabase64 = null;
            //datafoto.size();
        tomafoto=datafoto.size();
        if (tomafoto<5){
/*
        for (int i = 0; i <datafoto.size(); i++) {
            cadenabase64= datafoto.get(i).getbase64();
            byte[] imageAsBytes = Base64.decode(cadenabase64, Base64.DEFAULT);
                     if (i==0){

                mImage2.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
            if (i==1){

                mImage3.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
            if (i==2){

                mImage4.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
            if (i==3){

                mImage5.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
            if (i==4){

                mImage6.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
            if (i==5){

                mImage7.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
        }
        */






            buyButton.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    //Toast.makeText(getApplicationContext(),"Tomar foto function", Toast.LENGTH_SHORT).show();

                    tomarFoto(mImage);
                }

            });

        }
        else
{
    Toast.makeText(this, "Maximo de fotos permitidas", Toast.LENGTH_LONG).show();
}


       // mImage.setBackgroundResource(R.drawable.ic_menu_ruta);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(300, 300, Gravity.LEFT);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(300, 300, Gravity.LEFT);
        //FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(300, 300, Gravity.LEFT);
        //Agrega contenedor con botones.
        //addContentView(contenedor, params);
            params.topMargin=20;
        params.rightMargin=20;
        params.leftMargin=20;
        params.bottomMargin=50;

        layout.addView(mImage,params);
        layout.addView(mImage2,params2);
        layout.addView(mImage3,params2);
        layout.addView(mImage4,params2);
        layout.addView(mImage5,params2);
        layout.addView(mImage6,params2);
        layout.addView(mImage7,params2);

        //contenedor.addView(mImage,params);
        //contenedor.addView(mImage2,params2);
        //contenedor.addView(mImage3,params);
        contenedor.addView(layout);
        //Agrega vistas al contenedor.
        contenedor.addView(buyButton);
        //contenedor.addView(miImageView);

        //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(400, 1500, Gravity.CENTER);
        //Agrega contenedor con botones.
        //addContentView(contenedor, params);
    }

    //agregar anomalia
    public void showaddcomentario(){
        contenedor.removeAllViews();

        final String[]  anllistacoment = new String[11] ;
        anllistacoment[0]="";
        anllistacoment[1]="Foto (FT)";
        anllistacoment[2]="Toma Directa (T.D.)";
        anllistacoment[3]="Medidor Dentro (M.D)";
        anllistacoment[4]="Perros (CAN)";
        anllistacoment[5]="Válvula cerrada (V.C.)";
        anllistacoment[6]="Sin válvula (S.V.)";
        anllistacoment[7]="Limitado con servicio  (L.S.)";
        anllistacoment[8]="Sin llave paso (S.LL.)";
        anllistacoment[9]="Fuga (FG)";
        anllistacoment[10]="Mica opaca (M.O.)";




        MlectOperation vlectura;
        List<Mlect> datacomen;
        vlectura = new MlectOperation(getApplicationContext());
        vlectura.open();
        //Date c = Calendar.getInstance().getTime();

        //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        //String formattedDate = df.format(c);

        datacomen = vlectura.getcomentario(id,idtlec,odt);
        vlectura.close();

        for (int i=0; i < datacomen.size();i++){
            valorcomen=datacomen.get(i).getcomentario();
        }



        spinnercomentario=new Spinner(this);
        spinnercomentario.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,anllistacoment));

        spinnercomentario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2==0){
                    txtComentario.setText(valorcomen);
                }else {
                    txtComentario.setText(anllistacoment[arg2]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                txtComentario.setText(valorcomen);

            }
        });




        //Crea contenedor
       // LinearLayout contenedor = new LinearLayout(getApplicationContext());
        //contenedor.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
      //  contenedor.setOrientation(LinearLayout.VERTICAL);
        //contenedor.setGravity(Gravity.CENTER);
        //Crea ImageView y TextView
        //ImageView miImageView = new ImageView(getApplicationContext());
        TextView miTextView = new TextView(getApplicationContext());
         txtComentario= new EditText(getApplicationContext());
        txtComentario.setText(valorcomen);
        txtComentario.setTextColor(Color.BLACK);
        //txtComentario.setScaleY(20);
        txtComentario.setMaxHeight(5);
        txtComentario.setMaxLines(5);
        txtComentario.setTop(10);
        txtComentario.setBottom(15);
        InputFilter[] editFilters = txtComentario.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.LengthFilter(100); //the desired length
        txtComentario.setFilters(newFilters);

        //txtComentario.setMaxEms(20);
              Button comentButton = new Button(this);
        comentButton.setText("Guardar");
        comentButton.setTextColor(Color.WHITE);
        comentButton.setTop(25);
        comentButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        comentButton.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));



        comentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text=txtComentario.getText();

                 MlectOperation lectura;
                int  respons;
                lectura = new MlectOperation(getApplicationContext());
                lectura.open();
                //Date c = Calendar.getInstance().getTime();

                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                //String formattedDate = df.format(c);

                respons = lectura.updatelectura_comentario(id,idtlec,odt,text.toString());
                lectura.close();

                if (respons==1){
                    Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error text",  Toast.LENGTH_SHORT).show();
                }




            }
        });

        //Agrega propiedades al TextView.
        miTextView.setText("Agregar Comentario");
        miTextView.setTextColor(Color.BLACK);
        miTextView.setTextSize(30);
        miTextView.setPadding(10,5,5,5);
        miTextView.setTop(60);
        miTextView.setLeft(30);
        miTextView.setBottom(30);
        //miTextView.setBackgroundColor(Color.BLUE);
        //Agrega imagen al ImageView.
        //miImageView.setImageResource(R.mipmap.ic_launcher);
        contenedor.addView(spinnercomentario);
        contenedor.addView(miTextView);
        contenedor.addView(txtComentario);
        //Agrega vistas al contenedor.

        contenedor.addView(comentButton);
        //contenedor.addView(miImageView);

       // FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(400, 1500, Gravity.CENTER);
        //Agrega contenedor con botones.
     //   addContentView(contenedor, params);
    }



@RequiresApi(api = Build.VERSION_CODES.M)
public void tomarFoto(ImageView a){
    mImage =a;  //(ImageView) findViewById(R.id.camera_image);
    //1

   //if (checkSelfPermission(Manifest.permission.CAMERA)
    if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED


            ) {
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                CAMERA_PIC_REQUEST);
    } else {
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivityForResult(intent, CAMERA_PIC_REQUEST);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("createImageFile", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        }




    }


}
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());

        //String imageFileName = "prueba_JPEG_" + timeStamp + "_";
        String imageFileName="odt_"+odt+"_contrato_"+contrato+"_fecha_"+timeStamp;

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(image);
            scanIntent.setData(contentUri);
            sendBroadcast(scanIntent);
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + image.getPath())));
        }


        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_PIC_REQUEST && resultCode!=0) {
            //2
           // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
             // Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("error mImageBitmap", "mImageBitmap");
            }
            mImage.setImageBitmap(mImageBitmap);

                  //mImage.setImageBitmap(thumbnail);

              // Log.d("getExtras",""+data.getExtras().get("data"));
                //3
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
            byte[] b = bytes.toByteArray();
            String imgString = Base64.encodeToString(b, Base64.DEFAULT);
            Date c = Calendar.getInstance().getTime();
                       SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
            String formattedDate = df.format(c);


              String appDirectoryName = "lectura/"+"odt_"+odt;
              File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), appDirectoryName);
            imageRoot.mkdirs();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                  Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                  Uri contentUri = Uri.fromFile(imageRoot);
                scanIntent.setData(contentUri);
                sendBroadcast(scanIntent);
            } else {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + imageRoot.getPath())));
            }


            String rutaarchivo=Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES)+appDirectoryName;

             //String namefoto="contrato-"+contrato+"_odt-"+odt+"_"+"idtlec-"+ idtlec+"_fecha-"+formattedDate+".PNG";
            String namefoto="contrato_"+contrato+"_fecha_"+formattedDate+".jpg";

            DBfotoOperation dfoto;
            DBfoto newfoto = new DBfoto();
            dfoto = new DBfotoOperation(getApplicationContext());
            dfoto.open();
            newfoto.SetdataFoto(odt,idtlec,contrato,namefoto,formattedDate,imgString);
            dfoto.addFoto(newfoto);
            dfoto.close();
            tomafoto=tomafoto+1;


           Log.d("rutaarchivo",rutaarchivo+"/"+namefoto);
            //Log.d("rutaarchivo2",imageRoot+namefoto);
            //Toast.makeText(this, "image bytes " + imgString, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "foto " + namefoto, Toast.LENGTH_LONG).show();
                //4
               // File file = new File(dir+namefoto);
             File file = new File(imageRoot+"/"+namefoto);
            try {
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);

                    //5
                    fo.write(bytes.toByteArray());
                    fo.close();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(file);
                    scanIntent.setData(contentUri);
                    sendBroadcast(scanIntent);
                } else {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://" + file.getPath())));
                }





                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                Log.d("img", "Error  file: " + e.getMessage());
                }


        }else{
            //Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "camera accion cancelado", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            } else {
                Toast.makeText(this, "Permiso negado", Toast.LENGTH_LONG).show();
            }

        }


    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (idanm1==null && idanm2==null || idanm1=="0" && idanm2=="0"){
                    Toast.makeText(this, "Permiso negado para salir", Toast.LENGTH_LONG).show();

            }
                else{
                    finish();
                      onBackPressed();
                }


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
 class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
