package com.example.jcruz.lectura11;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jcruz.lectura11.APIaguakan.APIactualizar;


import static java.lang.Thread.sleep;

public class ActualizarActivity extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    private APIactualizar getApi = null;
    private String CodeEMIE, device_unique_id;
    private AlertDialog alertDialog;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        loadIMEI();

        FloatingActionButton ButtonActualizar = (FloatingActionButton) findViewById(R.id.floatingActionButtonActualizar);
        ButtonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                //Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_LONG).show();

                if (isNetDisponible()) {
                    if (isOnlineNet()) {
                        actualizardata();
                    } else {
                        showProgress(false);
                        Toast.makeText(getApplicationContext(), "isOnlineNet no disponible", Toast.LENGTH_LONG).show();
                    }
                } else {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), "isNetDisponible no disponible", Toast.LENGTH_LONG).show();
                }


            }

        });


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
        // CodeEMIE = mngr.getDeviceId();
        //Toast.makeText(this, "ver en el telefono marcando *#06#    ->  EMIE " + CodeEMIE,
        //      Toast.LENGTH_LONG).show();
        //  Log.d("imei",""+CodeEMIE);


    }

    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
//                get_imei_data();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        } else {

            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            CodeEMIE = mngr.getDeviceId();
            device_unique_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            //  textView.setText(device_unique_id+"----"+mngr.getDeviceId());
            // READ_PHONE_STATE permission is already been granted.
            //Toast.makeText(this, "Alredy granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                Toast.makeText(this,"Alredy DONE",Toast.LENGTH_SHORT).show();
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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
                device_unique_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
              //  textView.setText(device_unique_id+"----"+mngr.getDeviceId());

            } else {
                Toast.makeText(this,"Permiso negado",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void actualizardata(){

         alertDialog = new AlertDialog.Builder(this).create();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                getApi = new APIactualizar("actualizar", "", CodeEMIE, getBaseContext(), new APIactualizar.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                       // Toast.makeText(getApplicationContext(),output,Toast.LENGTH_LONG).show();


                alertDialog.setTitle("");
                alertDialog.setMessage(output);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                    }
                });
               // getApi.delegate=this;
                getApi.MyCustomTask(getApplicationContext());
                getApi.execute((Void) null);
                showProgress(false);
                //Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
            }
        }, 3000);

    }

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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                   getSystemService(Context.CONNECTIVITY_SERVICE);
              //  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

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
  /*  @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        Toast.makeText(getApplicationContext(),"okprocessFinish "+output,Toast.LENGTH_LONG).show();
    }
*/


}
