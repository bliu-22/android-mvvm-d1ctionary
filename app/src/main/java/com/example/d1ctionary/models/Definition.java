package com.example.d1ctionary.models;

import java.io.Serializable;
import java.util.List;

public class Definition implements Serializable {
    private List<String> synonyms;
    private List<String> antonyms;
    private String definition;
    private String example;

    public Definition(List<String> synonyms, List<String> antonyms, String definition, String example) {
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.definition = definition;
        this.example = example;
    }

    public Definition() {
    }

    public List<String> getSynonyms(){
        return synonyms;
    }

    public List<String> getAntonyms(){
        return antonyms;
    }

    public String getDefinition(){
        return definition;
    }

    public String getExample(){
        return example;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "synonyms=" + synonyms +
                ", antonyms=" + antonyms +
                ", definition='" + definition + '\'' +
                ", example='" + example + '\'' +
                '}';
    }

    public String toReadableString(){
        if(example!= null){
            return definition + "\n" +
                    "Example: " + example + "\n";
        } else {
            return  definition + "\n";
        }


    }
}
