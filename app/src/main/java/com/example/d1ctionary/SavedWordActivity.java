package com.example.d1ctionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.recyclerviews.SavedWordRecyclerViewAdaptor;
import com.example.d1ctionary.viewmodels.SavedWordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SavedWordActivity extends AppCompatActivity {

    private static final String TAG = "SavedWordActivity";
    private FloatingActionButton fab;
    private SavedWordViewModel savedWordViewModel;
    private RecyclerView savedWordRecyclerview;
    private SavedWordRecyclerViewAdaptor savedWordRecyclerViewAdaptor;
    private LiveData<List<WordPageInfo>> listSavedWords;
    int optionSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_word);
        getSupportActionBar().setTitle("Saved Words");
        savedWordViewModel = new ViewModelProvider(this).get(SavedWordViewModel.class);

        savedWordRecyclerview = findViewById(R.id.saved_word_recyclerview);
        initRecyclerView();

        fab = findViewById(R.id.fab_search);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedWordActivity.this, SearchWordActivity.class);
                startActivity(intent);
            }
        });

        savedWordViewModel.getListSavedWords().observe(this, new Observer<List<WordPageInfo>>() {
            @Override
            public void onChanged(List<WordPageInfo> wordPageInfos) {
                if(wordPageInfos != null){
                    savedWordRecyclerViewAdaptor.setListSavedWords(wordPageInfos);
                    //Log.d(TAG, "onChanged: saved word: priority " + wordPageInfos.get(0).getPriorityLevel());
                }

            }
        });


        savedWordRecyclerViewAdaptor.setOnItemClickListener(new SavedWordRecyclerViewAdaptor.OnSavedWordClickListener() {
            @Override
            public void onSavedWordCLick(WordPageInfo wordPageInfo) {
                Intent intent = new Intent(SavedWordActivity.this, WordPageActivity.class);
                //intent.putExtra("wordPageInfo", wordPageInfo);
                intent.putExtra("wordId", wordPageInfo.getWordId());
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                savedWordViewModel.removeBookmark(savedWordRecyclerViewAdaptor.getWordPageInfoAt(viewHolder.getAbsoluteAdapterPosition()));
            }
        }).attachToRecyclerView(savedWordRecyclerview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "delete clicked",Toast.LENGTH_SHORT).show();

        showDialog();

        return true;

    }

    private void showDialog(){

        String[] options = {"Delete Cache", "Delete Bookmarks"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SavedWordActivity.this);
        builder.setTitle("Select what to delete");
        builder.setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                optionSelected = which;
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (optionSelected){
                    case 0:
                        savedWordViewModel.deleteCachedWords();
                        //Toast.makeText(SavedWordActivity.this, "00000",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //Toast.makeText(SavedWordActivity.this, "1111",Toast.LENGTH_SHORT).show();
                        savedWordViewModel.deleteAllSavedWords();
                        break;
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

        private void initRecyclerView(){
//        wordInfoRecyclerViewAdaptor = new WordInfoRecyclerViewAdaptor();
//        wordInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        wordInfoRecyclerView.setAdapter(wordInfoRecyclerViewAdaptor);
//        wordInfoRecyclerView.setHasFixedSize(true);

        savedWordRecyclerViewAdaptor = new SavedWordRecyclerViewAdaptor();
        savedWordRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        savedWordRecyclerview.setAdapter(savedWordRecyclerViewAdaptor);
        savedWordRecyclerview.setHasFixedSize(true);

    }
}