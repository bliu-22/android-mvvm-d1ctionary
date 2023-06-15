package com.example.d1ctionary.models;

import java.io.Serializable;
import java.util.List;

public class Meaning implements Serializable {
    private String partOfSpeech;
    private List<Definition> definitions;

    public Meaning(String partOfSpeech, List<Definition> definitions) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
    }

    public Meaning() {
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public String toString() {
        return "Meaning{" +
                "partOfSpeech='" + partOfSpeech + '\'' +
                ", definitions=" + definitions +
                '}';
    }

    public String toReadableStringDefinition (){
        String r = "";
        int index = 1;
        for (Definition definition : definitions) {
            r += index + ". " +  definition.toReadableString() + "\n";
            index++;
        }

        return r;
    }
    public String toStringShortDefinition(){
        String r = "";
        int index = 1;
        for (Definition definition : definitions) {
            r += index + ". " +  definition.toReadableString() + "  ";
            index++;
        }

        return r;
    }


}
