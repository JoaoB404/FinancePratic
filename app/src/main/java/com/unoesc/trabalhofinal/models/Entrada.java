package com.unoesc.trabalhofinal.models;

import java.util.Date;

public class Entrada extends Entity {
    private Date data;
    private float valor;
    private String observacao;

    @Override
    protected String ObterDescricaoListagem() {
        return getId() + " - " + getValor();
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
