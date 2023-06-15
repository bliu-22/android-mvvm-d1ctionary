package com.example.d1ctionary.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.Update;


import com.example.d1ctionary.models.Word;
import com.example.d1ctionary.models.WordPageInfo;

import java.util.List;

@Dao
public interface DictionaryDao {
    @Insert
    void insert(WordPageInfo wordPageInfo);

    @Delete
    void delete(WordPageInfo wordPageInfo);


    @Query("UPDATE word_table SET isBookmarked = :isBookMarked WHERE wordId =:wordId")
    void changeBookMark(int wordId, boolean isBookMarked);

    @Query("UPDATE word_table SET priorityLevel = :newPriorityLvl WHERE wordId = :wordId")
    void changePriorityLevel(int wordId, int newPriorityLvl);

    @Query("UPDATE word_table SET note = :noteToAdd WHERE wordId = :wordId")
    void changeNote(int wordId, String noteToAdd);

    @Query("SELECT * FROM word_table WHERE isBookmarked = 1 ORDER BY priorityLevel DESC")
    LiveData<List<WordPageInfo>> getSavedWords();

    @Query("SELECT * FROM word_table WHERE wordTitle LIKE :wordEntered || '%'")
    LiveData<List<WordPageInfo>> searchDatabase(String wordEntered);


    @Query("SELECT * FROM word_table WHERE wordId = :wordId")
    LiveData<WordPageInfo> getWordById(int wordId);

    @Query("SELECT EXISTS (SELECT * FROM word_table WHERE wordTitle = :wordEntered )")
    int checkIfExist(String wordEntered);

    @Query("DELETE FROM word_table")
    void deleteAllWords();

    @Query("UPDATE word_table SET isBookmarked = 0 WHERE isBookmarked = 1")
    void deleteSavedWords();

    @Query("DELETE FROM word_table WHERE isBookmarked = 0")
    void  deleteCachedWords();
}
