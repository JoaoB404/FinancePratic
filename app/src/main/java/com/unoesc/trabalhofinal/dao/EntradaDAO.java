package com.unoesc.trabalhofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unoesc.trabalhofinal.models.Categoria;
import com.unoesc.trabalhofinal.models.Entrada;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EntradaDAO extends ApplicationDAO<Entrada> {
    public EntradaDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues MontarDadosInserir(Entrada entity) {
        ContentValues valores = new ContentValues();
        valores.put("CategoriaId", entity.getCategoriaId());
        valores.put("Data", entity.getData().toString());
        valores.put("Valor", entity.getValor());
        valores.put("Observacao", entity.getObservacao());

        return valores;
    }

    @Override
    protected String ObterNomeTabela() {
        return "Entrada";
    }

    @Override
    protected Entrada CriarRegistroListagem(Cursor c) {
        Entrada dado = new Entrada();
        dado.setId(c.getLong(c.getColumnIndex("Id")));
        dado.setCategoriaId(c.getLong(c.getColumnIndex("CategoriaId")));
        dado.setData(c.getString(c.getColumnIndex("Data")));
        dado.setValor(c.getFloat(c.getColumnIndex("Valor")));
        dado.setObservacao(c.getString(c.getColumnIndex("Observacao")));

        return dado;
    }

    @Override
    protected void PreencherValoresAtualizacao(ContentValues valores, Entrada entity) {
        valores.put("CategoriaId", entity.getCategoriaId());
        valores.put("Data", entity.getData().toString());
        valores.put("Valor", entity.getValor());
        valores.put("Observacao", entity.getObservacao());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sqlCategoria = new StringBuilder();

        sqlCategoria.append("CREATE TABLE Entrada(Id INTEGER PRIMARY KEY, CategoriaId INTEGER, Valor REAL, Data TEXT NOT NULL, Observacao TEXT);");

        db.execSQL(sqlCategoria.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Entrada;";

        db.execSQL(sql);
        onCreate(db);
    }

    public float ObterTotalEntradas(){
        String sql = "SELECT Sum(Valor) as Retorno FROM " + ObterNomeTabela();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Entrada> lista = new ArrayList<Entrada>();

        float valor = 0;
        while(cursor.moveToNext()) {
            valor = cursor.getFloat(cursor.getColumnIndex("Retorno"));
        }

        cursor.close();

        return valor;
    }
}
