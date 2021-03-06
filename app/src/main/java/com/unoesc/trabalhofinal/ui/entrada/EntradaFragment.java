package com.unoesc.trabalhofinal.ui.entrada;

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
import com.unoesc.trabalhofinal.dao.EntradaDAO;
import com.unoesc.trabalhofinal.models.Entrada;

import java.util.List;

public class EntradaFragment extends Fragment {
    private Button botao_cadastrar;
    private ListView lista;
    private TextView text_list_empty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_entrada, container, false);

        lista = root.findViewById(R.id.entradaList);
        text_list_empty = root.findViewById(R.id.textListEmpty);
        botao_cadastrar = root.findViewById(R.id.btnCadastrar);

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirCadastro = new Intent(getActivity(), com.unoesc.trabalhofinal.ui.entrada.CadastroActivity.class);
                startActivity(abrirCadastro);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entrada dado = (Entrada) lista.getItemAtPosition(position);
                Intent abrirEdicao = new Intent(getContext(), CadastroActivity.class);
                abrirEdicao.putExtra("Entrada", dado);
                startActivity(abrirEdicao);
            }
        });

        registerForContextMenu(lista);

        return root;
    }


    public void listarEntradas(){
        EntradaDAO dao = new EntradaDAO(getContext());
        List<Entrada> entrada = dao.Listar();
        ArrayAdapter<Entrada> adapter = new ArrayAdapter<Entrada>(getContext(), android.R.layout.simple_list_item_1, entrada);

        if(entrada.isEmpty())
            text_list_empty.setText("N??o h?? nenhuma entrada cadastrada");
        else
            text_list_empty.setText("");

        lista.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        listarEntradas();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo dados = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Entrada dado = (Entrada) lista.getItemAtPosition(dados.position);

                EntradaDAO dao = new EntradaDAO(getContext());
                dao.Remover(dado);
                listarEntradas();

                Toast.makeText(getContext(), "Deletado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}