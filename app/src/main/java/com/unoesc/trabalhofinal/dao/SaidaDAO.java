package com.unoesc.trabalhofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unoesc.trabalhofinal.models.Entrada;
import com.unoesc.trabalhofinal.models.Saida;

import java.util.ArrayList;
import java.util.List;

public class SaidaDAO extends ApplicationDAO<Saida> {
    public SaidaDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues MontarDadosInserir(Saida entity) {
        ContentValues valores = new ContentValues();
        valores.put("CategoriaId", entity.getCategoriaId());
        valores.put("Data", entity.getData());
        valores.put("Valor", entity.getValor());
        valores.put("Observacao", entity.getObservacao());
        valores.put("CaminhoComprovante", entity.getCaminhoComprovante());

        return valores;
    }

    @Override
    protected String ObterNomeTabela() {
        return "Saida";
    }

    @Override
    protected Saida CriarRegistroListagem(Cursor c) {
        Saida dado = new Saida();
        dado.setId(c.getLong(c.getColumnIndex("Id")));
        dado.setCategoriaId(c.getLong(c.getColumnIndex("CategoriaId")));
        dado.setData(c.getString(c.getColumnIndex("Data")));
        dado.setValor(c.getFloat(c.getColumnIndex("Valor")));
        dado.setObservacao(c.getString(c.getColumnIndex("Observacao")));
        dado.setCaminhoComprovante(c.getString(c.getColumnIndex("CaminhoComprovante")));

        return dado;
    }

    @Override
    protected void PreencherValoresAtualizacao(ContentValues valores, Saida entity) {
        valores.put("CategoriaId", entity.getCategoriaId());
        valores.put("Data", entity.getData());
        valores.put("Valor", entity.getValor());
        valores.put("Observacao", entity.getObservacao());
        valores.put("CaminhoComprovante", entity.getCaminhoComprovante());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sqlCategoria = new StringBuilder();

        sqlCategoria.append("CREATE TABLE Saida(Id INTEGER PRIMARY KEY, CategoriaId INTEGER, Valor REAL, Data TEXT NOT NULL, Observacao TEXT, CaminhoComprovante TEXT);");

        db.execSQL(sqlCategoria.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Saida;";

        db.execSQL(sql);
        onCreate(db);
    }

    public float ObterTotalSaidas(){
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
