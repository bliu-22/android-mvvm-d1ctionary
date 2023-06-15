package com.example.d1ctionary.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
@Entity(tableName = "word_table")
public class WordPageInfo  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int wordId;

    private List<Word> listWordInfo;
    private String wordTitle;
    private String wordPhonetic;
    private boolean isBookmarked;
    private int priorityLevel;
    private String note;


    private String shortDescription;


    @NonNull
    public int getWordId() {
        return wordId;
    }

    public void setWordId(@NonNull int wordId) {
        this.wordId = wordId;
    }

    public WordPageInfo(List<Word> listWordInfo, boolean isBookmarked, int priorityLevel, String note) {
        this.listWordInfo = listWordInfo;
        this.isBookmarked = isBookmarked;
        this.priorityLevel = priorityLevel;
        this.note = note;
        this.wordTitle = listWordInfo.get(0).getTitle();
        this.wordPhonetic = listWordInfo.get(0).getPhonetic();
        this.shortDescription = listWordInfo.get(0).getMeanings().get(0).toStringShortDefinition();
    }

    public WordPageInfo(String title){
        this.listWordInfo = null;
        this.isBookmarked = false;
        this.priorityLevel = 1;
        this.note = "";
        this.wordTitle = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    public String getWordTitle() {
        return wordTitle;
    }

    public void setWordTitle(String wordTitle) {
        this.wordTitle = wordTitle;
    }

    public String getWordPhonetic() {
        return wordPhonetic;
    }

    public void setWordPhonetic(String wordPhonetic) {
        this.wordPhonetic = wordPhonetic;
    }

    public List<Word> getListWordInfo() {
        return listWordInfo;
    }

    public void setListWordInfo(List<Word> listWordInfo) {
        this.listWordInfo = listWordInfo;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
