package com.example.aluno.calendario;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Acer on 17/12/2017.
 */

public class AdapterAnnotations extends BaseAdapter {

    Context context;
    private ArrayList<EntityAnotacoes> listAnotacoes;

    public AdapterAnnotations(Context context, ArrayList<EntityAnotacoes> listAnotacoes){
        this.context = context;
        this.listAnotacoes = listAnotacoes;
    }

    @Override
    public int getCount() {
        return listAnotacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return listAnotacoes.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        EntityAnotacoes item = (EntityAnotacoes) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.activity_adapter_anotacoes, null);
        TextView txtTituloData = (TextView) view.findViewById(R.id.txtTituloData);
        TextView txtConteudo= (TextView) view.findViewById(R.id.txtConteudo);

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(item.getAno(), item.getMes(), item.getDia());
        weekDay = dayFormat.format(calendar.getTime());

        String data = item.getDia() + "/" + item.getMes() + "/" + item.getAno(), hora = item.getHora() + "h" + item.getMin() + "min  ("+ weekDay+")";

        txtTituloData.setText(item.getTitulo() + "      " + data);
        txtConteudo.setText("" + item.getConteudo());

        txtTituloData.setTextColor(Color.parseColor("#FF4081"));

        return view;
    }
}
