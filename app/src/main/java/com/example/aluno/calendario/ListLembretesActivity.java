package com.example.aluno.calendario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ListLembretesActivity extends AppCompatActivity {

    private ListView listView;
    private TextView btnVoltar, btnAdd, txtTemNada;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lembretes);

        btnVoltar = (TextView) findViewById(R.id.btnVoltar);
        btnAdd = (TextView) findViewById(R.id.btnAdd);
        listView = (ListView) findViewById(R.id.listView);
        txtTemNada = (TextView) findViewById(R.id.txtTemNada);

        adapter = new Adapter(this, PegaArrayItens());
        listView.setAdapter(adapter);

        txtTemNada.setVisibility(View.INVISIBLE);
        if(adapter.getCount() <= 0){

            txtTemNada.setVisibility(View.VISIBLE);
        }

        final DatabaseCRUD bd = new DatabaseCRUD(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                EntityLembrete entity = (EntityLembrete)listView.getAdapter().getItem(i);
                int id = entity.getIdItem();

                RepositorioCentral.idDoLembrete = id;
                RepositorioCentral.telaListShow = true;

                startActivity(new Intent(ListLembretesActivity.this, FormAddLembretesActivity.class));
                finish();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepositorioCentral.telaListShow = false;
                startActivity(new Intent(ListLembretesActivity.this, MainActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ListLembretesActivity.this, FormAddLembretesActivity.class));
                finish();
                RepositorioCentral.btnAdd = true;
            }
        });

    }

    private ArrayList<EntityLembrete> PegaArrayItens() {
        ArrayList<EntityLembrete> listItem;

        DatabaseCRUD bd = new DatabaseCRUD(this);
        listItem = (ArrayList<EntityLembrete>) bd.buscar();

        return listItem;
    }

    @Override
    public void onBackPressed() {
        RepositorioCentral.telaListShow = false;
        startActivity(new Intent(ListLembretesActivity.this, MainActivity.class));
        finish();
    }
}
