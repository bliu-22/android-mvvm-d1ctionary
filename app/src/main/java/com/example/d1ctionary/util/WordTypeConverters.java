package com.example.d1ctionary.util;

import androidx.room.TypeConverter;

import com.example.d1ctionary.models.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WordTypeConverters {
    @TypeConverter
    public static String fromList(List<Word> listWordInfo){
        Gson gson = new Gson();
        String json = gson.toJson(listWordInfo);
        return json;
    }

    @TypeConverter
    public static List<Word> fromList(String value){
        Type listType = new TypeToken<List<Word>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }
}
