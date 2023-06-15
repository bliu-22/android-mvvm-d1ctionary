package com.example.d1ctionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.recyclerviews.SearchResultRecyclerViewAdaptor;

import com.example.d1ctionary.util.Resource;
import com.example.d1ctionary.viewmodels.WordSearchViewModel;


import java.util.List;



public class SearchWordActivity extends AppCompatActivity {

    private static final String TAG = "SearchWordActivity";


    private WordSearchViewModel wordSearchViewModel;
    private SearchResultRecyclerViewAdaptor searchResultRecyclerViewAdaptor;
    private RecyclerView searchResultRecyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);
        getSupportActionBar().hide();
        searchResultRecyclerView = findViewById(R.id.search_result_recyclerview);

        initRecyclerView();
        initSearchView();
        wordSearchViewModel = new ViewModelProvider(this).get(WordSearchViewModel.class);






        wordSearchViewModel.getListWordPageData().observe(this, new Observer<Resource<List<WordPageInfo>>>() {
            @Override
            public void onChanged(Resource<List<WordPageInfo>> listResource) {
                if(listResource != null){
                    Log.d(TAG, "onChanged:  status: " + listResource.status);

                    if(listResource.data != null){
                        Log.d(TAG, "onChanged:  listResource.data != NULL");
                        Log.d(TAG, "onChanged:  response size: " + listResource.data.size());

                        searchResultRecyclerViewAdaptor.setListWordPageInfo(listResource.data);

                    }
                }
            }
        });


        searchResultRecyclerViewAdaptor.setOnItemClickListener(new SearchResultRecyclerViewAdaptor.OnResultClickListener() {
            @Override
            public void onResultCLick(WordPageInfo wordPageInfo) {
                Intent intent = new Intent(SearchWordActivity.this, WordPageActivity.class);
//                intent.putExtra("wordPageInfo", wordPageInfo);
                intent.putExtra("wordId", wordPageInfo.getWordId());
                startActivity(intent);
            }
        });





    }



    private void initSearchView(){
        searchView = findViewById(R.id.search_view_bar);
        searchView.setIconified(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                wordSearchViewModel.searchWordApiTest(query, false);
                Log.d(TAG, "onQueryTextSubmit:  word entered: " + query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != ""){
                    wordSearchViewModel.searchWordApiTest(newText, true);
                    Log.d(TAG, "onQueryTextChange:  word entered: " + newText);
                }

                return false;
            }
        });
    }

    private void initRecyclerView(){

        searchResultRecyclerViewAdaptor = new SearchResultRecyclerViewAdaptor();
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultRecyclerView.setAdapter(searchResultRecyclerViewAdaptor);
        searchResultRecyclerView.setHasFixedSize(true);

    }

//    public void searchDictionaryApi(String wordEntered){
//        wordSearchViewModel.searchDictionaryApi(wordEntered);
//    }

//    private void testApi(){
//
//        DictionaryApi dictionaryApi = RequestManager.getDictionaryApi();
//
//        Call<List<WordResponse>> responseCall = dictionaryApi.getWordInfo("nice");
//        responseCall.enqueue(new Callback<List<WordResponse>>() {
//            @Override
//            public void onResponse(Call<List<WordResponse>> call, Response<List<WordResponse>> response) {
//                //successful call
//                Log.d(TAG, "on Response: " + response.code());
//
//                if(response.code() == 200){
//                    List<Word> words = new ArrayList<>();
//                    //Log.d(TAG, "on Response: " + response.body().get(0).getWord().getTitle());
////                    txtTest.setText(response.body().get(0).toString());
//                    for(WordResponse word : response.body()){
//                        Log.d(TAG, "on Response: " + word.getWord().getTitle());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<WordResponse>> call, Throwable t) {
//                    Log.d(TAG, "on Failure: ");
//            }
//        });
//
//    }
}