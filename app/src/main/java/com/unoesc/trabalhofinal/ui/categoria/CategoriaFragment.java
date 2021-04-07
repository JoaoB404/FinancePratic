package com.unoesc.trabalhofinal.ui.categoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.unoesc.trabalhofinal.R;
import com.unoesc.trabalhofinal.dao.CategoriaDAO;
import com.unoesc.trabalhofinal.models.Categoria;

import java.util.List;

public class CategoriaFragment extends Fragment {

    private CategoriaViewModel galleryViewModel;
    private Button botao_cadastrar;
    private ListView lista;
    private TextView text_list_empty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(CategoriaViewModel.class);

        View root = inflater.inflate(R.layout.fragment_categoria, container, false);

        lista = root.findViewById(R.id.categoriaList);
        text_list_empty = root.findViewById(R.id.textListEmpty);
        botao_cadastrar = root.findViewById(R.id.btnCadastrar);

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirCadastro = new Intent(getActivity(), com.unoesc.trabalhofinal.ui.categoria.CadastroActivity.class);
                startActivity(abrirCadastro);
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) lista.getItemAtPosition(position);
                Intent abrirEdicao = new Intent(getContext(), CadastroActivity.class);
                abrirEdicao.putExtra("Categoria", categoria);
                startActivity(abrirEdicao);
            }
        });

        registerForContextMenu(lista);

        return root;
    }

    public void listarCategorias(){
        CategoriaDAO dao = new CategoriaDAO(getContext());
        List<Categoria> categorias = dao.Listar();
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getContext(), android.R.layout.simple_list_item_1, categorias);
        if(categorias.isEmpty())
            text_list_empty.setText("Não há nenhuma categoria cadastrada");
        else
            text_list_empty.setText("");

        lista.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        listarCategorias();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletarPessoa = menu.add("Deletar");
        deletarPessoa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo dados = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Categoria pessoa = (Categoria) lista.getItemAtPosition(dados.position);

                CategoriaDAO dao = new CategoriaDAO(getContext());
                dao.Remover(pessoa);
                listarCategorias();

                Toast.makeText(getContext(), "Deletado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}