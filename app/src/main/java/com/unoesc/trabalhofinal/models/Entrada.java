package com.unoesc.trabalhofinal.models;

import android.text.Editable;

import java.util.Date;

public class Entrada extends Entity {
    private Long categoriaId;
    private String data;
    private float valor;
    private String observacao;

    @Override
    protected String ObterDescricaoListagem() {
        return getData() + " - " + getValor();
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
