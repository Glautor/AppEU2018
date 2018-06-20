package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface CheckDao {
    @Query("SELECT * FROM `check`")
    List<Check> getAll();

    @Query("SELECT * FROM `check` WHERE user_id IN (:userId)")
    List<Check> loadAllByIds(int userId);


    @Insert
    void insertAll(Check... checks);

    @Delete
    void delete(Check check);
}
