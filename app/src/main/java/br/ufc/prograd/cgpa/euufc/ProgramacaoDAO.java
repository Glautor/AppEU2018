package br.ufc.prograd.cgpa.euufc;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProgramacaoDAO {

    @Query("SELECT * FROM programacao")
    List<Programacao> getAll();

    @Query("SELECT * FROM programacao WHERE id IN (:programacaoIds)")
    List<Programacao> loadAllByIds(int[] programacaoIds);

    @Query("SELECT * FROM programacao WHERE id LIKE :id LIMIT 1")
    Programacao findByProgramacaoId(int id);

    @Insert
    void insertAll(Programacao... programacoes);

    @Delete
    void delete(Programacao programacao);

}
