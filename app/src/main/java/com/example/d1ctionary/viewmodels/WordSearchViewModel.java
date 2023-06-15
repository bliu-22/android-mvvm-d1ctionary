package com.example.d1ctionary.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.d1ctionary.models.Word;
import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.repositories.WordRepository;
import com.example.d1ctionary.util.Resource;

import java.util.List;

public class WordSearchViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private MediatorLiveData<Resource<List<WordPageInfo>>> wordPageData = new MediatorLiveData<>();

    public WordSearchViewModel(Application application) {
        super(application);
        wordRepository = WordRepository.getInstance(application);

    }

//    public LiveData<List<Word>> getWordInfo(){
//        return wordRepository.getWordInfo();
//    }

    public LiveData<Resource<List<WordPageInfo>>> getListWordPageData(){
        return wordPageData;
    }

//    public void searchDictionaryApi(String wordEntered){
//        wordRepository.searchDictionaryApi(wordEntered);
//    }

    public void searchWordApiTest(String wordEntered, boolean onQueryTextUpdate){
        final LiveData<Resource<List<WordPageInfo>>> repositorySource = wordRepository.searchWord(wordEntered, onQueryTextUpdate);
        wordPageData.addSource(repositorySource, new Observer<Resource<List<WordPageInfo>>>() {
            @Override
            public void onChanged(Resource<List<WordPageInfo>> listResource) {
                wordPageData.setValue(listResource);
            }
        });

    }
}
