package com.example.lauramarra.quintafeira;

/**
 * Created by lauramarra on 11/02/15.
 */
public class CheckMateria {
    String code = null;
    String name = null;
    String periodo = null;
    String creditos = null;
    String preRequisito1 = null;
    String preRequisito2 = null;
    String preRequisito3 = null;
    String diaSemana = null;
    String local = null;
    String status;
    boolean selected = false;

    public CheckMateria(String code,
                        String name,
                        String periodo,
                        String diaSemana,
                        boolean selected,
                        String status) {
        super();
        this.code = code;
        this.name = name;
        this.periodo = periodo;
        this.diaSemana = diaSemana;
        this.selected = selected;
        this.status = status;
    }

    public String getCode() { return code; }

    public String getName() { return name; }

    public String getDiaSemana () { return diaSemana; }

    public String getPeriodo() { return periodo;}

    public String getCreditos() {
        return creditos;
    }

    public String getPreRequisito1() {
        return preRequisito1;
    }

    public String getPreRequisito2() {
        return preRequisito2;
    }

    public String getPreRequisito3() {
        return preRequisito3;
    }

    public String getLocal() { return local; }

    public String getStatus() {return status; }

    public boolean isSelected() { return selected; }


    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) { this.name = name; }

    public void setDiaSemana (String diaSemana) { this.diaSemana = diaSemana;}

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public void setPreRequisito1(String preRequisito1) {
        this.preRequisito1 = preRequisito1;
    }

    public void setPreRequisito2(String preRequisito2) {
        this.preRequisito2 = preRequisito2;
    }

    public void setPreRequisito3(String preRequisito3) {
        this.preRequisito3 = preRequisito3;
    }

    public void setLocal(String local) { this.local = local; }

    public void setStatus(String status) {this.status = status; }

    public void setSelected(boolean selected) { this.selected = selected; }
}
