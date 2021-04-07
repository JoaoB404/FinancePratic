package com.unoesc.trabalhofinal.models;

public class Categoria extends Entity {
    private String Descricao;
    private String Observacao;

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    @Override
    public String ObterDescricaoListagem() {
        return getId() + " - " + getDescricao();
    }
}
