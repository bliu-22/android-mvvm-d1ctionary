package com.example.d1ctionary.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "word_info_table")
public class WordPageData implements Serializable {

    @PrimaryKey
    @NonNull
    private String wordId;
    private List<Word> listWordInfo;
    private String wordTitle;
    private String wordPhonetic;
    private boolean isBookmarked;
    private int priorityLevel;
    private String note;

    public WordPageData(List<Word> listWordInfo, String wordTitle, String wordPhonetic, boolean isBookmarked, int priorityLevel, String note) {
        this.listWordInfo = listWordInfo;
        this.wordTitle = wordTitle;
        this.wordPhonetic = wordPhonetic;
        this.isBookmarked = isBookmarked;
        this.priorityLevel = priorityLevel;
        this.note = note;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public List<Word> getListWordInfo() {
        return listWordInfo;
    }

    public void setListWordInfo(List<Word> listWordInfo) {
        this.listWordInfo = listWordInfo;
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
