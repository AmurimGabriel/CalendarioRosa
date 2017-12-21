package com.example.aluno.calendario;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FormAddLembretesActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    Button btnPickDate, btnPickTime;
    EditText txtTitulo, txtAssunto, txtLocal, txtDescricao;
    TextView txtData, txtHora, btnSalvar, btnCancelar, btnEditar, btnApagar;
    TextInputLayout layoutTitulo, layoutAssunto, layoutLocal, layoutDescricao;
    int ano, mes, dia, hora, minuto;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Boolean okdata, oktime;
    int diaDoTxt, mesDoTxt, anoDoTxt, horaDoTxt, minutoDoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lembretes);

        btnSalvar = (TextView) findViewById(R.id.btnSalvar);
        btnCancelar = (TextView) findViewById(R.id.btnCancelar);
        btnEditar = (TextView) findViewById(R.id.btnEditar);
        btnApagar = (TextView) findViewById(R.id.btnApagar);

        btnPickTime = (Button) findViewById(R.id.btnPickHora);
        btnPickDate = (Button) findViewById(R.id.btnPickData);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtAssunto = (EditText) findViewById(R.id.txtAssunto);
        txtLocal = (EditText) findViewById(R.id.txtLocal);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);

        layoutTitulo = (TextInputLayout) findViewById(R.id.textInputLayout);
        layoutAssunto = (TextInputLayout) findViewById(R.id.textInputLayout2);

        txtData = (TextView) findViewById(R.id.lblData);
        txtHora = (TextView) findViewById(R.id.lblHora);

        okdata = false;
        oktime = false;

        final DatabaseCRUD bd = new DatabaseCRUD(this);

        txtTitulo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60), new EmojiExcludeFilter()});
        txtAssunto.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60), new EmojiExcludeFilter()});
        txtLocal.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60), new EmojiExcludeFilter()});
        txtDescricao.setFilters(new InputFilter[]{new InputFilter.LengthFilter(128), new EmojiExcludeFilter()});

        txtTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutTitulo.setError(null);
            }
        });

        txtAssunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutAssunto.setError(null);
            }
        });

        if (RepositorioCentral.dia > 0) {

            diaDoTxt = RepositorioCentral.dia;
            mesDoTxt = RepositorioCentral.mes;
            anoDoTxt = RepositorioCentral.ano;

        }

        if (RepositorioCentral.telaListData || RepositorioCentral.telaListShow) {

            EntityLembrete e;
            e = bd.buscarUm(RepositorioCentral.idDoLembrete);

            txtTitulo.setText(e.getTitulo());
            txtAssunto.setText(e.getAssunto());
            txtLocal.setText(e.getLocal());
            txtDescricao.setText(e.getDescricao());

            String weekDay;
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar calendar = Calendar.getInstance();
            calendar.set(e.getAno(), e.getMes(), e.getDia());
            weekDay = dayFormat.format(calendar.getTime());

            txtData.setText(e.getDia() + "/" + e.getMes() + "/" + e.getAno() + " ( " + diaDaSemana(weekDay) + " )");

            if (e.getHora() > 11)
                txtHora.setText(e.getHora() + "h " + e.getMin() + "min");
            else
                txtHora.setText(e.getHora() + "h " + e.getMin() + "min");

            btnSalvar.setVisibility(View.INVISIBLE);
            btnCancelar.setVisibility(View.INVISIBLE);

            diaDoTxt = e.getDia();
            mesDoTxt = e.getMes();
            anoDoTxt = e.getAno();
            horaDoTxt = e.getHora();
            minutoDoTxt = e.getMin();


        } else {

            btnEditar.setVisibility(View.INVISIBLE);
            btnApagar.setVisibility(View.INVISIBLE);
        }

        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(FormAddLembretesActivity.this)
                        .setTitle("Excluir Lembrete")
                        .setMessage("Deseja excluir este lembrete?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                EntityLembrete entity = new EntityLembrete();
                                entity.setIdItem(RepositorioCentral.idDoLembrete);
                                bd.excluir(entity);
                                limparCampos();
                                sair();
                            }
                        })
                        .create()
                        .show();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EntityLembrete entity = new EntityLembrete();

                entity.setIdItem(RepositorioCentral.idDoLembrete);

                int taCerto = 0;

                if (txtTitulo.getText().length() == 0) {
                    layoutTitulo.setError("*Campo Obrigatório!");
                    layoutTitulo.setFocusable(true);
                } else {
                    entity.setTitulo(txtTitulo.getText().toString());
                    taCerto++;
                }

                if (txtAssunto.getText().length() == 0) {
                    layoutAssunto.setError("*Campo Obrigatório!");
                    layoutAssunto.setFocusable(true);
                } else {
                    entity.setAssunto(txtAssunto.getText().toString());
                    taCerto++;
                }
                entity.setLocal(txtLocal.getText().toString());
                entity.setDescricao(txtDescricao.getText().toString());

                Calendar calendar = Calendar.getInstance();

                if (okdata) {
                    entity.setDia(dia);
                    entity.setMes(mes);
                    entity.setAno(ano);

                    calendar.set(ano, mes, dia);

                } else {
                    entity.setDia(diaDoTxt);
                    entity.setMes(mesDoTxt);
                    entity.setAno(anoDoTxt);

                    calendar.set(anoDoTxt, mesDoTxt, diaDoTxt);
                }

                if (oktime) {
                    entity.setHora(hora);
                    entity.setMin(minuto);

                    calendar.set(hora, minuto);
                } else {
                    entity.setHora(horaDoTxt);
                    entity.setMin(minutoDoTxt);

                    calendar.set(horaDoTxt, minutoDoTxt);
                }

                entity.setDiaDoAno(calendar.get(Calendar.DAY_OF_YEAR));

                okdata = false;
                oktime = false;
                if (taCerto == 2) {
                    bd.atualizar(entity);
                    limparCampos();
                    sair();
                    Toast.makeText(FormAddLembretesActivity.this, "Lembrete atualizado", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EntityLembrete entity = new EntityLembrete();
                int taCerto = 0;

                if (txtTitulo.getText().length() == 0) {
                    layoutTitulo.setError("*Campo Obrigatório!");
                    layoutTitulo.setFocusable(true);
                } else {
                    entity.setTitulo(txtTitulo.getText().toString());
                    taCerto++;
                }

                if (txtAssunto.getText().length() == 0) {
                    layoutAssunto.setError("*Campo Obrigatório!");
                    layoutAssunto.setFocusable(true);
                } else {
                    entity.setAssunto(txtAssunto.getText().toString());
                    taCerto++;
                }

                if (dia == 0) {
                    Toast.makeText(FormAddLembretesActivity.this, "Nenhuma DATA foi selecionada", Toast.LENGTH_SHORT).show();
                } else {
                    entity.setDia(dia);
                    entity.setMes(mes);
                    entity.setAno(ano);
                    taCerto++;
                }

                if (hora == 0 && minuto == 0) {
                    Toast.makeText(FormAddLembretesActivity.this, "Nenhuma HORA foi selecionada", Toast.LENGTH_SHORT).show();
                } else {
                    entity.setHora(hora);
                    entity.setMin(minuto);
                    taCerto++;
                }

                entity.setLocal(txtLocal.getText().toString());
                entity.setDescricao(txtDescricao.getText().toString());

                if (taCerto == 4) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(ano, mes, dia, hora, minuto);
                    entity.setDiaDoAno(calendar.get(Calendar.DAY_OF_YEAR));

                    bd.inserir(entity);
                    Toast.makeText(FormAddLembretesActivity.this, "Lembrete salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    sair();
                    limparCampos();
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sair();
                Toast.makeText(FormAddLembretesActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        });


        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FormAddLembretesActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Data");

                okdata = true;

            }
        });

        btnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = timePickerDialog.newInstance(
                        FormAddLembretesActivity.this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        false);

                tpd.show(getFragmentManager(), "Horário");

                oktime = true;

            }
        });

        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FormAddLembretesActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Data");

                okdata = true;
            }

        });

        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = timePickerDialog.newInstance(
                        FormAddLembretesActivity.this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        false);

                tpd.show(getFragmentManager(), "Horário");

                oktime = true;

            }
        });

        txtDescricao.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        if (hourOfDay >= 12)
            txtHora.setText(hourOfDay + "h" + minute + "min");
        else
            txtHora.setText(hourOfDay + "h" + minute + "min");

        hora = hourOfDay;
        minuto = minute;

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        weekDay = dayFormat.format(calendar.getTime());

        txtData.setText(dayOfMonth + "/" + monthOfYear + "/" + year + " ( " + diaDaSemana(weekDay) + " )");
        ano = year;
        mes = monthOfYear;
        dia = dayOfMonth;
    }

    @Override
    public void onBackPressed() {
        sair();
        return;
    }

    public void sair() {

        if (RepositorioCentral.telaListData) {
            startActivity(new Intent(FormAddLembretesActivity.this, ListDataActivity.class));
            finish();
            RepositorioCentral.telaListData = false;
        }
        if (RepositorioCentral.telaListShow) {
            startActivity(new Intent(FormAddLembretesActivity.this, ListLembretesActivity.class));
            finish();
            RepositorioCentral.telaListShow = false;
        }
        if (RepositorioCentral.btnAdd) {
            startActivity(new Intent(FormAddLembretesActivity.this, ListLembretesActivity.class));
            finish();
            RepositorioCentral.btnAdd = false;
        }
        if (RepositorioCentral.btnAdc) {
            startActivity(new Intent(FormAddLembretesActivity.this, CalendarioActivity.class));
            finish();
            RepositorioCentral.btnAdc = false;
        }
        if (RepositorioCentral.btnAdd2) {
            startActivity(new Intent(FormAddLembretesActivity.this, ListDataActivity.class));
            finish();
            RepositorioCentral.btnAdd2 = false;
        }
    }

    public void limparCampos() {
        txtTitulo.setText("");
        txtAssunto.setText("");
        txtLocal.setText("");
        txtData.setText("");
        txtHora.setText("");
        txtDescricao.setText("");

    }

    public String diaDaSemana(String s) {

        switch (s) {

            case "Sunday":
                s = "Domingo";
                break;

            case "Monday":
                s = "Segunda";
                break;

            case "Tuesday":
                s = "Terça";
                break;

            case "Wednesday":
                s = "Quarta";
                break;

            case "Thursday":
                s = "Quinta";
                break;

            case "Friday":
                s = "Sexta";
                break;

            case "Saturday":
                s = "Sábado";
                break;
        }

        return s;
    }

    class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

}
