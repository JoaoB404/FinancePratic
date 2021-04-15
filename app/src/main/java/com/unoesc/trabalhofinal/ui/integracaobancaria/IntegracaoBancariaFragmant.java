package com.unoesc.trabalhofinal.ui.integracaobancaria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.unoesc.trabalhofinal.R;

public class IntegracaoBancariaFragmant extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragmanet_integracao_bancaria, container, false);

        return root;
    }
}
