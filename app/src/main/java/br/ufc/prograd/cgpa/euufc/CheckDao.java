package br.ufc.prograd.cgpa.euufc;

import android.arch.persistence.room.*;

import java.util.Date;
import java.util.List;

@Dao
public interface CheckDao {
    @Query("SELECT * FROM `check`")
    List<Check> getAll();

    @Query("SELECT * FROM `check` WHERE id_check IN (:checkId)")
    Check loadById(int checkId);

    @Query("SELECT * FROM `check` WHERE user_id IN (:userId)")
    List<Check> loadAllByIds(int[] userId);

    @Query("SELECT `id_check` FROM `check` WHERE `dHourIn` = (:hIn)")
    int loadIdByHourIn(Date hIn);

    @Query("SELECT * FROM `check` WHERE user_id == (:id) AND atServidor IN (:ser) ORDER BY `dHourIn`")
    List<Check> loadAllByAtServidor(int id,boolean ser);

    @Query("UPDATE `check` SET `dHourOut` = (:hOut) WHERE `id_check` = (:cId)")
    int updateCheckOut(Date hOut,int cId);

    @Query("UPDATE `check` SET `atServidor` = (:ser) WHERE `id_check` = (:cId)")
    int updateAtServidor(int cId, boolean ser);

    @Insert
    void insertAll(Check... checks);

    @Delete
    void delete(Check check);
}
