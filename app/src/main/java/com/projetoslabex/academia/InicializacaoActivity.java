package com.projetoslabex.academia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

public class InicializacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verificarAutenticacaoEIniciarAtividade();
    }

    private void verificarAutenticacaoEIniciarAtividade() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("token", "");

        Intent intent;
        if (!TextUtils.isEmpty(token)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, TelaLoginActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}


