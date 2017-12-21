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

public class Adapter extends BaseAdapter {

    Context context;
    private ArrayList<EntityLembrete> listLembretes;

    public Adapter(Context context, ArrayList<EntityLembrete> listLembretes) {
        this.context = context;
        this.listLembretes = listLembretes;
    }

    @Override
    public int getCount() {
        return listLembretes.size();
    }

    @Override
    public Object getItem(int position) {
        return listLembretes.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        EntityLembrete item = (EntityLembrete) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.activity_adapter_item, null);
        TextView txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDateTime);
        TextView txtAssunto = (TextView) view.findViewById(R.id.txtAssunto);

        txtTitulo.setText(item.getTitulo());
        txtAssunto.setText("( " + item.getAssunto() + " )");

        String data = item.getDia() + "/" + item.getMes() + "/" + item.getAno(), hora = item.getHora() + "h" + item.getMin() + "min";

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(item.getAno(), item.getMes(), item.getDia());
        weekDay = dayFormat.format(calendar.getTime());

        txtDate.setText(data + "   " + hora + "  " + " ( " + diaDaSemana(weekDay) + " )");

        txtTitulo.setTextColor(Color.parseColor("#FF4081"));

        return view;
    }

    public String diaDaSemana(String s){

        switch (s){

            case "Sunday" :
                s = "Dom";
                break;

            case "Monday":
                s = "Seg";
                break;

            case "Tuesday":
                s = "Ter";
                break;

            case "Wednesday":
                s = "Quar";
                break;

            case "Thursday":
                s = "Quin";
                break;

            case "Friday":
                s = "Sex";
                break;

            case "Saturday":
                s = "Sab";
                break;
        }

        return s;
    }
}
