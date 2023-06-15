package com.example.d1ctionary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Word implements Serializable {

    @SerializedName("word")
    @Expose()
    private String title;
    private String phonetic;
    private List<Phonetic> phonetics;
    private List<Meaning> meanings;

    public Word(String title, String phonetic, List<Phonetic> phonetics, List<Meaning> meanings) {
        this.title = title;
        this.phonetic = phonetic;
        this.phonetics = phonetics;
        this.meanings = meanings;
    }

    public Word() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + title + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", phonetics=" + phonetics +
                ", meanings=" + meanings +
                '}';
    }
}
