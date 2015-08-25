package com.example.lauramarra.quintafeira;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lauramarra on 11/02/15.
 */
public class MyDBHandler2 extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "andamentoDB.db";
    public static final String TABLE_ANDAMENTO = "andamento";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PERIODO = "periodo";
    public static final String COLUMN_DIASEMANA = "diaSemana";
    public static final String COLUMN_STATUS = "status";

    public MyDBHandler2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ANDAMENTO + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CODIGO + " TEXT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_PERIODO + " TEXT, " +
                COLUMN_DIASEMANA + " TEXT, " +
                COLUMN_STATUS + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANDAMENTO);
        onCreate(db);
    }

    public void addMateria(GradeParcialDB materia){
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODIGO, materia.get_codigo());
        values.put(COLUMN_NOME, materia.get_nome());
        values.put(COLUMN_PERIODO, materia.get_periodo());
        values.put(COLUMN_DIASEMANA, materia.get_diaSemana());
        values.put(COLUMN_STATUS, materia.get_status());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ANDAMENTO, null, values);
        db.close();
    }

    public void delMateria(String codigo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ANDAMENTO + " WHERE " + COLUMN_CODIGO + "=\"" + codigo + "\";");
    }

    public boolean itemExists(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANDAMENTO + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {
                return true;
            }
            c.moveToNext();
        }
        db.close();
        return false;
    }

    public String getClassName(String day){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANDAMENTO;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {

                String days = c.getString(c.getColumnIndex("diaSemana"));
                String [] days2 = days.split(":");

                for(int i = 0; i < days2.length; i++) {
                    if((c.getString(c.getColumnIndex("status")).equalsIgnoreCase("cursando")) &&
                            days2[i].equalsIgnoreCase(day)) {
                        dbString += c.getString(c.getColumnIndex("nome"));
                        dbString += ";";
                    }
                }
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String getClassID(String day){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANDAMENTO;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {

                String days = c.getString(c.getColumnIndex("diaSemana"));
                String [] days2 = days.split(":");

                for(int i = 0; i < days2.length; i++) {
                    if ((c.getString(c.getColumnIndex("status")).equalsIgnoreCase("cursando")) &&
                            days2[i].equalsIgnoreCase(day)) {
                        dbString += c.getString(c.getColumnIndex("codigo"));
                        dbString += ";";
                    }
                }
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
