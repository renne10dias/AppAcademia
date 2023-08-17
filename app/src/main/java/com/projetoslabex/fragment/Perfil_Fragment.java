package com.projetoslabex.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projetoslabex.academia.MainActivity;
import com.projetoslabex.academia.R;
import com.projetoslabex.academia.TelaLoginActivity;
import com.projetoslabex.api.ApiService;
import com.projetoslabex.model.ClienteTeste;
import com.projetoslabex.model.LoginResponse;
import com.projetoslabex.model.Logout;
import com.projetoslabex.model.UsuarioLogadoResponse;
import com.projetoslabex.retrofit.UrlWebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Perfil_Fragment extends Fragment {

    private Button buttonSave;
    ApiService apiService;

    private TextView textNomeLogin;
    private TextView textCpfLogin;
    private TextView textUsuarioTypeLogin;

    // ... Outros métodos e variáveis existentes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_, container, false);



        buttonSave = view.findViewById(R.id.buttonLogout);

        textNomeLogin = view.findViewById(R.id.textNomeLogin);
        textCpfLogin = view.findViewById(R.id.textCpfLogin);
        textUsuarioTypeLogin = view.findViewById(R.id.textUsuarioTypeLogin);

        // Obter a instância do serviço do RetrofitClient
        // Instancia global do retrofit
        apiService = UrlWebService.globalRetrofit();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String tokenLogin = preferences.getString("token", "");

        dadosUsuarioLogado(tokenLogin);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog(tokenLogin);
            }
        });

        return view;
    }


    private void showLogoutConfirmationDialog(String tokenLogin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Tem certeza de que deseja fazer logout?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realizarLogout(tokenLogin);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void realizarLogout(String token) {
        Call<Logout> call = apiService.logout(token);
        call.enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(Call<Logout> call, Response<Logout> response) {
                if (response.isSuccessful()) {
                    Logout logout = response.body();
                    boolean logoutResponse = logout.getResponse();

                    if(logoutResponse){
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.apply();
                        // chamo a tela de login
                        Intent intent = new Intent(getActivity(), TelaLoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Logout realizado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("ListarPessoas", "Erro ao listar pessoas: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Logout> call, Throwable t) {
                Log.d("ListarPessoas", "Erro ao listar pessoas: " + t.getMessage());
                Toast.makeText(getActivity(), "Falha de conexão com a internet", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void dadosUsuarioLogado(String token) {
        Call<ResponseBody> call = apiService.dadosUsuarioLogado(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        String nome = jsonObject.optString("nome");
                        String cpf = jsonObject.optString("cpf");
                        String usuarioType = jsonObject.optString("usuario_type");

                        textNomeLogin.setText("Nome: " + nome);
                        textCpfLogin.setText("CPF: " + cpf);

                        String tipoUsuarioExibicao;
                        if (usuarioType.equals("D")) {
                            tipoUsuarioExibicao = "Dono";
                        } else if (usuarioType.equals("F")) {
                            tipoUsuarioExibicao = "Funcionário";
                        } else {
                            tipoUsuarioExibicao = "Tipo de usuário desconhecido";
                        }

                        textUsuarioTypeLogin.setText("Tipo de Usuário: " + tipoUsuarioExibicao);


                    } catch (IOException | JSONException e) {
                        Log.d("ListarPessoas", "Erro ao processar resposta: " + e.getMessage());
                    }
                } else {
                    Log.d("ListarPessoas", "Erro ao listar pessoas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ListarPessoas", "Erro ao listar pessoas: " + t.getMessage());
            }
        });
    }





}