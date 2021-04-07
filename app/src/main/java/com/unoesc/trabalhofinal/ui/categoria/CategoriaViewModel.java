package com.unoesc.trabalhofinal.ui.categoria;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoriaViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Button botao_cadastrar;

    public CategoriaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}