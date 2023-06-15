package com.example.d1ctionary.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.d1ctionary.AppExecutors;
import com.example.d1ctionary.local.DictionaryDao;
import com.example.d1ctionary.local.DictionaryDatabase;
import com.example.d1ctionary.models.Word;
import com.example.d1ctionary.models.WordPageInfo;

import com.example.d1ctionary.requests.RequestManager;
import com.example.d1ctionary.requests.responses.ApiResponse;
import com.example.d1ctionary.util.NetworkBoundResource;
import com.example.d1ctionary.util.Resource;

import java.util.List;

public class WordRepository {


    private DictionaryDao dictionaryDao;

    private static WordRepository instance;

    private LiveData<List<WordPageInfo>> listSavedWords;

    public static WordRepository getInstance(Context context){
        if(instance ==null){
            instance = new WordRepository(context);
        }
        return instance;
    }

    private WordRepository(Context context){
        dictionaryDao = DictionaryDatabase.getInstance(context).dictionaryDao();


    }



    public LiveData<WordPageInfo> getWordById(int wordId){
        return dictionaryDao.getWordById(wordId);
    }


    public LiveData<List<WordPageInfo>> getListSavedWords(){
        return dictionaryDao.getSavedWords();
    }

    public void removeBookmark(WordPageInfo wordToDelete){
        dictionaryDao.changeBookMark(wordToDelete.getWordId(), false);
    }

    public void deleteCachedWords(){
        dictionaryDao.deleteCachedWords();
    }

    public void deleteAllSavedWords(){
        dictionaryDao.deleteSavedWords();
    };


    public LiveData<Resource<List<WordPageInfo>>> searchWord(String wordEntered, boolean onQueryTextUpdate){
        return new NetworkBoundResource<List<WordPageInfo>, List<Word>>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull List<Word> item) {
                //Log.d("NetworkBoundResource", "saveCallResult: " + item.get(0).getTitle());
                WordPageInfo wordPageInfo = new WordPageInfo(item, false, 1, null);
                dictionaryDao.insert(wordPageInfo);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<WordPageInfo> data) {
                if(onQueryTextUpdate){
                    return false;
                }
                boolean ret = true;
                for (WordPageInfo word : data){
                    if(word.getWordTitle().equals(wordEntered)){
                        ret = false;
                    }
                }
                return ret;

            }

            @NonNull
            @Override
            protected LiveData<List<WordPageInfo>> loadFromDb() {
                Log.d( "NETWorkBoundResource", "loadFromDb: " + wordEntered);
                return dictionaryDao.searchDatabase(wordEntered);


            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Word>>> createCall() {

                return RequestManager.getDictionaryApi().getWordInfo(wordEntered);
            }
        }.getAsLiveData();
    }

}



