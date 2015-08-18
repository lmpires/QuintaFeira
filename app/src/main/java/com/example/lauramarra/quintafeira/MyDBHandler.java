package com.example.lauramarra.quintafeira;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lauramarra on 07/02/15.
 */
public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "fluxogramaDB.db";
    public static final String TABLE_FLUXOGRAMA = "fluxograma";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PERIODO = "periodo";
    public static final String COLUMN_CREDITOS = "creditos";
    public static final String COLUMN_PREREQUISITO1 = "preRequisito1";
    public static final String COLUMN_DIASEMANA = "diaSemana";
    public static final String COLUMN_LOCAL = "local";
    public static final String COLUMN_STATUS = "status";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_FLUXOGRAMA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CODIGO + " TEXT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_PERIODO + " TEXT, " +
                COLUMN_CREDITOS + " TEXT, " +
                COLUMN_PREREQUISITO1 + " TEXT, " +
                COLUMN_DIASEMANA + " TEXT, " +
                COLUMN_LOCAL + " TEXT, " +
                COLUMN_STATUS + " TEXT" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLUXOGRAMA);
        onCreate(db);
    }

    public void addMateria(GradeCompletaDB gradeCompletaDB){
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODIGO, gradeCompletaDB.get_codigo());
        values.put(COLUMN_NOME, gradeCompletaDB.get_nome());
        values.put(COLUMN_PERIODO, gradeCompletaDB.get_periodo());
        values.put(COLUMN_CREDITOS, gradeCompletaDB.get_creditos());
        values.put(COLUMN_PREREQUISITO1, gradeCompletaDB.get_preRequisito1());
        values.put(COLUMN_DIASEMANA, gradeCompletaDB.get_diaSemana());
        values.put(COLUMN_LOCAL, gradeCompletaDB.get_local());
        values.put(COLUMN_STATUS, gradeCompletaDB.get_status());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FLUXOGRAMA, null, values);
        db.close();
    }

    public String getLocal(String codigo) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_CODIGO + ", " + COLUMN_LOCAL + ", " + COLUMN_NOME + " FROM " + TABLE_FLUXOGRAMA;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {
                if (c.getString(c.getColumnIndex("codigo")).equalsIgnoreCase(codigo)) {
                    dbString += c.getString(c.getColumnIndex("local"));
                }
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
