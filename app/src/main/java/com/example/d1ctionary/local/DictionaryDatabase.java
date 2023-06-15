package com.example.d1ctionary.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.util.WordTypeConverters;

@Database(entities = {WordPageInfo.class}, version = 1)
@TypeConverters({WordTypeConverters.class})
public abstract class DictionaryDatabase extends RoomDatabase {

    public abstract DictionaryDao dictionaryDao();

    public static final String DATABASE_NAME = "dictionary_db";
    private static DictionaryDatabase instance;
    public static DictionaryDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DictionaryDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        }
        return instance;
    }
}
