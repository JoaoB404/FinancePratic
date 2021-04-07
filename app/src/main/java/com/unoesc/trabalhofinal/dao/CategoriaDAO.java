package com.unoesc.trabalhofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unoesc.trabalhofinal.models.Categoria;

public class CategoriaDAO extends ApplicationDAO<Categoria> {
    public CategoriaDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues MontarDadosInserir(Categoria entity) {
        ContentValues valores = new ContentValues();
        valores.put("Descricao", entity.getDescricao());
        valores.put("Observacao", entity.getObservacao());

        return valores;
    }

    @Override
    protected String ObterNomeTabela() {
        return "Categoria";
    }

    @Override
    protected Categoria CriarRegistroListagem(Cursor c) {
        Categoria categoria = new Categoria();
        categoria.setId(c.getLong(c.getColumnIndex("Id")));
        categoria.setDescricao(c.getString(c.getColumnIndex("Descricao")));
        categoria.setObservacao(c.getString(c.getColumnIndex("Observacao")));

        return categoria;
    }

    @Override
    protected void PreencherValoresAtualizacao(ContentValues valores, Categoria entity) {
        valores.put("Descricao", entity.getDescricao());
        valores.put("Observacao", entity.getObservacao());
    }
}