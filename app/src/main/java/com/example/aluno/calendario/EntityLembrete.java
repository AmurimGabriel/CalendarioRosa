package com.example.aluno.calendario;

public class EntityLembrete {

    private String Titulo;
    private String Assunto;
    private String Descricao;
    private String Local;
    private int Hora;
    private int Min;
    private int Dia;
    private int Mes;
    private int Ano;
    private int diaDoAno;
    private int idItem;

    public EntityLembrete() {

    }

    public EntityLembrete(String titulo, String assunto, String descricao, String local, int hora, int min, int dia, int mes, int ano, int diaDoAno) {
        Titulo = titulo;
        Assunto = assunto;
        Descricao = descricao;
        Local = local;
        Hora = hora;
        Min = min;
        Dia = dia;
        Mes = mes;
        Ano = ano;
        this.diaDoAno = diaDoAno;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getAssunto() {
        return Assunto;
    }

    public void setAssunto(String assunto) {
        Assunto = assunto;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String local) {
        Local = local;
    }

    public int getHora() {
        return Hora;
    }

    public void setHora(int hora) {
        Hora = hora;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int ano) {
        Ano = ano;
    }

    public int getDiaDoAno() {
        return diaDoAno;
    }

    public void setDiaDoAno(int diaDoAno) {
        this.diaDoAno = diaDoAno;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
}
