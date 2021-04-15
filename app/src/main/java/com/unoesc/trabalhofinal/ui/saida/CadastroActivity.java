package com.unoesc.trabalhofinal.ui.saida;

import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.Activity;

import java.io.File;


import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unoesc.trabalhofinal.BuildConfig;
import com.unoesc.trabalhofinal.R;
import com.unoesc.trabalhofinal.dao.CategoriaDAO;
import com.unoesc.trabalhofinal.dao.SaidaDAO;
import com.unoesc.trabalhofinal.models.Categoria;
import com.unoesc.trabalhofinal.models.Saida;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {
    private Spinner input_categoria;
    private EditText input_data;
    private EditText input_valor;
    private EditText input_observacao;
    private Button botao_salvar;
    private Button botao_comprovante;
    private int codigo_camera = 100;
    private String caminho_comprovante;
    private Saida dado;
    private List<Categoria> categorias;
    private ImageView campo_comprovante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_saida);

        input_categoria = findViewById(R.id.inputCategoria);
        input_data = findViewById(R.id.inputData);
        input_valor = findViewById(R.id.inputValor);
        input_observacao = findViewById(R.id.inputObservacao);
        botao_salvar = findViewById(R.id.botaoSalvar);
        botao_comprovante = findViewById(R.id.botaoComprovante);
        campo_comprovante = findViewById(R.id.caminhoComprovante);

        CategoriaDAO daoCategoria = new CategoriaDAO(CadastroActivity.this);

        categorias = daoCategoria.Listar();

        input_categoria.setAdapter(new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categorias));

        dado = new Saida();

        Intent intent = getIntent();

        Saida dadoEdicao = (Saida) intent.getSerializableExtra("Saida");

        if(dadoEdicao != null) {
            input_categoria.setSelection(obterPositionListItemSelectedById(dadoEdicao.getId()));
            input_data.setText(dadoEdicao.getData());
            input_valor.setText(String.valueOf(dadoEdicao.getValor()));
            input_observacao.setText(dadoEdicao.getObservacao());

            this.dado = dadoEdicao;
            botao_salvar.setText("Atualizar");

            if(dadoEdicao.getCaminhoComprovante() != null && dadoEdicao.getCaminhoComprovante() != ""){
                Bitmap bitmap = BitmapFactory.decodeFile(dadoEdicao.getCaminhoComprovante());
                Bitmap miniatura = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                campo_comprovante.setImageBitmap(miniatura);
                campo_comprovante.setTag(dadoEdicao.getCaminhoComprovante());
            }

        }

        botao_comprovante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminho_comprovante = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File foto = new File(caminho_comprovante);
                camera.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(CadastroActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                foto));
                startActivityForResult(camera, codigo_camera);
            }
        });

        botao_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categoria categoriaSelecionada = (Categoria) categorias.get(input_categoria.getSelectedItemPosition());

                dado.setCategoriaId(categoriaSelecionada.getId());

                if(campo_comprovante.getTag() != null)
                    dado.setCaminhoComprovante(campo_comprovante.getTag().toString());

                dado.setData(input_data.getText().toString());
                dado.setValor(Float.valueOf(input_valor.getText().toString()));
                dado.setObservacao(input_observacao.getText().toString());

                SaidaDAO dao = new SaidaDAO(CadastroActivity.this);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == codigo_camera) {
                ImageView foto = findViewById(R.id.caminhoComprovante);
                Bitmap bitmap = BitmapFactory.decodeFile(caminho_comprovante);
                Bitmap miniatura = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                foto.setImageBitmap(miniatura);
                foto.setTag(caminho_comprovante);
            }
        }
    }
}
