package com.example.d1ctionary.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.repositories.WordRepository;

public class WordPageViewModel extends AndroidViewModel {
    WordRepository wordRepository;

    public int getCurWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    int wordId;

    public WordPageViewModel(@NonNull Application application) {
        super(application);
        wordRepository = WordRepository.getInstance(application);
    }

    public LiveData<WordPageInfo> getWordById(int wordId){
        return wordRepository.getWordById(wordId);
    }


}
