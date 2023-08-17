package com.projetoslabex.retrofit;

import com.projetoslabex.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UrlWebService {

    //private static final String BASE_URL = "http://192.168.26.80/projetoAcademia/WebServiceAppAcademiaLoginToken/";
    private static final String BASE_URL = "http://10.0.0.50/projetoAcademia/Teste3/";
    private static Retrofit retrofit;


    // RESPONSAVEL PELAS REQUISEÇÕES DO USUARIO
    public static ApiService globalRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}
