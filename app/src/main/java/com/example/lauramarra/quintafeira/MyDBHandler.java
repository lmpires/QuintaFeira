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

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fluxogramaDB.db";
    public static final String TABLE_FLUXOGRAMA = "fluxograma";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PERIODO = "periodo";
    public static final String COLUMN_CREDITOS = "creditos";
    public static final String COLUMN_PREREQUISITO1 = "preRequisito1";
    public static final String COLUMN_PREREQUISITO2 = "preRequisito2";
    public static final String COLUMN_PREREQUISITO3 = "preRequisito3";
    public static final String COLUMN_DIASEMANA = "diaSemana";


    //We need to pass database information along to superclass
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
                COLUMN_PREREQUISITO2 + " TEXT, " +
                COLUMN_PREREQUISITO3 + " TEXT, " +
                COLUMN_DIASEMANA + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLUXOGRAMA);
        onCreate(db);
    }

    //Add a new row to the database
    public void addMateria(Materia materia){
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODIGO, materia.get_codigo());
        values.put(COLUMN_NOME, materia.get_nome());
        values.put(COLUMN_PERIODO, materia.get_periodo());
        values.put(COLUMN_CREDITOS, materia.get_creditos());
        values.put(COLUMN_PREREQUISITO1, materia.get_preRequisito1());
        values.put(COLUMN_PREREQUISITO2, materia.get_preRequisito2());
        values.put(COLUMN_PREREQUISITO3, materia.get_preRequisito3());
        values.put(COLUMN_DIASEMANA, materia.get_diaSemana());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FLUXOGRAMA, null, values);
        db.close();
    }
    //Delete a product from the database
    public void delMateria(String codigo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FLUXOGRAMA + " WHERE " + COLUMN_CODIGO + "=\"" + codigo + "\";");
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FLUXOGRAMA + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);

        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {
                dbString += "Codigo: ";
                dbString += c.getString(c.getColumnIndex("codigo"));
                dbString += "\n";

                dbString += "Nome: ";
                dbString += c.getString(c.getColumnIndex("nome"));
                dbString += "\n";

                dbString += "Periodo: ";
                dbString += c.getString(c.getColumnIndex("periodo"));
                dbString += "\n";

                dbString += "Creditos: ";
                dbString += c.getString(c.getColumnIndex("creditos"));
                dbString += "\n";

                dbString += "Pre requisito 1: ";
                dbString += c.getString(c.getColumnIndex("preRequisito1"));
                dbString += "\n";

                dbString += "Pre requisito 2: ";
                dbString += c.getString(c.getColumnIndex("preRequisito2"));
                dbString += "\n";

                dbString += "Pre requisito 3: ";
                dbString += c.getString(c.getColumnIndex("preRequisito3"));
                dbString += "\n";

                dbString += "Dia da semana: ";
                dbString += c.getString(c.getColumnIndex("diaSemana"));
                dbString += "\n\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
