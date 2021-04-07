package com.unoesc.trabalhofinal.models;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    protected abstract String ObterDescricaoListagem();

    @Override
    public String toString(){
        return ObterDescricaoListagem();
    }
}
