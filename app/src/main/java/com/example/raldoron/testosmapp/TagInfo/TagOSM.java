package com.example.raldoron.testosmapp.TagInfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raldoron on 17.12.15.
 */
public class TagOSM {
    //public final String key;
    @SerializedName("value")
    public String mValue;
    public String getValue(){
        return mValue;
    }

    @SerializedName("count")
    public int mCount;
    public int getCount(){
        return mCount;
    }

    @SerializedName("fraction")
    public float mFraction;
    public float getFraction(){
        return mFraction;
    }

    @SerializedName("in_wiki")
    public boolean mIn_wiki;
    public boolean getIn_wiki(){
        return mIn_wiki;
    }

    @SerializedName("description")
    public String mDescription;
    public String getDescription(){
        return mDescription;
    }

    /*
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
    */
}
