package com.example.raldoron.testosmapp.TagInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raldoron on 18.12.15.
 */
public class TagInfoData {

    @SerializedName("data")
    public List<TagOSM> mData;
    public List<TagOSM> getData(){
        return mData;
    }
}
