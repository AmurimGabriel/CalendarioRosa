package com.example.aluno.calendario;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView calendarView;
    FloatingActionButton btnToday, btnAdc;
    List<EventDay> events;

    int yy, mm, dd;

    TextView txt;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnToday = (FloatingActionButton) findViewById(R.id.btnToday);
        btnAdc = (FloatingActionButton) findViewById(R.id.btnAdc);

        events = new ArrayList<>();

        final DatabaseCRUD bd = new DatabaseCRUD(this);

        List<EntityLembrete> list = bd.buscar();

        for (EntityLembrete e : list) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(e.getAno(), e.getMes(), e.getDia(), e.getHora(), e.getMin());
            events.add(new EventDay(calendar, R.drawable.ic_marcaa));
        }
        ;

        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                Calendar selectedDate = calendarView.getSelectedDate();

                RepositorioCentral.dia = selectedDate.get(Calendar.DAY_OF_MONTH);
                RepositorioCentral.mes = selectedDate.get(Calendar.MONTH);
                RepositorioCentral.ano = selectedDate.get(Calendar.YEAR);

                startActivity(new Intent(CalendarioActivity.this, ListDataActivity.class));
                finish();

            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                calendarView.setDate(calendar);
                Toast.makeText(CalendarioActivity.this, "Voltando para o dia de hoje...", Toast.LENGTH_SHORT).show();
            }
        });


        btnAdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(CalendarioActivity.this, "Adicionar novo lembrete", Toast.LENGTH_SHORT).show();
                RepositorioCentral.btnAdc = true;
                startActivity(new Intent(CalendarioActivity.this, FormAddLembretesActivity.class));
                finish();
            }
        });
    }

}
