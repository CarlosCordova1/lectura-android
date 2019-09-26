package com.example.jcruz.lectura11.Model;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;


import com.example.jcruz.lectura11.Model.DBlecturaHandler;
import com.example.jcruz.lectura11.Model.Mlect;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
        import java.util.List;


public class MlectOperation {
    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
           // DBlecturaHandler.COLUMN_ID,
            DBlecturaHandler.lectura_COLUMN_idtodt,
            DBlecturaHandler.lectura_COLUMN_ruta,
            DBlecturaHandler.lectura_COLUMN_numcpr,
            DBlecturaHandler.lectura_COLUMN_medidor,
            DBlecturaHandler.lectura_COLUMN_rng1,
            DBlecturaHandler.lectura_COLUMN_idtclt,
            DBlecturaHandler.lectura_COLUMN_idtctr,
            DBlecturaHandler.lectura_COLUMN_uso,
            DBlecturaHandler.lectura_COLUMN_edocliente,
            DBlecturaHandler.lectura_COLUMN_edo,
            DBlecturaHandler.lectura_COLUMN_idtanm1,
            DBlecturaHandler.lectura_COLUMN_idtanm2

    };
    private static final String[] allColumns_ruta = {
            DBlecturaHandler.lectura_COLUMN_ID,
            DBlecturaHandler.lectura_COLUMN_idtlec,
            DBlecturaHandler.lectura_COLUMN_idtodt,
            DBlecturaHandler.lectura_COLUMN_ruta,
            DBlecturaHandler.lectura_COLUMN_numcpr,
            DBlecturaHandler.lectura_COLUMN_medidor,
            DBlecturaHandler.lectura_COLUMN_rng1,
            DBlecturaHandler.lectura_COLUMN_idtclt,
            DBlecturaHandler.lectura_COLUMN_idtctr,
            DBlecturaHandler.lectura_COLUMN_domicilio,
            DBlecturaHandler.lectura_COLUMN_gironegocio,
            DBlecturaHandler.lectura_COLUMN_uso,
            DBlecturaHandler.lectura_COLUMN_edocliente,
            DBlecturaHandler.lectura_COLUMN_edo,
            DBlecturaHandler.lectura_COLUMN_idtanm1,
            DBlecturaHandler.lectura_COLUMN_idtanm2,
            DBlecturaHandler.lectura_COLUMN_comentario,
            DBlecturaHandler.lectura_COLUMN_lectura,
            DBlecturaHandler.lectura_COLUMN_consumoprom,
            DBlecturaHandler.lectura_COLUMN_lecturaant,
            DBlecturaHandler.lectura_COLUMN_limitedown,
            DBlecturaHandler.lectura_COLUMN_limiteup,
            DBlecturaHandler.lectura_COLUMN_limitedown2,
            DBlecturaHandler.lectura_COLUMN_limiteup2,

            DBlecturaHandler.lectura_COLUMN_ruedas,
            DBlecturaHandler.lectura_COLUMN_fechacorte,
            DBlecturaHandler.lectura_COLUMN_intentos,
            DBlecturaHandler.lectura_COLUMN_idedocliente



    };
