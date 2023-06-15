package com.example.d1ctionary.requests;

import androidx.lifecycle.LiveData;

import com.example.d1ctionary.models.Word;
import com.example.d1ctionary.requests.responses.ApiResponse;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {

    @GET("entries/en/{word}")

    LiveData<ApiResponse<List<Word>>> getWordInfo(@Path("word") String word);

}
