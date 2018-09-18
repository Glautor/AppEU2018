package br.ufc.prograd.cgpa.euufc;

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

    @Query("SELECT * FROM resumo WHERE titulo LIKE :titulo LIMIT 1")
    Resumo findByName(String titulo);

    @Insert
    void insertAll(Resumo... resumos);

    @Delete
    void delete(Resumo resumo);
}
