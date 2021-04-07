package com.unoesc.trabalhofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unoesc.trabalhofinal.models.Entity;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationDAO<T extends Entity> extends SQLiteOpenHelper {

    //region Constructors

    public ApplicationDAO(Context context) {
        super(context, "TrabalhoFinal", null, 1);
    }

    //endregion

    //region Protected Abstract Methods

    protected abstract ContentValues MontarDadosInserir(T entity);

    protected abstract String ObterNomeTabela();

    protected abstract T CriarRegistroListagem(Cursor c);

    protected abstract void PreencherValoresAtualizacao(ContentValues valores, T entity);

    //endregion

    //region Public Override Methods

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCategoria = "CREATE TABLE Categoria(Id INTEGER PRIMARY KEY, Descricao TEXT NOT NULL, Observacao TEXT);";
        db.execSQL(sqlCategoria);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS pessoa";
        db.execSQL(sql);
        onCreate(db);
    }

    //endregion

    //region Public Methods

    public void Inserir(T entity){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ObterNomeTabela(), null, MontarDadosInserir(entity));
    }

    public void Editar(T entity){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();

        String[] parametros = {entity.getId().toString()};

        PreencherValoresAtualizacao(valores, entity);

        db.update(ObterNomeTabela(), valores,"Id = ?", parametros);
    }

    public void Remover(T entity){
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {entity.getId().toString()};
        db.delete(ObterNomeTabela(), "Id = ?", parametros);
    }

    public List<T> Listar(){
        String sql = "SELECT * FROM " + ObterNomeTabela();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<T> lista = new ArrayList<T>();

        while(cursor.moveToNext()) {
            lista.add(CriarRegistroListagem(cursor));
        }

        cursor.close();

        return lista;
    }

    //endregion
}