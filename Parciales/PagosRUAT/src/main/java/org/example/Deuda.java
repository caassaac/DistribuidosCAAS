package org.example;

import java.io.Serializable;

public class Deuda implements Serializable {
    private String ci;
    private int ano;
    private String impuesto;
    private double monto;

    public Deuda(String ci, int ano, String impuesto, double monto) {
        this.ci = ci;
        this.ano = ano;
        this.impuesto = impuesto;
        this.monto = monto;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return ano + "," + impuesto + "," + monto;
    }
}
