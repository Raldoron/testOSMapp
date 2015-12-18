package com.example.raldoron.testosmapp;

/**
 * Created by Raldoron on 17.12.15.
 */
public class TagOSM {
    //public final String key;
    public final String value;
    public final int count;
    public final float fraction;
    public final boolean in_wiki;
    public final String description;

    public TagOSM(String value, int count, float fraction, boolean in_wiki, String description){
        this.value = value;
        this.count = count;
        this.fraction = fraction;
        this.in_wiki = in_wiki;
        this.description = description;
    }
}
