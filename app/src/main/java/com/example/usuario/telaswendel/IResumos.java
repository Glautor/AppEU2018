package com.example.usuario.telaswendel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IResumos {
    @GET("buscarTodos")
    Call<List<Resumo>> buscarTodos();
}
