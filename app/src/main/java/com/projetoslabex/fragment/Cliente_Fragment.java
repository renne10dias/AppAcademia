package com.projetoslabex.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.projetoslabex.academia.R;
import com.projetoslabex.adapter.AdapterCliente;
import com.projetoslabex.api.ApiService;
import com.projetoslabex.model.ClienteTeste;
import com.projetoslabex.retrofit.UrlWebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cliente_Fragment extends Fragment {
    private FloatingActionButton floatingCadastrar;
    private RecyclerView recyclerClientes;
    private AdapterCliente adapterCliente;
    private SearchView searchCliente;
    private List<ClienteTeste> listaCompletaClientes = new ArrayList<>();
    List<ClienteTeste> clientes = new ArrayList<>();
    ApiService apiService;

    //private static final long SCREEN_DELAY = 1000;

   // private String token = ""; // Variável para armazenar o token


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente_, container, false);

        recyclerClientes = view.findViewById(R.id.recycleClientes);
        floatingCadastrar = view.findViewById(R.id.add_cliente);

        // Obter a instância do serviço do RetrofitClient
        // Instancia global do retrofit
        apiService = UrlWebService.globalRetrofit();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String tokenLogin = preferences.getString("token", "");

        if(!(tokenLogin == null)){
            try {
                listarClientes(tokenLogin);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }


        // Configurar o RecyclerView
        recyclerClientes.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerClientes.setHasFixedSize(true);
        adapterCliente = new AdapterCliente(clientes, getActivity());
        recyclerClientes.setAdapter(adapterCliente);

        // Configurar o SearchView
        searchCliente = view.findViewById(R.id.searchCliente);
        searchCliente.clearFocus();
        searchCliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        }




        );




        /*
        floatingCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarTransicaoDeTelaCadastro();
            }
        });

         */

        return view;
    }







    // Método para filtrar os itens do RecyclerView com base no texto de busca
    private void filter(String query) {
        List<ClienteTeste> filteredList = new ArrayList<>();
        for (ClienteTeste cliente : listaCompletaClientes) {
            if (cliente.getNome().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(cliente);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getActivity(), "A lista esta vazia", Toast.LENGTH_SHORT).show();
        }else{
            adapterCliente.setClientes(filteredList);
        }

    }

    /*
    private void realizarTransicaoDeTelaCadastro() {
        // Crie um Intent para a nova Activity que você deseja abrir
        Intent intent = new Intent(getActivity(), TelaCadastroActivity.class);

        // Inicie a nova Activity
        startActivity(intent);
    }

     */



    private void listarClientes(String token) throws JSONException {

        Call<List<ClienteTeste>> call = apiService.listarCLiente(token);
        call.enqueue(new Callback<List<ClienteTeste>>() {
            @Override
            public void onResponse(Call<List<ClienteTeste>> call, Response<List<ClienteTeste>> response) {
                if (response.isSuccessful()) {
                    clientes.clear();
                    clientes.addAll(response.body());
                    listaCompletaClientes = clientes;
                    adapterCliente.notifyDataSetChanged();
                } else {
                    Log.d("ListarPessoas", "Erro ao listar pessoas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ClienteTeste>> call, Throwable t) {
                // Mostrar um Snackbar com a mensagem de erro
                View view = getView(); // Obtém a referência à view do Fragment
                if (view != null) {
                    Snackbar.make(view, "Erro ao carregar os dados. Verifique sua conexão com a internet.", Snackbar.LENGTH_LONG).show();
                }
                Log.d("ListarPessoas", "Erro ao listar pessoas: " + t.getMessage());
            }
        });
    }


}