package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

import java.util.Date;
import java.util.List;

@Dao
public interface CheckDao {
    @Query("SELECT * FROM `check`")
    List<Check> getAll();

    @Query("SELECT * FROM `check` WHERE user_id IN (:userId)")
    Check loadById(int userId);

    @Query("SELECT * FROM `check` WHERE user_id IN (:userId)")
    List<Check> loadAllByIds(int[] userId);

    @Query("SELECT `id_check` FROM `check` WHERE `dHourIn` = (:hIn)")
    int loadIdByHourIn(Date hIn);

    @Query("SELECT * FROM `check` WHERE atServidor IN (:ser) ORDER BY `dHourIn`")
    List<Check> loadAllByAtServdor(boolean ser);

    @Query("UPDATE `check` SET `dHourOut` = (:hOut) WHERE `id_check` = (:cId)")
    int updateCheckOut(Date hOut,int cId);

    @Insert
    void insertAll(Check... checks);

    @Delete
    void delete(Check check);
}
