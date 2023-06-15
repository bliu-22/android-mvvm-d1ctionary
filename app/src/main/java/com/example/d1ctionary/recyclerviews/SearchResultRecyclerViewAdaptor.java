package com.example.d1ctionary.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d1ctionary.R;
import com.example.d1ctionary.models.Word;
import com.example.d1ctionary.models.WordPageInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchResultRecyclerViewAdaptor extends RecyclerView.Adapter<SearchResultRecyclerViewAdaptor.SearchResultHolder> {

    List<WordPageInfo> listWordPageInfo = new ArrayList<>();
    OnResultClickListener listener;
    @NonNull
    @Override
    public SearchResultRecyclerViewAdaptor.SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultRecyclerViewAdaptor.SearchResultHolder holder, int position) {

        holder.searchResultTitle.setText(listWordPageInfo.get(position).getWordTitle());
        holder.searchResultDescription.setText(listWordPageInfo.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        if(listWordPageInfo != null){
            return listWordPageInfo.size();
        }
        return 0;
    }

    public void setListWordPageInfo(List<WordPageInfo> listWordPageInfo){
        this.listWordPageInfo = listWordPageInfo;
        notifyDataSetChanged();
    }

    public interface OnResultClickListener{
        void onResultCLick(WordPageInfo wordPageInfo);
    }

    public void setOnItemClickListener(OnResultClickListener listener){
        this.listener = listener;
    }


    class SearchResultHolder extends RecyclerView.ViewHolder{
        TextView searchResultTitle;
        TextView searchResultDescription;


        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            this.searchResultTitle = itemView.findViewById(R.id.search_result_title);
            this.searchResultDescription = itemView.findViewById(R.id.search_result_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (listener != null && position!= RecyclerView.NO_POSITION){
                        listener.onResultCLick(listWordPageInfo.get(position));
                    }

                }
            });
        }
    }
}


