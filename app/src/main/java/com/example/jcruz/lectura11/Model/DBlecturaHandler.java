package com.example.jcruz.lectura11.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBlecturaHandler extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "mlect.db";
    private static final int DATABASE_VERSION = 1;


    public static final String anomalia_TABLE= "anomalia";
    public static final String anomalia_COLUMN_ID = "id";
    public static final String anomalia_COLUMN_codigo = "codigo";
    public static final String anomalia_COLUMN_descripcion = "descripcion";

    public static final String usuario_TABLE= "usuario";
    public static final String usuario_COLUMN_ID = "id";
    public static final String usuario_COLUMN_idportal = "idportal";
    public static final String usuario_COLUMN_nombre = "nombre";
    public static final String usuario_COLUMN_userlogin = "userlogin";
    public static final String usuario_COLUMN_passwlogin = "passwlogin";
    public static final String usuario_COLUMN_imei = "imei";
    public static final String usuario_COLUMN_token = "tkn";

    public static final String foto_TABLE= "foto";
    public static final String foto_COLUMN_ID = "id";
    public static final String foto_COLUMN_idodt = "idodt";
    public static final String foto_COLUMN_idlec = "idlec";
    public static final String foto_COLUMN_contrato = "contrato";
    public static final String foto_COLUMN_nombre = "nombre";
    public static final String foto_COLUMN_base64 = "base64";
    public static final String foto_COLUMN_fecha = "fecha";

    private static final String foto_TABLE_CREATE =
            "CREATE TABLE " + foto_TABLE + " (" +
                    foto_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    foto_COLUMN_idodt + " NUMERIC, " +
                    foto_COLUMN_idlec+ " NUMERIC, " +
                    foto_COLUMN_contrato+ " TEXT, " +
                    foto_COLUMN_nombre + " TEXT, " +
                    foto_COLUMN_fecha + " TEXT, " +
                    foto_COLUMN_base64 + " TEXT " +

                    ")";




    public static final String lectura_TABLE= "lectura";
    public static final String lectura_COLUMN_ID = "id";
    public static final String lectura_COLUMN_idtlec = "idtlec";
    public static final String lectura_COLUMN_ruta = "ruta";
    public static final String lectura_COLUMN_uso = "uso";

    public static final String lectura_COLUMN_idtodt = "idtodt";
    public static final String lectura_COLUMN_numcpr = "idmedidor";
    public static final String lectura_COLUMN_medidor = "medidor";
    public static final String lectura_COLUMN_rng1= "secuencia";
    public static final String lectura_COLUMN_idtclt= "idtclt";
    public static final String lectura_COLUMN_idtctr= "idtctr";
    public static final String lectura_COLUMN_idtusrlec= "idtusrlec";
    public static final String lectura_COLUMN_fchuac= "fchuac";
    public static final String lectura_COLUMN_fchlec= "fchlec";
    public static final String lectura_COLUMN_fch_verifica= "fch_verifica";
    public static final String lectura_COLUMN_lectura= "lectura";
    public static final String lectura_COLUMN_lecturaver= "lecturaver";
    public static final String lectura_COLUMN_coordx= "coordx";
    public static final String lectura_COLUMN_coordy= "coordy";
    public static final String lectura_COLUMN_mcoordx= "mcoordx";
    public static final String lectura_COLUMN_mcoordy= "mcoordy";
    public static final String lectura_COLUMN_edocliente= "edocliente";
    public static final String lectura_COLUMN_gironegocio= "gironegocio";
    public static final String lectura_COLUMN_domicilio= "domicilio";
    public static final String lectura_COLUMN_consumoprom= "consumoprom";
    public static final String lectura_COLUMN_lecturaant= "lecturaant";
    public static final String lectura_COLUMN_idtanm1= "idtanm1";
    public static final String lectura_COLUMN_idtanm2= "idtanm2";
    public static final String lectura_COLUMN_comentario= "comentario";
    public static final String lectura_COLUMN_edo= "edo";
    public static final String lectura_COLUMN_idtscru= "idtscru";
    public static final String lectura_COLUMN_limitedown= "limitedown";
    public static final String lectura_COLUMN_limiteup= "limiteup";

    public static final String lectura_COLUMN_limiteup2= "limiteup2";
    public static final String lectura_COLUMN_limitedown2= "limitedown2";
    public static final String lectura_COLUMN_ruedas= "ruedas";
    public static final String lectura_COLUMN_fechacorte= "fechacorte";
    public static final String lectura_COLUMN_intentos= "intentos";
    public static final String lectura_COLUMN_idedocliente= "idedocliente";

    private static final String lectura_TABLE_CREATE =
            "CREATE TABLE " + lectura_TABLE + " (" +
                    lectura_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    lectura_COLUMN_idtlec + " NUMERIC, " +
                    lectura_COLUMN_idtodt + " NUMERIC, " +
                    lectura_COLUMN_ruta + " TEXT, " +
                    lectura_COLUMN_numcpr + " TEXT, " +
                    lectura_COLUMN_medidor + " TEXT, " +
                    lectura_COLUMN_rng1 + " NUMERIC, " +
                    lectura_COLUMN_idtclt + " TEXT, " +
                    lectura_COLUMN_idtctr + " TEXT, " +
                    lectura_COLUMN_idtusrlec + " NUMERIC, " +
                    lectura_COLUMN_fchuac + " TEXT, " +
                    lectura_COLUMN_fchlec + " TEXT, " +
                    lectura_COLUMN_fch_verifica + " TEXT, " +
                    lectura_COLUMN_lectura + " NUMERIC, " +
                    lectura_COLUMN_lecturaver + " NUMERIC, " +
                    lectura_COLUMN_coordx + " NUMERIC, " +
                    lectura_COLUMN_coordy + " NUMERIC, " +
                    lectura_COLUMN_mcoordx + " NUMERIC, " +
                    lectura_COLUMN_mcoordy + " NUMERIC, " +
                    lectura_COLUMN_edocliente + " TEXT, " +
                    lectura_COLUMN_gironegocio + " TEXT, " +
                    lectura_COLUMN_uso + " TEXT, " +
                    lectura_COLUMN_domicilio + " TEXT, " +
                    lectura_COLUMN_consumoprom + " NUMERIC, " +
                    lectura_COLUMN_lecturaant + " NUMERIC, " +
                    lectura_COLUMN_idtanm1 + " NUMERIC, " +
                    lectura_COLUMN_idtanm2 + " NUMERIC, " +
                    lectura_COLUMN_comentario + " TEXT, " +
                    lectura_COLUMN_edo + " TEXT, " +
                    lectura_COLUMN_idtscru + " NUMERIC, " +
                    lectura_COLUMN_limitedown + " NUMERIC, " +
                    lectura_COLUMN_limiteup + " NUMERIC, " +
                         lectura_COLUMN_limiteup2+ " NUMERIC, " +
                          lectura_COLUMN_limitedown2+ " NUMERIC, " +
                          lectura_COLUMN_ruedas+ " NUMERIC, " +
                         lectura_COLUMN_fechacorte+ " TEXT, " +
                     lectura_COLUMN_intentos + " NUMERIC, " +
                      lectura_COLUMN_idedocliente + " TEXT " +

                    ")";
    private static final String anomalia_TABLE_CREATE =
            "CREATE TABLE " + anomalia_TABLE + " (" +
                    anomalia_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    anomalia_COLUMN_codigo + " TEXT, " +
                    anomalia_COLUMN_descripcion + " TEXT " +
                    ")";


    private static final String usuario_TABLE_CREATE =
            "CREATE TABLE " + usuario_TABLE + " (" +
                    usuario_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    usuario_COLUMN_idportal + " NUMERIC, " +
                    usuario_COLUMN_nombre + " TEXT, " +
                    usuario_COLUMN_userlogin + " TEXT, " +
                    usuario_COLUMN_passwlogin + " TEXT, " +
                    usuario_COLUMN_imei + " TEXT, " +
                    usuario_COLUMN_token + " TEXT " +

                    ")";



    public DBlecturaHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(lectura_TABLE_CREATE);
        db.execSQL(anomalia_TABLE_CREATE);
        db.execSQL(usuario_TABLE_CREATE);
        db.execSQL(foto_TABLE_CREATE);
        //db.delete("lectura", null, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+lectura_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+anomalia_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+usuario_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+foto_TABLE);
        db.execSQL(lectura_TABLE_CREATE);
        db.execSQL(anomalia_TABLE_CREATE);
        db.execSQL(usuario_TABLE_CREATE);
        db.execSQL(foto_TABLE_CREATE);
    }
}