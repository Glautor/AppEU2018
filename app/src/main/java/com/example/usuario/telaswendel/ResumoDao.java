package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface ResumoDao {
    @Query("SELECT * FROM resumo")
    List<Resumo> getAll();

    @Query("SELECT * FROM resumo WHERE id IN (:resumoIds)")
    List<Resumo> loadAllByIds(int[] resumoIds);


    @Query("SELECT * FROM resumo WHERE id LIKE :id LIMIT 1 ")
    Resumo findById(int id);

    @Insert
    void insertAll(Resumo... resumos);

    @Delete
    void delete(Resumo resumo);
}
