package com.example.d1ctionary.models;

import java.io.Serializable;

public class Phonetic implements Serializable {

    private String text;
    private String audio;

    public Phonetic(String text, String audio) {
        this.text = text;
        this.audio = audio;
    }

    public Phonetic() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Phonetic{" +
                "text='" + text + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }
}
