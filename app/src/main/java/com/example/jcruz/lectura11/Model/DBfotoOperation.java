package com.example.jcruz.lectura11.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBfotoOperation {
    public static final String LOGTAG = "DBfotoOperation";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBlecturaHandler.foto_COLUMN_ID,
            DBlecturaHandler.foto_COLUMN_idodt,
            DBlecturaHandler.foto_COLUMN_idlec,
            DBlecturaHandler.foto_COLUMN_contrato,
            DBlecturaHandler.foto_COLUMN_nombre,
            DBlecturaHandler.foto_COLUMN_fecha,
            DBlecturaHandler.foto_COLUMN_base64
    };

    public DBfotoOperation(Context context){
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
        database.delete(DBlecturaHandler.foto_TABLE, null, null);

    }

    public DBfoto addFoto(DBfoto foto){
        ContentValues values  = new ContentValues();
        values.put(DBlecturaHandler.foto_COLUMN_idodt,foto.getidodt());
        values.put(DBlecturaHandler.foto_COLUMN_idlec,foto.getidlec());
        values.put(DBlecturaHandler.foto_COLUMN_contrato,foto.getcontrato());
        values.put(DBlecturaHandler.foto_COLUMN_nombre,foto.getnombre());
        values.put(DBlecturaHandler.foto_COLUMN_fecha,foto.getfecha());
        values.put(DBlecturaHandler.foto_COLUMN_base64,foto.getbase64());
        long insertid = database.insert(DBlecturaHandler.foto_TABLE,null,values);
        foto.setid(insertid);
        return foto;

    }

    /* // Getting single data
    public DBfoto getFoto(long id) {

        Cursor cursor = database.query(DBlecturaHandler.foto_TABLE,allColumns,
                DBlecturaHandler.foto_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DBfoto e = new DBfoto( cursor.getLong(0),cursor.getInt(1),
                cursor.getString(2),cursor.getString(3),  cursor.getString(4)         );
        // return data
        return e;
    }
    */

    public List<DBfoto> getFotobyodtandidlec(int odt, int idlec) {
        String whereClause = " idlec='"+idlec + "'" + " and idodt='"+odt + "'";
        Cursor cursor = database.query(DBlecturaHandler.foto_TABLE,allColumns,whereClause,
                null,null, null, null);

        List<DBfoto> datafoto = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                DBfoto value = new DBfoto();
                value.setid(cursor.getLong(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_ID)));
                value.setidodt(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_idodt)));
                value.setidlec(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_idlec)));
                value.setcontrato(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_contrato)));
                value.setnombre(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_nombre)));
                value.setfecha(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_fecha)));
                value.setbase64(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.foto_COLUMN_base64)));
                datafoto.add(value);
            }
        }
        // return All datafoto
        return datafoto;
    }

    public Cursor getAllFotoEnviarTox7(int idtodt, int idlec) {

        // Cursor cursor = database.query(DBlecturaHandler.lectura_TABLE,allColumns_ruta,
        //       null,
        //     null,"idtodt", null, null);


        Cursor cursor = database.rawQuery("select * from foto where idodt="+idtodt+" and idlec="+idlec, null);




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






}