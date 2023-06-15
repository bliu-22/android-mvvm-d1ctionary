package com.example.d1ctionary.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.repositories.WordRepository;

import java.util.List;

public class SavedWordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private LiveData<List<WordPageInfo>> listSavedWords;

    public SavedWordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = WordRepository.getInstance(application);

    }

    public void removeBookmark(WordPageInfo wordToDelete){
        wordRepository.removeBookmark(wordToDelete);
    }

    public void deleteCachedWords(){
        wordRepository.deleteCachedWords();
    }

    public void deleteAllSavedWords(){
        wordRepository.deleteAllSavedWords();
    }

    public LiveData<List<WordPageInfo>> getListSavedWords(){
        return wordRepository.getListSavedWords();
    }
}
