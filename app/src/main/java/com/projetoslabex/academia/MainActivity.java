package com.projetoslabex.academia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projetoslabex.fragment.Carteira_Fragment;
import com.projetoslabex.fragment.Cliente_Fragment;
import com.projetoslabex.fragment.Funcionario_Fragment;
import com.projetoslabex.fragment.Perfil_Fragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        abrirFragment(new Cliente_Fragment()); // inicializa a tela do aplicativo já com o fragment cliente


        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.cliente){
                abrirFragment(new Cliente_Fragment());

            }else if (item.getItemId() == R.id.funcionarios) {
                abrirFragment(new Funcionario_Fragment());

            }else if (item.getItemId() == R.id.carteira) {
                abrirFragment(new Carteira_Fragment());

            }else if (item.getItemId() == R.id.perfil) {
                abrirFragment(new Perfil_Fragment());
            }
            return true;
        });


    }



    private void abrirFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPage, fragment); // id do nosso frame layout da tela principal
        fragmentTransaction.commit();
    }



}