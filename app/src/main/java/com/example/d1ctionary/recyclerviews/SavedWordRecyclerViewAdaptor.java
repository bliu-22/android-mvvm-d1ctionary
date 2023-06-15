package com.example.d1ctionary.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d1ctionary.R;
import com.example.d1ctionary.models.WordPageInfo;

import java.util.ArrayList;
import java.util.List;

public class SavedWordRecyclerViewAdaptor extends RecyclerView.Adapter<SavedWordRecyclerViewAdaptor.SavedWordHolder> {
    List<WordPageInfo> listSavedWords = new ArrayList<>();
    OnSavedWordClickListener listener;
    @NonNull
    @Override
    public SavedWordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_word_list_item, parent, false);
        return new SavedWordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordRecyclerViewAdaptor.SavedWordHolder holder, int position) {

        holder.savedWordTitle.setText(listSavedWords.get(position).getWordTitle());

        holder.savedWordPriority.setText(String.valueOf(listSavedWords.get(position).getPriorityLevel()));
        // TODO hardcoded, change later
        holder.savedWordDescription.setText(listSavedWords.get(position).getShortDescription());


    }


    @Override
    public int getItemCount() {
        if(listSavedWords != null){
            return listSavedWords.size();
        }
        return 0;

    }

    public void setListSavedWords(List<WordPageInfo> listSavedWords){
        this.listSavedWords = listSavedWords;
        notifyDataSetChanged();
    }

    public WordPageInfo getWordPageInfoAt(int position){
        return listSavedWords.get(position);
    }

    public interface OnSavedWordClickListener{
        void onSavedWordCLick(WordPageInfo wordPageInfo);
    }

    public void setOnItemClickListener(SavedWordRecyclerViewAdaptor.OnSavedWordClickListener listener){
        this.listener = listener;
    }

    class SavedWordHolder extends RecyclerView.ViewHolder{
        TextView savedWordTitle;
        TextView savedWordPriority;
        TextView savedWordDescription;


        public SavedWordHolder(@NonNull View itemView) {
            super(itemView);
            savedWordTitle = itemView.findViewById(R.id.saved_word_title);
            savedWordPriority = itemView.findViewById(R.id.saved_word_priority);
            savedWordDescription = itemView.findViewById(R.id.saved_word_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (listener != null && position!= RecyclerView.NO_POSITION){
                        listener.onSavedWordCLick(listSavedWords.get(position));
                    }
                }
            });
        }
    }
}
