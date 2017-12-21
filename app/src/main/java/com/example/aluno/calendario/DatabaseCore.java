package com.example.aluno.calendario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCore extends SQLiteOpenHelper {

    public static  final String BANCO_NAME="CalendarioRosa";
    public static  final int BANCO_VERSION=7;

    public DatabaseCore(Context context) {
        super(context, BANCO_NAME, null, BANCO_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE Lembretes(titulo text, assunto text, descricao text, local text,"
        +" hora integer, min integer, dia integer, mes integer, ano integer, diaDoAno integer,_ID INTEGER PRIMARY KEY);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        bd.execSQL("DROP TABLE Lembretes");
        onCreate(bd);
    }
}
