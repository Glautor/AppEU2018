package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE nome LIKE :nome LIMIT 1 ")
    User findByName(String nome);

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1 ")
    User findById(int id);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

}

Glauton