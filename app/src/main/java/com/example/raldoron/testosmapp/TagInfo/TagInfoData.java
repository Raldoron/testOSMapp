package com.example.raldoron.testosmapp.TagInfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raldoron on 18.12.15.
 */
public class TagInfoData {

    @SerializedName("data")
    public TagOSM mData;
    public TagOSM getData(){
        return mData;
    }
}
