package com.example.jcruz.lectura11;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcruz.lectura11.Model.DBusuario;
import com.example.jcruz.lectura11.Model.DBusuarioOperation;
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
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String CodeEMIE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);


        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    showProgress(true);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            attemptLogin();

                        }
                    }, 1500);

                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                attemptLogin();

                            }
                        }, 1500);


            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        TelephonyManager mngr = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        CodeEMIE = mngr.getDeviceId();
       // Toast.makeText(this, "ver en el telefono marcando *#06#    ->  EMIE " + CodeEMIE,
         //       Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //  menu.add(Menu.NONE, 0, Menu.NONE, "left")
        //        .setActionView(R.layout.activity_main)
        //      .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
             case R.id.sub_actualizar:
                Intent intent = new Intent(getApplicationContext(), ActualizarActivity.class);
               // intent.putExtra("json", success);
                startActivity(intent);
                return true;
                /*
            case R.id.sub_configuracion:
               Intent intent2 = new Intent(getApplicationContext(), AyudaActivity.class);
               //  intent2.putExtra("json", success);
               startActivity(intent2);
                return true;
                */
            case R.id.sub_salir:
               finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = false;
        }

        // Check for a valid email address.
        /* if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;

            cancel = true;
        } else
            */
            if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            //mAuthTask = new UserLoginTask(email, password,CodeEMIE);
           //mAuthTask.execute((Void) null);


            buscarUsuario(MD5(email),MD5(password));
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            // intent.putExtra("json", success);
            //startActivity(intent);
            //finish();

           // showProgress(false);
        }
    }

    public void buscarUsuario(String usuario,String paswd){
        showProgress(false);
        //DBusuario newusuario = new DBusuario();

       // Log.d("usuario",usuario);
       // Log.d("contrasena",paswd);

        DBusuarioOperation usuarioData = new DBusuarioOperation(this);
        usuarioData.open();
         List<DBusuario> datausuario;
       // usuarioData.TRUNCATE();
        datausuario=usuarioData.getusuarioregistrado(usuario,paswd);
        usuarioData.close();



        if (datausuario.size()>0){

          // Toast.makeText(this, "usuario valido: " + datausuario.get(0).getnombre(),
            //      Toast.LENGTH_LONG).show();
           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
             intent.putExtra("oratkn", datausuario.get(0).gettoken());
            intent.putExtra("name", datausuario.get(0).getnombre());
            intent.putExtra("idportal", datausuario.get(0).getidportal());
             startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "usuario no valido",
                    Toast.LENGTH_LONG).show();

        }




    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
       // return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                                                                     .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        /*List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
        */
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Object, Object, String> {

        private final String mEmail;
        private final String mPassword;
        private final String mcode;

        UserLoginTask(String email, String password,String code) {
            mEmail = email;
            mPassword = password;
            mcode = code;
        }

        @Override
        protected String doInBackground(Object... params) {
            // TODO: attempt authentication against a network service.
            String Cadena ="";
            String data = null;
            //try {
            //data = "action=" + URLEncoder.encode("action=login&login=valor&pass=1254","UTF-8");
            data = "action=login&login="+mEmail+"&pass="+mPassword+"&code="+mcode+"&auth="+" ";
            // data = "action=" + URLEncoder.encode("action=login&login=valor&pass=1254","UTF-8");
            //} catch (UnsupportedEncodingException e) {
            //  e.printStackTrace();
            //}

            try {

                // Simulate network access.
                Thread.sleep(1);
                /**/
                HttpURLConnection connection=null;
                BufferedReader reader=null;

                try {
                    //demo="22f0258b6685684c113bad94d91b8fa02a"
                    // String passCifrado=md5(mPassword);
                    String passCifrado=mPassword;
                    // Log.d("password",mPassword);
                    // String passCifrado="22f0258b6685684c113bad94d91b8fa02a";
                    //
                    //Cadena =mPassword;
                   // URL url= new URL("http://192.168.223.100/git/api/apiagk.php?urlget=lgn/1.0/lgn/");
                    URL url= new URL("https://www.aguakan.com/git/api/webt.php?urlget=lct/1.0/mobile");
                    //URL url=new URL("http://192.168.223.100/git/RNX/controlador/CnxOra.php?con=lps&sql=cnvUp&app=censo&prm={%22tb%22:%22LuE%22,%22u%22:%22"+mEmail+"%22,%22p%22:%"+passCifrado+"%22}");
                    //URL url=new URL("http://192.168.223.100/git/RNX/controlador/CnxOra.php?con=lps&sql=cen&prm=1&app=censo");
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

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return Cadena;// pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.

            return Cadena;
        }

        @Override
        protected void onPostExecute(final String success) {
            mAuthTask = null;
            showProgress(false);
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

                int status = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("status").getAsInt();
                if(status==1){
                    int  cns;
                    try {
                        //cns = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("servicios").getAsJsonObject().get("cns").getAsJsonObject().get("operativo").getAsInt();

                       cns=1;
                        // Log.d("cns", cns + "");
                        if(cns==1){
                            //Log.d("acceso permitido", cns + "");
                            Toast.makeText(getApplicationContext(),"Acceso permitido",Toast.LENGTH_LONG).show();
                            //intent.putExtra("json", success);
                            //int iduser = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("iduser").getAsInt();
                            //String oratkn = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("oratkn").getAsString();
                            //intent.putExtra("iduser", iduser);
                            //intent.putExtra("oratkn", oratkn);

                            //showProgress(true);
                            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //intent.putExtra("json", success);
                           //startActivity(intent);
                           // finish();
                        }
                        else{
                            //Log.d("acceso negado", cns + "");
                            Toast.makeText(getApplicationContext(),"Acceso negado por el administrador de la aplicacion",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e){
                        //Log.d("cns", "Servicio no disponible para este usuario");
                        Toast.makeText(getApplicationContext(),"Servicio no disponible para este usuario",Toast.LENGTH_LONG).show();
                    }

                }else{
                    //Log.d("valida", "Usuario no valido");
                    Toast.makeText(getApplicationContext(),"Usuario o contraña no valido",Toast.LENGTH_LONG).show();
                }
                // Log.d("mail", mail);
                // JsonElement  cns = servicio.getAsJsonArray().get(0).getAsJsonObject().get("cns");
                //Log.d("cns", cns.toString());

                // finish();
            } else {
                //mPasswordView.setError(success);

                mEmailView.setError("No se encontro usuario");
                // mPasswordView.setError(getString(R.string.error_incorrect_password));
                mEmailView.requestFocus();
                //mPasswordView.requestFocus();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

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
}

