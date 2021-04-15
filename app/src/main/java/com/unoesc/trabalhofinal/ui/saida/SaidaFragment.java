package com.unoesc.trabalhofinal.ui.saida;

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

import com.unoesc.trabalhofinal.R;
import com.unoesc.trabalhofinal.dao.SaidaDAO;
import com.unoesc.trabalhofinal.models.Saida;

import java.util.List;

public class SaidaFragment extends Fragment {
    private Button botao_cadastrar;
    private ListView lista;
    private TextView text_list_empty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saida, container, false);

        lista = root.findViewById(R.id.saidaList);
        text_list_empty = root.findViewById(R.id.textListEmpty);
        botao_cadastrar = root.findViewById(R.id.btnCadastrar);

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirCadastro = new Intent(getActivity(), CadastroActivity.class);
                startActivity(abrirCadastro);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Saida dado = (Saida) lista.getItemAtPosition(position);
                Intent abrirEdicao = new Intent(getContext(), CadastroActivity.class);
                abrirEdicao.putExtra("Saida", dado);
                startActivity(abrirEdicao);
            }
        });

        registerForContextMenu(lista);

        return root;
    }

    public void listarEntradas(){
        SaidaDAO dao = new SaidaDAO(getContext());
        List<Saida> entrada = dao.Listar();
        ArrayAdapter<Saida> adapter = new ArrayAdapter<Saida>(getContext(), android.R.layout.simple_list_item_1, entrada);

        if(entrada.isEmpty())
            text_list_empty.setText("Não há nenhuma saída cadastrada");
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

                Saida dado = (Saida) lista.getItemAtPosition(dados.position);

                SaidaDAO dao = new SaidaDAO(getContext());
                dao.Remover(dado);
                listarEntradas();

                Toast.makeText(getContext(), "Deletado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
