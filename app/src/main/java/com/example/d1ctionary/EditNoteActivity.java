package com.example.d1ctionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.d1ctionary.local.DictionaryDao;
import com.example.d1ctionary.local.DictionaryDatabase;
import com.example.d1ctionary.viewmodels.WordPageViewModel;

public class EditNoteActivity extends AppCompatActivity {

    private static final String TAG = "EditNoteActivity";
    int wordId;
    String  noteReceived;
    Button btnCancel;
    Button btnSave;
    EditText noteContent;
    DictionaryDao dictionaryDao;
    WordPageViewModel wordPageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        getSupportActionBar().setTitle("Edit Note");

        wordPageViewModel = new ViewModelProvider(this).get(WordPageViewModel.class);
        dictionaryDao = DictionaryDatabase.getInstance(EditNoteActivity.this).dictionaryDao();

        noteContent = findViewById(R.id.note_content);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);

        if(getIntent().hasExtra("wordId")){
            wordId = (int) getIntent().getIntExtra("wordId",0);
            Log.d(TAG, "received extra: word ID: " + wordId);
            wordPageViewModel.setWordId(wordId);
        }
        if(getIntent().hasExtra("noteContent")){
            noteReceived = (String) getIntent().getStringExtra("noteContent");
            noteContent.setText(noteReceived);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteToAdd = noteContent.getText().toString();
                dictionaryDao.changeNote(wordId, noteToAdd);
                onBackPressed();
            }
        });


    }
}