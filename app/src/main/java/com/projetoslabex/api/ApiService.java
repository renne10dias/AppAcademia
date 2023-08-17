package com.projetoslabex.api;

import com.projetoslabex.model.ClienteTeste;
import com.projetoslabex.model.LoginRequest;
import com.projetoslabex.model.LoginResponse;
import com.projetoslabex.model.Logout;
import com.projetoslabex.model.UsuarioLogadoResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login3.php")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("Logout.php")
    Call<Logout> logout(@Header("Authorization") String token);

    //@GET("Logout.php")
    //Call<UsuarioLogadoResponse> dadosUsuarioLogado(@Header("Authorization") String token);

    @GET("dadosUsuarioLogado.php")
    Call<ResponseBody> dadosUsuarioLogado(@Header("Authorization") String token);




    @GET("Clientes.php")
    Call<List<ClienteTeste>> listarCLiente(@Header("Authorization") String token);



}
