package com.unoesc.trabalhofinal.ui.entrada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.unoesc.trabalhofinal.R;
import com.unoesc.trabalhofinal.dao.CategoriaDAO;
import com.unoesc.trabalhofinal.dao.EntradaDAO;
import com.unoesc.trabalhofinal.models.Categoria;
import com.unoesc.trabalhofinal.models.Entrada;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {
    private Spinner input_categoria;
    private EditText input_data;
    private EditText input_valor;
    private EditText input_observacao;
    private Button botao_salvar;
    private Entrada dado;
    private List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_entrada);

        input_categoria = findViewById(R.id.inputCategoria);
        input_data = findViewById(R.id.inputData);
        input_valor = findViewById(R.id.inputValor);
        input_observacao = findViewById(R.id.inputObservacao);
        botao_salvar = findViewById(R.id.botaoSalvar);

        CategoriaDAO daoCategoria = new CategoriaDAO(CadastroActivity.this);

        categorias = daoCategoria.Listar();

        input_categoria.setAdapter(new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categorias));

        dado = new Entrada();

        Intent intent = getIntent();

        Entrada dadoEdicao = (Entrada) intent.getSerializableExtra("Entrada");

        if(dadoEdicao != null) {

            input_categoria.setSelection(obterPositionListItemSelectedById(dadoEdicao.getId()));
            input_data.setText(dadoEdicao.getData());
            input_valor.setText(String.valueOf(dadoEdicao.getValor()));
            input_observacao.setText(dadoEdicao.getObservacao());

            this.dado = dadoEdicao;
            botao_salvar.setText("Atualizar");
        }

        botao_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categoria categoriaSelecionada = (Categoria) categorias.get(input_categoria.getSelectedItemPosition());

                dado.setCategoriaId(categoriaSelecionada.getId());
                dado.setData(input_data.getText().toString());
                dado.setValor(Float.valueOf(input_valor.getText().toString()));
                dado.setObservacao(input_observacao.getText().toString());

                EntradaDAO dao = new EntradaDAO(CadastroActivity.this);

                if(dado.getId() != null){
                    dao.Editar(dado);
                }else {
                    dao.Inserir(dado);
                }

                finish();
            }
        });
    }

    private int obterPositionListItemSelectedById(Long id){
        int i = 0;
        for (Categoria categoria: categorias) {
            if(((long) categoria.getId() == (long) id))
                return i;
            i ++;
        }

        return i;
    }
}
