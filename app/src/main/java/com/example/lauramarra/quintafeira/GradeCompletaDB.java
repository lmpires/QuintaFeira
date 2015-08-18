package com.example.lauramarra.quintafeira;

/**
 * Created by lauramarra on 07/02/15.
 */
public class GradeCompletaDB {
    private int _id;
    private String _codigo, _nome, _periodo, _creditos, _preRequisito1, _diaSemana, _local, _status;

    public GradeCompletaDB(){}

    public GradeCompletaDB(String codigo,
                           String nome,
                           String periodo,
                           String creditos,
                           String preRequisito1,
                           String diaSemana,
                           String local,
                           String status) {
        this._codigo = codigo;
        this._nome = nome;
        this._periodo = periodo;
        this._creditos = creditos;
        this._preRequisito1 = preRequisito1;
        this._diaSemana = diaSemana;
        this._local = local;
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

    public void set_periodo(String _periodo) {
        this._periodo = _periodo;
    }

    public void set_creditos(String _creditos) {
        this._creditos = _creditos;
    }

    public void set_preRequisito1(String _preRequisito1) {
        this._preRequisito1 = _preRequisito1;
    }

    public void set_diaSemana(String _diaSemana) {
        this._diaSemana = _diaSemana;
    }

    public void set_local(String _local) { this._local = _local; }

    public void set_status(String _status) {this._status = _status; }

    public int get_id() {
        return _id;
    }

    public String get_codigo(){
        return _codigo;
    }

    public String get_nome() {
        return _nome;
    }

    public String get_periodo() {
        return _periodo;
    }

    public String get_creditos() {
        return _creditos;
    }

    public String get_preRequisito1() {
        return _preRequisito1;
    }

    public String get_diaSemana() {
        return _diaSemana;
    }

    public String get_local() { return _local; }

    public String get_status() { return _status; }
}
