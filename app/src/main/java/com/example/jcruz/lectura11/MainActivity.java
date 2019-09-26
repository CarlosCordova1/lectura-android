package com.example.jcruz.lectura11;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcruz.lectura11.Model.DBusuario;
import com.example.jcruz.lectura11.Model.DBusuarioOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentRuta.OnFragmentInteractionListener,
        FragmentODT.OnFragmentInteractionListener,
        FragmentDescargarDeX7.OnFragmentInteractionListener,
        FragmentTox7.OnFragmentInteractionListener,
               FragmentAlerta.OnFragmentInteractionListener {
    private  String display_name,mail,oratkn; int idportal=0;


    public String gettoken() {
        return oratkn;
    }
    public int getidportal() {
        return idportal;
    }

    public void setMytoken(String oratkn) {
        this.oratkn = oratkn;
    }
    public void setIdportal(int  portal) {
        this.idportal = portal;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       Intent intent = getIntent();
      String JsonResponse = intent.getStringExtra("json");
        //display_name = intent.getStringExtra("name");
        // oratkn = intent.getStringExtra("oratkn");
         idportal=intent.getIntExtra("idportal",0);

        DBusuarioOperation usuarioData = new DBusuarioOperation(this);
        usuarioData.open();
        List<DBusuario> datausuario;
        // usuarioData.TRUNCATE();
        datausuario=usuarioData.getusuarioregistradoByidportal(idportal);

        if (datausuario.size()>0){
            display_name = datausuario.get(0).getnombre();
            oratkn=datausuario.get(0).gettoken();
        }


        usuarioData.close();

                 mail =display_name;

   /*      JsonParser parser = new JsonParser();
        JsonElement arrayElement = parser.parse(JsonResponse);
         display_name = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("display_name").getAsString();
         mail = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("mail").getAsString();

        oratkn = arrayElement.getAsJsonArray().get(0).getAsJsonObject().get("oratkn").getAsString();
*/



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            //fragmentClass = FragmentODT.class;
            fragmentClass = FragmentRuta.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView ViewDisplay_name = (TextView)header.findViewById(R.id.nombreshow);
        ViewDisplay_name.setText(display_name);

        //TextView ViewDisplay_mail = (TextView)header.findViewById(R.id.textView);
        //ViewDisplay_mail.setText(mail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        String title="Ruta";
        //Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();
        /*if (id == R.id.nav_odt) {
            title="Orden de Trabajo";
            //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            //drawer.closeDrawer(GravityCompat.START);
            fragmentClass = FragmentODT.class;
            Toast.makeText(this, "Iniciada", Toast.LENGTH_SHORT).show();

        } else
            */
        if (id == R.id.nav_ruta) {
            title="Iniciar";
            //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            //drawer.closeDrawer(GravityCompat.START);
            fragmentClass = FragmentRuta.class;
           // Toast.makeText(this, "Iniciada", Toast.LENGTH_SHORT).show();

        } /*else
        if (id == R.id.nav_camera) {
                  title="Recibida";
                //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                  //drawer.closeDrawer(GravityCompat.START);
                  fragmentClass = FragmentRuta.class;
            Toast.makeText(this, "Recibida", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_slideshow) {
                  title="Enviada";
                  fragmentClass = FragmentRuta.class;
                  Toast.makeText(this, "Enviada", Toast.LENGTH_SHORT).show();
              }*/ else if (id == R.id.nav_gallery) {
                  //Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show();
                  //fragmentClass = FragmentDescargarDeX7.class;

                  Intent newAct = new Intent(this, LoginActivity.class);
                    finish();
                  startActivity(newAct);

        } /*else if (id == R.id.nav_manage) {
                  fragmentClass = FragmentRuta.class;
        }*/
        else if (id == R.id.nav_share) {
                  title="Descargar Ruta";
                  fragmentClass = FragmentDescargarDeX7.class;
        } else if (id == R.id.nav_send) {
                  title="Enviar Ruta";
                  fragmentClass = FragmentTox7.class;
        }
  /*      else{
                  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                  drawer.closeDrawer(GravityCompat.START);
              }
*/
     /*   if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        }*/

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }catch (Exception e) {
            e.printStackTrace();
        }


        getSupportActionBar().setTitle(title);
        // change icon to arrow drawable
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
