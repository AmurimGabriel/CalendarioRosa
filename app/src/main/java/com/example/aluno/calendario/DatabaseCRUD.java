package com.example.aluno.calendario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCRUD {
    private SQLiteDatabase bd;

    public DatabaseCRUD(Context c) {

        DatabaseCore bdCore = new DatabaseCore(c);
        bd = bdCore.getWritableDatabase();
    }

    public void inserir(EntityLembrete entityLembrete) {
        ContentValues valores = new ContentValues();


        valores.put("titulo", entityLembrete.getTitulo());
        valores.put("assunto", entityLembrete.getAssunto());
        valores.put("descricao", entityLembrete.getDescricao());
        valores.put("local", entityLembrete.getLocal());
        valores.put("hora", entityLembrete.getHora());
        valores.put("min", entityLembrete.getMin());
        valores.put("dia", entityLembrete.getDia());
        valores.put("mes", entityLembrete.getMes());
        valores.put("ano", entityLembrete.getAno());
        valores.put("diaDoAno", entityLembrete.getDiaDoAno());

        bd.insert("Lembretes", null, valores);
    }

    public void atualizar(EntityLembrete entityLembrete) {
        ContentValues valores = new ContentValues();

        valores.put("titulo", entityLembrete.getTitulo());
        valores.put("assunto", entityLembrete.getAssunto());
        valores.put("descricao", entityLembrete.getDescricao());
        valores.put("local", entityLembrete.getLocal());
        valores.put("hora", entityLembrete.getHora());
        valores.put("min", entityLembrete.getMin());
        valores.put("dia", entityLembrete.getDia());
        valores.put("mes", entityLembrete.getMes());
        valores.put("ano", entityLembrete.getAno());
        valores.put("diaDoAno", entityLembrete.getDiaDoAno());

        String[] string = new String[]{entityLembrete.getIdItem()+""};

        String campos = "_ID = ?";

        int ret = bd.update("Lembretes", valores, campos, string);
        ret++;
    }

    public void excluir(EntityLembrete entityLembrete) {
        bd.delete("Lembretes", " _ID = " + entityLembrete.getIdItem(), null);
    }

    public List<EntityLembrete> buscar() {
        List<EntityLembrete> list = new ArrayList<EntityLembrete>();

        Cursor c = bd.rawQuery("SELECT * FROM Lembretes", null);

        if (c.getCount() > 0) {
            c.moveToFirst();

            do {
                EntityLembrete e = new EntityLembrete();

                e.setTitulo(c.getString(c.getColumnIndex("titulo")));
                e.setAssunto(c.getString(c.getColumnIndex("assunto")));
                e.setDescricao(c.getString(c.getColumnIndex("descricao")));
                e.setLocal(c.getString(c.getColumnIndex("local")));
                e.setHora(c.getInt(c.getColumnIndex("hora")));
                e.setMin(c.getInt(c.getColumnIndex("min")));
                e.setDia(c.getInt(c.getColumnIndex("dia")));
                e.setMes(c.getInt(c.getColumnIndex("mes")));
                e.setAno(c.getInt(c.getColumnIndex("ano")));
                e.setDiaDoAno(c.getInt(c.getColumnIndex("diaDoAno")));
                e.setIdItem(c.getInt(c.getColumnIndex("_ID")));

                RepositorioCentral.idDoLembrete = c.getInt(c.getColumnIndex("_ID"));

                list.add(e);

            } while (c.moveToNext());
        }
        return list;
    }

    public EntityLembrete buscarUm(int id) {
        EntityLembrete e = new EntityLembrete();

        String[] colunas = new String[]{"titulo", "assunto", "descricao", "local", "hora", "min", "dia", "mes", "ano", "diaDoAno", "_ID"};
        Cursor c = bd.query("Lembretes", colunas, " _ID = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();

            e.setTitulo(c.getString(c.getColumnIndex("titulo")));
            e.setAssunto(c.getString(c.getColumnIndex("assunto")));
            e.setDescricao(c.getString(c.getColumnIndex("descricao")));
            e.setLocal(c.getString(c.getColumnIndex("local")));
            e.setHora(c.getInt(c.getColumnIndex("hora")));
            e.setMin(c.getInt(c.getColumnIndex("min")));
            e.setDia(c.getInt(c.getColumnIndex("dia")));
            e.setMes(c.getInt(c.getColumnIndex("mes")));
            e.setAno(c.getInt(c.getColumnIndex("ano")));
            e.setDiaDoAno(c.getInt(c.getColumnIndex("diaDoAno")));
            e.setIdItem(c.getInt(c.getColumnIndex("_ID")));

        }
        return e;
    }

//    public EntityLembrete buscarCar(int id) {
//        EntityLembrete e = new EntityLembrete();
//
//        String[] colunas = new String[]{"titulo", "assunto", "descricao", "local", "hora", "min", "dia", "mes", "ano", "diaDoAno", "_ID"};
//        Cursor c = bd.query("Lembretes", colunas, " diaDoAno = ?", new String[]{String.valueOf(id)}, null, null, "diaDoAno ASC");
//
//        if (c.getCount() > 0) {
//            c.moveToFirst();
//
//            e.setTitulo(c.getString(c.getColumnIndex("titulo")));
//            e.setAssunto(c.getString(c.getColumnIndex("assunto")));
//            e.setDescricao(c.getString(c.getColumnIndex("descricao")));
//            e.setLocal(c.getString(c.getColumnIndex("local")));
//            e.setHora(c.getInt(c.getColumnIndex("hora")));
//            e.setMin(c.getInt(c.getColumnIndex("min")));
//            e.setDia(c.getInt(c.getColumnIndex("dia")));
//            e.setMes(c.getInt(c.getColumnIndex("mes")));
//            e.setAno(c.getInt(c.getColumnIndex("ano")));
//            e.setDiaDoAno(c.getInt(c.getColumnIndex("diaDoAno")));
//            e.setIdItem(c.getInt(c.getColumnIndex("_ID")));
//
//        }
//        return e;
//    }

    public List<EntityLembrete> buscarUmLista(int dia, int mes, int ano) {
        List<EntityLembrete> list = new ArrayList<EntityLembrete>();

        String[] colunas = new String[]{"titulo", "assunto", "descricao", "local", "hora", "min", "dia", "mes", "ano", "diaDoAno", "_ID"};
        Cursor c = bd.query("Lembretes", colunas, " dia = ? AND mes = ? AND ano = ? ", new String[]{dia+"", mes+"", ano+""}, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();

            do {
                EntityLembrete e = new EntityLembrete();

                e.setTitulo(c.getString(c.getColumnIndex("titulo")));
                e.setAssunto(c.getString(c.getColumnIndex("assunto")));
                e.setDescricao(c.getString(c.getColumnIndex("descricao")));
                e.setLocal(c.getString(c.getColumnIndex("local")));
                e.setHora(c.getInt(c.getColumnIndex("hora")));
                e.setMin(c.getInt(c.getColumnIndex("min")));
                e.setDia(c.getInt(c.getColumnIndex("dia")));
                e.setMes(c.getInt(c.getColumnIndex("mes")));
                e.setAno(c.getInt(c.getColumnIndex("ano")));
                e.setDiaDoAno(c.getInt(c.getColumnIndex("diaDoAno")));
                e.setIdItem(c.getInt(c.getColumnIndex("_ID")));

                list.add(e);

            } while (c.moveToNext());
        }
        return list;
    }

}
