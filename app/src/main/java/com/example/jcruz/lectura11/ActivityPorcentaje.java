package com.example.jcruz.lectura11;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.jcruz.lectura11.Model.Mlect;
import com.example.jcruz.lectura11.Model.MlectOperation;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityPorcentaje extends AppCompatActivity {
    private PieChart pieChart;
    private int odt,idportal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
          odt = intent.getIntExtra("odt",0);
        idportal=intent.getIntExtra("idportal",0);


        MlectOperation lecturas2;
        Cursor datalec2;

        lecturas2 = new MlectOperation(getApplicationContext());
        lecturas2.open();
              datalec2 = lecturas2.getPorcentajeODT(odt,idportal);

int totalregistro=0,capturado=0,Nocapturado=0; double  resultcapturado,resultNocapturado;
        ArrayList<Entry> valsY = new ArrayList<>();
        if(datalec2.getCount() > 0){
            while(datalec2.moveToNext()){

              valsY.add(new Entry(datalec2.getInt(datalec2.getColumnIndex("capturado")),0));
              valsY.add(new Entry(datalec2.getInt(datalec2.getColumnIndex("Nocapturado")),1));
                totalregistro=datalec2.getInt(datalec2.getColumnIndex("total"));
                capturado=datalec2.getInt(datalec2.getColumnIndex("capturado"));
                Nocapturado=datalec2.getInt(datalec2.getColumnIndex("Nocapturado"));
            }
        }
        lecturas2.close();


        setContentView(R.layout.activity_porcentaje);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        pieChart = (PieChart) findViewById(R.id.pieChart);

        /*definimos algunos atributos*/
        pieChart.setHoleRadius(40f);
        //pieChart.setDrawYValues(true);
        //pieChart.setDrawXValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        /*creamos una lista para los valores Y*/
        //ArrayList<Entry> valsY = new ArrayList<>();
        //valsY.add(new Entry(5* 100 / 25,0));
        //valsY.add(new Entry(20 * 100 / 25,1));

        resultcapturado=((double)capturado/(double)totalregistro)*100;
        resultNocapturado=((double)Nocapturado/(double)totalregistro)*100;
        //Log.d("totalregistro",""+totalregistro);


        //Log.d("capturado",""+capturado);
        //Log.d("Nocapturado",""+Nocapturado);

        //Log.d("resultcapturado",""+resultcapturado);
        //Log.d("resultNocapturado",""+resultNocapturado);

        DecimalFormat Vresultcapturado = new DecimalFormat("#0.00");
        DecimalFormat VresultNocapturado = new DecimalFormat("#0.00");



        /*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();
        valsX.add("Registrado: "+Vresultcapturado.format(resultcapturado)+" %");
        valsX.add("Sin registrar: "+VresultNocapturado.format(resultNocapturado)+" %");


        /*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.colorAccent));
        colors.add(getResources().getColor(R.color.colorPrimary));

        /*seteamos los valores de Y y los colores*/
        //PieDataSet set1 = new PieDataSet(valsY, "");
        PieDataSet set1 = new PieDataSet(valsY, "Total: "+totalregistro);

        set1.setSliceSpace(3f);
        set1.setColors(colors);
        set1.setValueTextSize(15);

        /*seteamos los valores de X*/
        PieData data = new PieData(valsX, set1);


        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        /*Ocutar descripcion*/
        pieChart.setDescription("");
        /*Ocultar leyenda*/
       // pieChart.setDrawLegend(false);


    }
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
    @Override
    public void onBackPressed() {
    }
}
