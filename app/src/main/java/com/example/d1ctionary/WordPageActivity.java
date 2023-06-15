package com.example.d1ctionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import android.media.AudioManager;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d1ctionary.local.DictionaryDao;
import com.example.d1ctionary.local.DictionaryDatabase;
import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.recyclerviews.WordInfoRecyclerViewAdaptor;
import com.example.d1ctionary.viewmodels.WordPageViewModel;
import com.google.android.material.tabs.TabLayout;

import java.net.URI;

public class WordPageActivity extends AppCompatActivity {
    private static final String TAG = "WordPageActivity";
    WordPageInfo wordPageInfoToDisplay;
    int wordId;
    TabLayout wordTabLayout;
    ViewPager2 wordViewPager;
    ViewPagerAdaptor viewPagerAdaptor;
    TextView wordPageTitle;
    TextView wordPagePhonetic;
    ImageView bookmarkIcon;
    ImageView audioIcon;
    TextView wordPagePriority;
    ImageView addPriority;
    ImageView minusPriority;
    RecyclerView wordInfoRecyclerView;
    WordInfoRecyclerViewAdaptor wordInfoRecyclerViewAdaptor;
    DictionaryDao dictionaryDao;
    WordPageViewModel wordPageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_page);
        wordViewPager = findViewById(R.id.word_viewpager);
        wordTabLayout = findViewById(R.id.word_tab_layout);
        viewPagerAdaptor = new ViewPagerAdaptor(this);
        wordViewPager.setAdapter(viewPagerAdaptor);

        wordPageTitle = findViewById(R.id.word_page_title);
        wordPagePhonetic = findViewById(R.id.word_page_phonetic);
        bookmarkIcon = findViewById(R.id.bookmark_icon);

        addPriority = findViewById(R.id.btn_priority_plus);
        minusPriority = findViewById(R.id.btn_priority_minus);
        wordPagePriority = findViewById(R.id.cur_priority_level);
        audioIcon = findViewById(R.id.word_page_phonetic_audio);

        wordPageViewModel = new ViewModelProvider(this).get(WordPageViewModel.class);
        dictionaryDao = DictionaryDatabase.getInstance(WordPageActivity.this).dictionaryDao();



        getSupportActionBar().hide();

        if(getIntent().hasExtra("wordPageInfo")){
            wordPageInfoToDisplay = (WordPageInfo) getIntent().getSerializableExtra("wordPageInfo");
            Log.d(TAG, "received extra: " + wordPageInfoToDisplay.getPriorityLevel());
            wordPageTitle.setText(wordPageInfoToDisplay.getWordTitle());
            wordPagePhonetic.setText(wordPageInfoToDisplay.getWordPhonetic());

            wordInfoRecyclerViewAdaptor.setListWordInfo(wordPageInfoToDisplay.getListWordInfo());

            if(wordPageInfoToDisplay.isBookmarked()){
                bookmarkIcon.setImageDrawable(ContextCompat.getDrawable(WordPageActivity.this, R.drawable.ic_baseline_bookmark_24));
            } else {
                bookmarkIcon.setImageDrawable(ContextCompat.getDrawable(WordPageActivity.this, R.drawable.ic_baseline_bookmark_border_24));
            }


        }

        if(getIntent().hasExtra("wordId")){
            wordId = (int) getIntent().getIntExtra("wordId",0);
            Log.d(TAG, "received extra: word ID: " + wordId);
            wordPageViewModel.setWordId(wordId);
        }

        wordPageViewModel.getWordById(wordId).observe(this, new Observer<WordPageInfo>() {
            @Override
            public void onChanged(WordPageInfo wordPageInfo) {
                if(wordPageInfo != null){
                    wordPageInfoToDisplay = wordPageInfo;
                    Log.d(TAG, "wordPage Updated: ");
                    wordPageTitle.setText(wordPageInfoToDisplay.getWordTitle());
                    wordPagePhonetic.setText(wordPageInfoToDisplay.getWordPhonetic());
                    wordPagePriority.setText(String.valueOf(wordPageInfoToDisplay.getPriorityLevel()));


                    if(wordPageInfoToDisplay.isBookmarked()){
                        bookmarkIcon.setImageDrawable(ContextCompat.getDrawable(WordPageActivity.this, R.drawable.ic_baseline_bookmark_24));
                    } else {
                        bookmarkIcon.setImageDrawable(ContextCompat.getDrawable(WordPageActivity.this, R.drawable.ic_baseline_bookmark_border_24));
                    }
                }
            }
        });


        wordTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                wordViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        wordViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                wordTabLayout.getTabAt(position).select();
            }
        });





        bookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO change to using background thread
                if(wordPageInfoToDisplay.isBookmarked()){

                    dictionaryDao.changeBookMark(wordPageInfoToDisplay.getWordId(), false);
                    Toast.makeText(WordPageActivity.this, wordPageInfoToDisplay.getWordTitle()+ " removed from bookmarks", Toast.LENGTH_SHORT).show();

                } else {

                    dictionaryDao.changeBookMark(wordPageInfoToDisplay.getWordId(), true);
                    Toast.makeText(WordPageActivity.this, wordPageInfoToDisplay.getWordTitle()+ " added to bookmarks", Toast.LENGTH_SHORT).show();
                    //bookmarkIcon.setImageDrawable(ContextCompat.getDrawable(WordPageActivity.this, R.drawable.ic_baseline_bookmark_24));
                }
                Log.d(TAG, "onClick:  is bookmarked: " + wordPageInfoToDisplay.isBookmarked());
            }
        });


        addPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: priority level incremented ");
                dictionaryDao.changePriorityLevel(wordId, wordPageInfoToDisplay.getPriorityLevel()+1);

                Toast.makeText(WordPageActivity.this, "Priority level raised", Toast.LENGTH_SHORT).show();
            }
        });

        minusPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wordPageInfoToDisplay.getPriorityLevel()>1){
                    Log.d(TAG, "onClick: priority level decreased ");
                    dictionaryDao.changePriorityLevel(wordId, wordPageInfoToDisplay.getPriorityLevel()-1);

                    Toast.makeText(WordPageActivity.this, "Priority level decreased", Toast.LENGTH_SHORT).show();
                }

            }
        });

        audioIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    Log.d(TAG, wordPageInfoToDisplay.getListWordInfo().get(0).getPhonetics().get(0).getAudio());
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(wordPageInfoToDisplay.getListWordInfo().get(0).getPhonetics().get(0).getAudio());
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(WordPageActivity.this, "Failed to play audio", Toast.LENGTH_SHORT).show();
                }



            }
        });




    }


    private void initRecyclerView() {
        wordInfoRecyclerViewAdaptor = new WordInfoRecyclerViewAdaptor();
        wordInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wordInfoRecyclerView.setAdapter(wordInfoRecyclerViewAdaptor);
        wordInfoRecyclerView.setHasFixedSize(true);
    }

}