package com.projetoslabex.academia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projetoslabex.api.ApiService;
import com.projetoslabex.model.LoginRequest;
import com.projetoslabex.model.LoginResponse;
import com.projetoslabex.retrofit.UrlWebService;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaLoginActivity extends AppCompatActivity {

    private EditText campoNome, campoSenha;
    private Button buttonFazerLogin;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        inicializarComponentes();

        // Obter a instância do serviço do RetrofitClient
        // Instancia global do retrofit
        apiService = UrlWebService.globalRetrofit();




        buttonFazerLogin.setOnClickListener(v -> {
            String nome = campoNome.getText().toString();
            String cpf = campoSenha.getText().toString();


            if (TextUtils.isEmpty(nome)) {
                campoNome.setError("Preencha o campo nome");
            } else if (TextUtils.isEmpty(cpf)) {
                campoSenha.setError("Preencha o campo CPF");
            }     else {
                try {
                    realizarLogin();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        });


    } // Fim do onCreate


    private void inicializarComponentes() {
        campoNome = findViewById(R.id.nomeCampoLogin);
        campoSenha = findViewById(R.id.senhaCampoLogin);
        buttonFazerLogin = findViewById(R.id.buttonLogin);
    }


    private void realizarLogin() throws JSONException {

        String nome = campoNome.getText().toString();
        String senha = campoSenha.getText().toString();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(nome);
        loginRequest.setPassword(senha);

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // A chamada foi bem-sucedida, você pode acessar o token no objeto LoginResponse
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();
                    boolean apiResponse = loginResponse.getResponse();
                    int id = loginResponse.getId();

                    if (token != null) {
                        // A resposta contém um token, faça algo com ele
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TelaLoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putInt("id", id);
                        editor.apply();

                        // Crie um Intent para a nova Activity que você deseja abrir
                        Intent intent = new Intent(TelaLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (apiResponse) {
                        // A resposta é verdadeira (true), faça algo correspondente a isso
                        Toast.makeText(TelaLoginActivity.this, "Usuario Já está logado", Toast.LENGTH_SHORT).show();
                    } else {
                        // A resposta é falsa (false), faça algo correspondente a isso
                        Toast.makeText(TelaLoginActivity.this, "Nome ou senha invalido", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Toast.makeText(TelaLoginActivity.this, "Nome ou senha invalido", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Não foi possivel entrar. Verifique seu usuario e sua senha" + response.code());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(TelaLoginActivity.this, "Deu algum erro", Toast.LENGTH_SHORT).show();
            }
        });
    }



}