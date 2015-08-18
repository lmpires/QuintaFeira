package com.example.lauramarra.quintafeira;

/**
 * Created by lauramarra on 11/02/15.
 */
public class GradeParcialDB extends GradeCompletaDB {
    private int _id;
    private String _codigo, _nome, _diaSemana, _status;

    public GradeParcialDB(){}

    public GradeParcialDB(String codigo, String nome, String diaSemana, String status) {
        this._codigo = codigo;
        this._nome = nome;
        this._diaSemana = diaSemana;
        this._status = status;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_codigo(String _codigo) {
        this._codigo = _codigo;
    }

    public void set_nome(String _nome) {
        this._nome = _nome;
    }

    public void set_diaSemana (String _diaSemana) { this._diaSemana = _diaSemana;}

    public void set_status(String _status) { this._status = _status; }

    public int get_id() {
        return _id;
    }

    public String get_codigo(){
        return _codigo;
    }

    public String get_nome() {
        return _nome;
    }

    public String get_status() { return _status; }

    public String get_diaSemana () { return _diaSemana; }

}
