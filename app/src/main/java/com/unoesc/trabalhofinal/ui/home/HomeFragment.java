package com.unoesc.trabalhofinal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.unoesc.trabalhofinal.R;
import com.unoesc.trabalhofinal.dao.EntradaDAO;
import com.unoesc.trabalhofinal.dao.SaidaDAO;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView totalEntradas = root.findViewById(R.id.totalEntradas);
        final TextView totalSaidas = root.findViewById(R.id.totalSaida);
        final TextView saldoConta = root.findViewById(R.id.saldoConta);

        EntradaDAO entradaDAO = new EntradaDAO(getContext());
        SaidaDAO saidaDAO = new SaidaDAO(getContext());

        float valorEntradas =  entradaDAO.ObterTotalEntradas();
        float valorSaidas =  saidaDAO.ObterTotalSaidas();
        float saldo = valorEntradas - valorSaidas;

        totalEntradas.setText(String.valueOf(valorEntradas));
        totalSaidas.setText(String.valueOf(valorSaidas));
        saldoConta.setText(String.valueOf(saldo));

        return root;
    }
}