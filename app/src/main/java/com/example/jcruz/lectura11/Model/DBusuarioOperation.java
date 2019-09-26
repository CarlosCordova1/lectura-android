package com.example.jcruz.lectura11.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBusuarioOperation {
    public static final String LOGTAG = "DBusuarioOperation";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBlecturaHandler.usuario_COLUMN_ID,
            DBlecturaHandler.usuario_COLUMN_idportal,
            DBlecturaHandler.usuario_COLUMN_nombre,
            DBlecturaHandler.usuario_COLUMN_userlogin,
            DBlecturaHandler.usuario_COLUMN_passwlogin,
            DBlecturaHandler.usuario_COLUMN_imei,
            DBlecturaHandler.usuario_COLUMN_token


    };
    private static final String[] allColumns_ruta = {
            DBlecturaHandler.usuario_COLUMN_ID,
            DBlecturaHandler.usuario_COLUMN_idportal,
            DBlecturaHandler.usuario_COLUMN_nombre,
            DBlecturaHandler.usuario_COLUMN_userlogin,
            DBlecturaHandler.usuario_COLUMN_passwlogin,
            DBlecturaHandler.usuario_COLUMN_imei,
            DBlecturaHandler.usuario_COLUMN_token
    };

    public DBusuarioOperation(Context context){
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
//        database.delete("usuario", null, null);
    }
    public void TRUNCATE(){
        Log.i(LOGTAG, "table TRUNCATE");
        //database.delete(DBanomaliahandler.TABLE_anomalia, null, null);
        database.execSQL("DELETE FROM "+DBlecturaHandler.usuario_TABLE); //delete all rows in a table
    }

    public DBusuario addUsuario(DBusuario usuario){
        ContentValues values  = new ContentValues();
        values.put(DBlecturaHandler.usuario_COLUMN_idportal,usuario.getidportal());
        values.put(DBlecturaHandler.usuario_COLUMN_nombre,usuario.getnombre());
        values.put(DBlecturaHandler.usuario_COLUMN_userlogin,usuario.getuserlogin());
        values.put(DBlecturaHandler.usuario_COLUMN_passwlogin,usuario.getpasswlogin());
        values.put(DBlecturaHandler.usuario_COLUMN_imei,usuario.getimei());
        values.put(DBlecturaHandler.usuario_COLUMN_token,usuario.gettoken());
        long insertid = database.insert(DBlecturaHandler.usuario_TABLE,null,values);
        usuario.setid(insertid);
        return usuario;

    }

    // Getting single data
    public DBusuario getusuario(long id) {

        Cursor cursor = database.query(DBlecturaHandler.usuario_TABLE,allColumns,
                DBlecturaHandler.usuario_COLUMN_ID + "=?",new String[]{String.valueOf(id)},
                null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DBusuario e = new DBusuario( cursor.getLong(0),cursor.getInt(1),
                cursor.getString(2),cursor.getString(3),
                cursor.getString(4) ,cursor.getString(5) ,cursor.getString(6)       );
        // return data
        return e;
    }
/*
    public List<DBanomalia> getAllAnomalia() {

        Cursor cursor = database.query(DBanomaliahandler.TABLE_anomalia,allColumns,null,
                null,null, null, null);

        List<DBanomalia> dataAnomalia = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                DBanomalia value = new DBanomalia();
                value.setid(cursor.getLong(cursor.getColumnIndex(DBanomaliahandler.COLUMN_ID)));
                value.setcodigo(cursor.getString(cursor.getColumnIndex(DBanomaliahandler.COLUMN_codigo)));
                value.setDescripcion(cursor.getString(cursor.getColumnIndex(DBanomaliahandler.COLUMN_descripcion)));
                dataAnomalia.add(value);
            }
        }
        // return All Employees
        return dataAnomalia;
    }
*/

    public List<DBusuario> getusuarioregistrado(String user, String passw) {
        //String whereClause = "userlogin='"+user + "' and passwlogin='"+passw + "' and imei='"+imei+"'";
        String whereClause = "userlogin='"+user + "' and passwlogin='"+passw + "'";
        Cursor cursor = database.query(DBlecturaHandler.usuario_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, null);


        List<DBusuario> datausuario = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                DBusuario value = new DBusuario();
                value.setid(cursor.getLong(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_ID)));
                value.setidportal(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_idportal)));
                value.setnombre(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_nombre)));
                value.setuserlogin(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_userlogin)));
                value.setpasswlogin(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_passwlogin)));
                value.setimei(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_imei)));
                value.settoken(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_token)));
                datausuario.add(value);
            }
        }

        return datausuario;
    }

    public List<DBusuario> getusuarioregistradoByidportal(int user) {
        //String whereClause = "userlogin='"+user + "' and passwlogin='"+passw + "' and imei='"+imei+"'";
        String whereClause = "idportal="+user + "";
        Cursor cursor = database.query(DBlecturaHandler.usuario_TABLE,allColumns_ruta,
                whereClause,
                null,null, null, null);


        List<DBusuario> datausuario = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                DBusuario value = new DBusuario();
                value.setid(cursor.getLong(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_ID)));
                value.setidportal(cursor.getInt(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_idportal)));
                value.setnombre(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_nombre)));
                value.setuserlogin(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_userlogin)));
                value.setpasswlogin(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_passwlogin)));
                value.setimei(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_imei)));
                value.settoken(cursor.getString(cursor.getColumnIndex(DBlecturaHandler.usuario_COLUMN_token)));
                datausuario.add(value);
            }
        }

        return datausuario;
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
