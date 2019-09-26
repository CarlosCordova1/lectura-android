package com.example.jcruz.lectura11.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBanomaliaoperation {
    public static final String LOGTAG = "DBanomaliaoperation";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
              DBlecturaHandler.anomalia_COLUMN_ID,
            DBlecturaHandler.anomalia_COLUMN_codigo,
            DBlecturaHandler.anomalia_COLUMN_descripcion


    };
    private static final String[] allColumns_ruta = {
            DBlecturaHandler.anomalia_COLUMN_ID,
            DBlecturaHandler.anomalia_COLUMN_codigo,
            DBlecturaHandler.anomalia_COLUMN_descripcion
           };
/*
    public DBanomaliaoperation(Context context){
        dbhandler = new DBanomaliahandler(context);
    }
    */
    public DBanomaliaoperation(Context context){
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
       // database.delete(DBanomaliahandler.TABLE_anomalia, null, null);

    }
    public void TRUNCATE(){
        Log.i(LOGTAG, "table TRUNCATE");
          //database.delete(DBanomaliahandler.TABLE_anomalia, null, null);
        database.execSQL("DELETE FROM "+DBlecturaHandler.anomalia_TABLE); //delete all rows in a table
    }


    public DBanomalia addAnomalia(DBanomalia anomalia){
        ContentValues values  = new ContentValues();
        values.put(DBlecturaHandler.anomalia_COLUMN_codigo,anomalia.getcodigo());
        values.put(DBlecturaHandler.anomalia_COLUMN_descripcion,anomalia.getDescripcion());
        long insertid = database.insert(DBlecturaHandler.anomalia_TABLE,null,values);
        anomalia.setid(insertid);
        return anomalia;

    }

    // Getting single data
    public DBanomalia getanomalia(long id) {

        Cursor cursor = database.query(DBlecturaHandler.anomalia_TABLE,allColumns, DBlecturaHandler.anomalia_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DBanomalia e = new DBanomalia( cursor.getLong(0),cursor.getString(1),
                cursor.getString(2)           );
        // return data
        return e;
    }

    public List<DBanomalia> getAllAnomalia() {

        Cursor cursor = database.query(DBlecturaHandler.anomalia_TABLE,allColumns,null,
                null,null, null, null);

        List<DBanomalia> dataAnomalia = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                DBanomalia value = new DBanomalia();
                value.setid(cursor.getLong(cursor.getColumnIndex(DBlecturaHandler.anomalia_COLUMN_ID)));
                value.setcodigo(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.anomalia_COLUMN_codigo)));
                value.setDescripcion(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.anomalia_COLUMN_descripcion)));
                dataAnomalia.add(value);
            }
        }
        // return All Employees
        return dataAnomalia;
    }


    public List<DBanomalia> getanomaliaBycodigo(String codi) {
        String whereClause = "codigo="+codi;
        Cursor cursor = database.query(DBlecturaHandler.anomalia_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, null);


        List<DBanomalia> dataAnomalia = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                DBanomalia value = new DBanomalia();
                value.setid(cursor.getLong(cursor.getColumnIndex(DBlecturaHandler.anomalia_COLUMN_ID)));
                value.setcodigo(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.anomalia_COLUMN_codigo)));
                value.setDescripcion(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.anomalia_COLUMN_descripcion)));
                dataAnomalia.add(value);
            }
        }

        return dataAnomalia;
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