/*
    public MlectOperation(Context context){
        dbhandler = new MlectDBhandler(context);
    }
    */
    public MlectOperation(Context context){
        dbhandler = new DBlecturaHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }

    public void delete(){
        Log.i(LOGTAG, "table delete");
      // database.delete("lectura", null, null);

         }
    public void TRUNCATE(int ideportal){
        Log.i(LOGTAG, "table TRUNCATE");

        database.execSQL("DELETE FROM "+DBlecturaHandler.lectura_TABLE +" where "+DBlecturaHandler.lectura_COLUMN_idtusrlec+"="+ideportal); //delete all rows in a table


    }

    public Mlect addLectura(Mlect lectura){
        ContentValues values  = new ContentValues();
        values.put(DBlecturaHandler.lectura_COLUMN_idtlec,lectura.getidtlec());
        values.put(DBlecturaHandler.lectura_COLUMN_idtodt,lectura.getidtodt());
        values.put(DBlecturaHandler.lectura_COLUMN_ruta,lectura.getruta());
        values.put(DBlecturaHandler.lectura_COLUMN_numcpr,lectura.getnumcpr());
        values.put(DBlecturaHandler.lectura_COLUMN_medidor,lectura.getMedidor());
        values.put(DBlecturaHandler.lectura_COLUMN_rng1, lectura.getrng1());
        values.put(DBlecturaHandler.lectura_COLUMN_idtclt, lectura.getidtclt());
        values.put(DBlecturaHandler.lectura_COLUMN_idtctr, lectura.getidtctr());
        values.put(DBlecturaHandler.lectura_COLUMN_idtusrlec, lectura.getidtusrlec());
        values.put(DBlecturaHandler.lectura_COLUMN_fchuac, lectura.getfchuac());
        values.put(DBlecturaHandler.lectura_COLUMN_edocliente, lectura.getedocliente());
        values.put(DBlecturaHandler.lectura_COLUMN_gironegocio, lectura.getgironegocio());
        values.put(DBlecturaHandler.lectura_COLUMN_uso,lectura.getUso());
        values.put(DBlecturaHandler.lectura_COLUMN_domicilio, lectura.getdomicilio());
        values.put(DBlecturaHandler.lectura_COLUMN_consumoprom, lectura.getconsumoprom());
        values.put(DBlecturaHandler.lectura_COLUMN_lecturaant, lectura.getlecturaant());
        values.put(DBlecturaHandler.lectura_COLUMN_edo, lectura.getedo());
        values.put(DBlecturaHandler.lectura_COLUMN_idtscru, lectura.getidtscru());
        values.put(DBlecturaHandler.lectura_COLUMN_limitedown, lectura.getlimitedown());
        values.put(DBlecturaHandler.lectura_COLUMN_limiteup, lectura.getlimiteup());

        values.put(DBlecturaHandler.lectura_COLUMN_limitedown2, lectura.getlimitedown2());
        values.put(DBlecturaHandler.lectura_COLUMN_limiteup2, lectura.getlimiteup2());
        values.put(DBlecturaHandler.lectura_COLUMN_ruedas, lectura.getruedas());
        values.put(DBlecturaHandler.lectura_COLUMN_fechacorte, lectura.getfechacorte());

        values.put(DBlecturaHandler.lectura_COLUMN_intentos, lectura.getintentos());
        values.put(DBlecturaHandler.lectura_COLUMN_idedocliente, lectura.getidedocliente());

        long insertid = database.insert(DBlecturaHandler.lectura_TABLE,null,values);
        lectura.setid(insertid);
        return lectura;

    }

    // Getting single data
    public Mlect getEmployee(long id) {

        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns, DBlecturaHandler.lectura_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Mlect e = new Mlect(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return Employee
        return e;
    }

    public List<Mlect> getAllLectura() {

        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns,null,
                null,null, null, null);

        List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                employee.setid(cursor.getLong(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtclt)));
                //employee.setidtodt(cursor.getInt(cursor.getColumnIndex(MlectDBhandler.COLUMN_idtodt)));
                //employee.setnumcpr(cursor.getString(cursor.getColumnIndex(MlectDBhandler.COLUMN_numcpr)));
                //employee.setrng1(cursor.getInt(cursor.getColumnIndex(MlectDBhandler.COLUMN_rng1)));
               // employee.setidtclt(cursor.getString(cursor.getColumnIndex(MlectDBhandler.COLUMN_idtclt)));
                //employee.setidtclt(cursor.getString(cursor.getColumnIndex(MlectDBhandler.COLUMN_idtctr)));
                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }

    public Cursor getAllRuta(int idportal) {

      // Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
        //       null,
          //     null,"idtodt", null, null);


        Cursor cursor = database.rawQuery("select d.idtodt as odt, d.idtlec,d.ruta, count(*) as total,\n" +
                "(select count(*) as t from lectura as v where v.idtodt=d.idtodt  and v.edo ='CAP' AND v.idtusrlec="+idportal+" ) as capturado\n" +
                " from lectura as d where d.idtusrlec="+idportal+" group by d.idtodt", null);




        //List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            /*while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                employee.setidtlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtlec)));
                employee.setidtodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtodt)));
                employee.setruta(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruta)));

                employees.add(employee);
            }*/
        }
        // return All Employees
        return cursor;
    }

    public Cursor getAllRutaEnviarTox7(int idportal) {

        // Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
        //       null,
        //     null,"idtodt", null, null);


        Cursor cursor = database.rawQuery("select idtodt,idmedidor as idlec,fchlec,lectura,idtanm1,idtanm2, comentario,edo,intentos " +
                "from lectura where idtusrlec="+idportal+" and lectura is not null and EDO='CAP' OR EDO='MOD'  order by idtodt,idmedidor asc", null);




        //List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            /*while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                employee.setidtlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtlec)));
                employee.setidtodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtodt)));
                employee.setruta(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruta)));

                employees.add(employee);
            }*/
        }
        // return All Employees
        return cursor;
    }



    public List<Mlect> getAllRutabyODT(int vODT , int idportal) {
        String whereClause = "idtodt="+vODT+ " AND idtusrlec="+idportal;
        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, DBlecturaHandler.lectura_COLUMN_rng1+ " ASC");


        List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                employee.setidtlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtlec)));
                employee.setidtodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtodt)));
                employee.setruta(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruta)));
                employee.setnumcpr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_numcpr)));
                employee.setMedidor(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_medidor)));
                employee.setrng1(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_rng1)));
                 employee.setidtclt(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtclt)));
                employee.setidtctr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtctr)));
                employee.setUso(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_uso)));
                employee.setidtanm1(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm1)));
                employee.setidtanm2(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm2)));
                employee.setlectura(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_lectura)));
                employee.setgironegocio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_gironegocio)));
                employee.setdomicilio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_domicilio)));
                employee.setedocliente(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edocliente)));
                employee.setedo(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edo)));
                employee.setcomentario(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_comentario)));

                employee.setconsumoprom(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_consumoprom)));
                employee.setlecturaant(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_lecturaant)));
                employee.setlimitedown(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limitedown)));
                employee.setlimiteup(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limiteup)));

                employee.setlimitedown2(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limitedown2)));
                employee.setlimiteup2(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limiteup2)));

                employee.setruedas(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruedas)));
                employee.setfechacorte(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_fechacorte)));
                employee.setintentos(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_intentos)));
                employee.setidedocliente(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idedocliente)));




                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }


    public Cursor getPorcentajeODT(int vODT,  int idportal) {
        String whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+" ="+vODT+" AND idtusrlec="+idportal;
       /* Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, DBlecturaHandler.lectura_COLUMN_rng1+ " ASC");*/

        Cursor cursor = database.rawQuery("select count(*) total, " +
                "(select count(*)  from lectura as p where p.edo ='NVO' and p.idtodt="+vODT +" AND p.idtusrlec="+idportal+") as Nocapturado," +
                "(select count(*)  from lectura as l where l.edo ='CAP' and l.idtodt="+vODT+" AND l.idtusrlec="+idportal+") as capturado " +
                "from lectura as w where  w.idtodt="+vODT+" AND w.idtusrlec="+idportal, null);

       // List<Mlect> employees = new ArrayList<>();
      //  ArrayList<Entry> valsY = new ArrayList<>();
/*
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                //Mlect employee = new Mlect();
                //employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                  // employees.add(employee);
             //   valsY.add(new Entry(cursor.getInt(cursor.getColumnIndex("capturado")),0));
               // valsY.add(new Entry(cursor.getInt(cursor.getColumnIndex("Nocapturado")),1));
               //valsY.add(new Entry(cursor.getInt(cursor.getColumnIndex("total")),2));
            }
        }*/
        // return All Employees
        return cursor;
    }


    public List<Mlect> getAllRutabySearch(int vODT,String busqueda, int idportal) {
        String whereClause=null;
        String str = busqueda;
        String [] arrOfStr = str.split("\\.");
       // Log.d("str"," "+str);


        whereClause ="";
        if (arrOfStr[0].equals("s")){//secuencia
            whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+    " AND idtusrlec="+idportal+ " AND  "+DBlecturaHandler.lectura_COLUMN_rng1+" = '"+arrOfStr[1]+"'" ;
        }
        else if (arrOfStr[0].equals("m")){//medidor
            whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+  " AND idtusrlec="+idportal+ " AND  "+DBlecturaHandler.lectura_COLUMN_medidor+" = '"+arrOfStr[1]+"'" ;
        }
        else if (arrOfStr[0].equals("mb")){//medidor busqueda
            whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+  " AND idtusrlec="+idportal+ " AND  "+DBlecturaHandler.lectura_COLUMN_medidor+"  LIKE  '%"+arrOfStr[1]+"%'" ;
        }
        else if (arrOfStr[0].equals("d")){//domicilio
            whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+  " AND idtusrlec="+idportal+ " AND  "+DBlecturaHandler.lectura_COLUMN_domicilio+" LIKE '%"+arrOfStr[1]+"%'" ;
        }
        else if (arrOfStr[0].equals("rs")){
            String [] arrOfStr2 = arrOfStr[1].split("\\-");

            whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+  " AND idtusrlec="+idportal+ " AND " +
                    " "+DBlecturaHandler.lectura_COLUMN_rng1+" >= '"+arrOfStr2[0]+"'"+" AND  "+DBlecturaHandler.lectura_COLUMN_rng1+" <= '"+arrOfStr2[1]+"'" ;
        }
        else
        {
            whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+  " AND idtusrlec="+idportal+ " AND  "+DBlecturaHandler.lectura_COLUMN_domicilio+" LIKE '%"+arrOfStr[1]+"%'" ;
        }

         // whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+" AND  (" +
           //         ""+DBlecturaHandler.lectura_COLUMN_rng1+" LIKE '%"+busqueda+"%' OR "+DBlecturaHandler.lectura_COLUMN_medidor+" LIKE '%"+busqueda+"%')" ;



        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, DBlecturaHandler.lectura_COLUMN_rng1+ " ASC");


        List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                employee.setidtlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtlec)));
                employee.setidtodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtodt)));
                employee.setruta(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruta)));
                employee.setnumcpr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_numcpr)));
                employee.setMedidor(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_medidor)));
                employee.setrng1(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_rng1)));
                employee.setidtclt(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtclt)));
                employee.setidtctr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtctr)));
                employee.setUso(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_uso)));
                employee.setidtanm1(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm1)));
                employee.setidtanm2(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm2)));
                employee.setlectura(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_lectura)));
                employee.setgironegocio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_gironegocio)));
                employee.setdomicilio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_domicilio)));
                employee.setedocliente(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edocliente)));
                employee.setedo(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edo)));
                employee.setcomentario(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_comentario)));

                employee.setconsumoprom(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_consumoprom)));
                employee.setlecturaant(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_lecturaant)));
                employee.setlimitedown(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limitedown)));
                employee.setlimiteup(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limiteup)));

                employee.setlimitedown2(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limitedown2)));
                employee.setlimiteup2(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limiteup2)));

                employee.setruedas(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruedas)));

                employee.setfechacorte(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_fechacorte)));

                employee.setintentos(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_intentos)));
                employee.setidedocliente(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idedocliente)));


                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }


    public List<Mlect> getAllRutaPendiente(int vODT, int idportal) {
        String whereClause=null;

        //whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+" AND "+DBlecturaHandler.lectura_COLUMN_lectura+" is null" ;
        whereClause = DBlecturaHandler.lectura_COLUMN_idtodt+"="+vODT+ " AND idtusrlec="+idportal+ " AND  "+DBlecturaHandler.lectura_COLUMN_edo+" ='NVO'" ;



        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, DBlecturaHandler.lectura_COLUMN_rng1+ " ASC");


        List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                employee.setidtlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtlec)));
                employee.setidtodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtodt)));
                employee.setruta(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruta)));
                employee.setnumcpr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_numcpr)));
                employee.setMedidor(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_medidor)));
                employee.setrng1(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_rng1)));
                employee.setidtclt(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtclt)));
                employee.setidtctr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtctr)));
                employee.setUso(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_uso)));
                employee.setidtanm1(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm1)));
                employee.setidtanm2(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm2)));
                employee.setlectura(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_lectura)));
                employee.setgironegocio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_gironegocio)));
                employee.setdomicilio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_domicilio)));
                employee.setedocliente(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edocliente)));
                employee.setedo(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edo)));
                employee.setcomentario(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_comentario)));
                employee.setconsumoprom(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_consumoprom)));
                employee.setlecturaant(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_lecturaant)));
                employee.setlimitedown(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limitedown)));
                employee.setlimiteup(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limiteup)));

                employee.setlimitedown2(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limitedown2)));
                employee.setlimiteup2(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_limiteup2)));

                employee.setruedas(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruedas)));

                employee.setfechacorte(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_fechacorte)));
                employee.setintentos(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_intentos)));
                employee.setidedocliente(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idedocliente)));

                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }



    // Updating lectura anomalia
    public int updateAnomalia(long id, int idtlec, int odt, String anml1,String anml2) {
        ContentValues values = new ContentValues();
        values.put(DBlecturaHandler.lectura_COLUMN_idtanm1, anml1);
        values.put(DBlecturaHandler.lectura_COLUMN_idtanm2, anml2);
        String whereClause = "id='"+id + "' and idtlec='"+idtlec + "'" + " and idtodt='"+odt + "'";
        // updating row
         return database.update(DBlecturaHandler.lectura_TABLE, values,
                whereClause,null);

    }

    // Updating lectura
    public int updatelectura(long id, int idtlec, int odt, String lectura, String fechalec,String nvoStado, int nuevosintentos) {
        ContentValues values = new ContentValues();
        values.put(DBlecturaHandler.lectura_COLUMN_lectura, lectura);
        values.put(DBlecturaHandler.lectura_COLUMN_fchlec, fechalec);
        values.put(DBlecturaHandler.lectura_COLUMN_edo, nvoStado);
        values.put(DBlecturaHandler.lectura_COLUMN_intentos, nuevosintentos);
        String whereClause = "id='"+id + "' and idtlec='"+idtlec + "'" + " and idtodt='"+odt + "'";
        // updating row
        return database.update(DBlecturaHandler.lectura_TABLE, values,
                whereClause,null);

    }
    // Updating lectura
    public int updatelectura_comentario(long id, int idtlec, int odt, String comentario) {
        ContentValues values = new ContentValues();
        values.put(DBlecturaHandler.lectura_COLUMN_comentario, comentario);
          String whereClause = "id='"+id + "' and idtlec='"+idtlec + "'" + " and idtodt='"+odt + "'";
        // updating row
        return database.update(DBlecturaHandler.lectura_TABLE, values,
                whereClause,null);

    }



    public List<Mlect> getAllRutabyODT_lect_id(long id, int idtlec, int odt) {
       // String whereClause = "idtodt="+vODT;
        String whereClause = "id='"+id + "' and idtlec='"+idtlec + "'" + " and idtodt='"+odt + "'";
        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, null);


        List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Mlect employee = new Mlect();
                /*employee.setid(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ID)));
                employee.setidtlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtlec)));
                employee.setidtodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtodt)));
                employee.setruta(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_ruta)));
                employee.setnumcpr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_numcpr)));
                employee.setMedidor(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_medidor)));
                employee.setrng1(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_rng1)));
                employee.setidtclt(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtclt)));
                employee.setidtctr(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtctr)));
                employee.setUso(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_uso)));
                */
                employee.setidtanm1(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm1)));
                employee.setidtanm2(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_idtanm2)));
                /*//employee.setUso(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_uso)));
                employee.setgironegocio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_gironegocio)));
                employee.setdomicilio(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_domicilio)));
                employee.setedoatl(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edocliente)));
                employee.setedo(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_edo)));
                */
                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }


    public List<Mlect> getcomentario(long id, int idtlec, int odt) {
        // String whereClause = "idtodt="+vODT;
        String whereClause = "id='"+id + "' and idtlec='"+idtlec + "'" + " and idtodt='"+odt + "'";
        Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, null);


        List<Mlect> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Mlect employee = new Mlect();
              employee.setcomentario(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.lectura_COLUMN_comentario)));

                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }



/*


    // Updating Employee
    public int updateEmployee(mLect employee) {

        ContentValues values = new ContentValues();
        values.put(MlectDBhandler.COLUMN_idtodt, employee.getFirstname());
        values.put(MlectDBhandler.COLUMN_numcpr, employee.getLastname());
        values.put(MlectDBhandler.COLUMN_rng1, employee.getGender());
        values.put(MlectDBhandler.COLUMN_idtclt, employee.getHiredate());
        values.put(MlectDBhandler.COLUMN_idtctr, employee.getDept());

        // updating row
        return database.update(MlectDBhandler.TABLE_lectura, values,
                MlectDBhandler.COLUMN_ID + "=?",new String[] { String.valueOf(employee.getEmpId())});
    }

    // Deleting Employee
    public void removeEmployee(mLect employee) {

        database.delete(MlectDBhandler.TABLE_lectura, MlectDBhandler.COLUMN_ID + "=" + employee.getEmpId(), null);
    }


*/
}