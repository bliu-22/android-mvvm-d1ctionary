package com.example.d1ctionary;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.viewmodels.WordPageViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordNoteFragment extends Fragment {
    private static final String TAG = "WordNoteFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WordPageViewModel wordPageViewModel;
    private CardView noteCardView;
    private TextView noteTextView;
    public WordNoteFragment() {
        // Required empty public constructor
    }


    public static WordNoteFragment newInstance(String param1, String param2) {
        WordNoteFragment fragment = new WordNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d(TAG, "onCreate: CRREAted");

        wordPageViewModel = new ViewModelProvider(requireActivity()).get(WordPageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_note, container, false);
        noteCardView = view.findViewById(R.id.note_card_view);
        noteTextView = view.findViewById(R.id.note_text_view);

        noteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(requireActivity(), EditNoteActivity.class);
                intent.putExtra("wordId", wordPageViewModel.getCurWordId());

                Log.d(TAG, "onClick:  " + noteTextView.getText().toString());
                if(noteTextView.getText() !=null){
                    intent.putExtra("noteContent", noteTextView.getText().toString());
                }
                startActivity(intent);

            }
        });

        wordPageViewModel.getWordById(wordPageViewModel.getCurWordId()).observe(this, new Observer<WordPageInfo>() {
            @Override
            public void onChanged(WordPageInfo wordPageInfo) {
                if(wordPageInfo != null){
                    noteTextView.setText(wordPageInfo.getNote());
                }
            }
        });


    return view;

    }
}