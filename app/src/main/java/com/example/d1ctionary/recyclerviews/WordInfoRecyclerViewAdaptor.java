package com.example.d1ctionary.recyclerviews;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d1ctionary.R;
import com.example.d1ctionary.models.Meaning;
import com.example.d1ctionary.models.Word;

import java.util.ArrayList;
import java.util.List;

public class WordInfoRecyclerViewAdaptor extends RecyclerView.Adapter<WordInfoRecyclerViewAdaptor.WordInfoHolder> {

    List<Word> listWordInfo = new ArrayList<>();



    @NonNull
    @Override
    public WordInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_info_list_item,parent,false);
        return new WordInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordInfoRecyclerViewAdaptor.WordInfoHolder holder, int position) {
        holder.wordInfoMeanings.removeAllViews();

        holder.wordInfoTitle.setText(listWordInfo.get(position).getTitle());
        holder.wordInfoPhonetic.setText(listWordInfo.get(position).getPhonetic());

        //populate the meaning section with spannable string
        for (Meaning meaning : listWordInfo.get(position).getMeanings()){
            TextView textViewMeaning = new TextView(holder.itemView.getContext());

            textViewMeaning.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            SpannableString stringToAdd = new SpannableString(meaning.getPartOfSpeech() + "\n" + meaning.toReadableStringDefinition());
            stringToAdd.setSpan(new StyleSpan(Typeface.BOLD), 0, meaning.getPartOfSpeech().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewMeaning.setText(stringToAdd);

            holder.wordInfoMeanings.addView(textViewMeaning);
        }

    }


//    private void handleWordDisplay(Word word){
//        word.getTitle()
//    }

    @Override
    public int getItemCount() {

        if(listWordInfo!= null){
            return listWordInfo.size();
        }
        return 0;
    }

    public void setListWordInfo(List<Word> listWordInfo){
        this.listWordInfo = listWordInfo;
        notifyDataSetChanged();
    }

    class WordInfoHolder extends RecyclerView.ViewHolder{
        TextView wordInfoTitle;
        TextView wordInfoPhonetic;
        LinearLayoutCompat wordInfoMeanings;
        public WordInfoHolder(@NonNull View itemView) {
            super(itemView);
            wordInfoTitle = itemView.findViewById(R.id.word_info_title);
            wordInfoPhonetic = itemView.findViewById(R.id.word_info_phonetic);
            wordInfoMeanings = itemView.findViewById(R.id.word_info_meanings);


        }

//        @Override
//        public void onClick(View v) {
//
//        }
    }


}
