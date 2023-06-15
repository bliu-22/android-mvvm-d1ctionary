package com.example.d1ctionary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.d1ctionary.local.DictionaryDao;
import com.example.d1ctionary.models.WordPageInfo;
import com.example.d1ctionary.recyclerviews.WordInfoRecyclerViewAdaptor;
import com.example.d1ctionary.viewmodels.WordPageViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordInfoFragment extends Fragment {
    private static final String TAG = "WordInfoFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    WordInfoRecyclerViewAdaptor wordInfoRecyclerViewAdaptor;
    DictionaryDao dictionaryDao;
    RecyclerView wordInfoRecyclerView;
    WordPageViewModel wordPageViewModel;
    WordPageInfo curWordPageInfo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView wordFragmentRecyclerView;
    public WordInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordInfoFragment newInstance(int wordId) {
        WordInfoFragment fragment = new WordInfoFragment();
        Bundle args = new Bundle();

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
        Log.d(TAG, "onCreate: Created");


        wordPageViewModel = new ViewModelProvider(requireActivity()).get(WordPageViewModel.class);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_info, container, false);


        wordInfoRecyclerView = view.findViewById(R.id.word_info_recyclerview);

        wordInfoRecyclerViewAdaptor = new WordInfoRecyclerViewAdaptor();
        wordInfoRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        wordInfoRecyclerView.setAdapter(wordInfoRecyclerViewAdaptor);
        wordInfoRecyclerView.setHasFixedSize(true);

        Log.d(TAG, "onCreateView: fragment created");



        wordPageViewModel.getWordById(wordPageViewModel.getCurWordId()).observe(this, new Observer<WordPageInfo>() {
            @Override
            public void onChanged(WordPageInfo wordPageInfo) {
                if(wordPageInfo != null){
                    curWordPageInfo = wordPageInfo;
                    wordInfoRecyclerViewAdaptor.setListWordInfo(curWordPageInfo.getListWordInfo());
                }

            }
        });



        return view;

    }
}