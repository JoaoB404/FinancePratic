package com.unoesc.trabalhofinal.ui.categoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.unoesc.trabalhofinal.R;
import com.unoesc.trabalhofinal.dao.CategoriaDAO;
import com.unoesc.trabalhofinal.models.Categoria;

public class CadastroActivity extends AppCompatActivity {
    private EditText input_descricao;
    private EditText input_observacao;
    private Button botao_salvar;
    private Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_categoria);

        input_descricao = findViewById(R.id.inputDescricao);
        input_observacao = findViewById(R.id.inputObservacao);
        botao_salvar = findViewById(R.id.botaoSalvar);

        categoria = new Categoria();

        Intent intent = getIntent();

        Categoria categoriaEdicao = (Categoria) intent.getSerializableExtra("Categoria");

        if(categoriaEdicao != null) {
          input_descricao.setText(categoriaEdicao.getDescricao());
          input_observacao.setText(categoriaEdicao.getObservacao());
          this.categoria = categoriaEdicao;
          botao_salvar.setText("Atualizar");
        }

        botao_salvar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 categoria.setDescricao(input_descricao.getText().toString());
                 categoria.setObservacao(input_observacao.getText().toString());
                  CategoriaDAO dao = new CategoriaDAO(CadastroActivity.this);
                  if(categoria.getId() != null){
                      dao.Editar(categoria);
                  }else {
                      dao.Inserir(categoria);
                   }
              finish();
            }
        });
    }
}
