package br.ufc.prograd.cgpa.euufc;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {User.class,Check.class,Resumo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CheckDao checkDao();
    public abstract ResumoDao resumoDao();
}


