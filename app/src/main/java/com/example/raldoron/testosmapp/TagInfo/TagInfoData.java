package com.example.raldoron.testosmapp.TagInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raldoron on 18.12.15.
 */
public class TagInfoData {

    private static final TagInfoData INSTANCE = new TagInfoData();

    @SerializedName("data")
    public List<TagOSM> mData;
    public List<TagOSM> getData(){
        return mData;
    }

    public int getSize(){
        return mData.size();
    }

    public static TagInfoData getInstance(){
        return INSTANCE;
    }

    public TagOSM getTag(int pos){
        if(pos < mData.size()) {
            return mData.get(pos);
        }
        else{
            return null;
        }
    }
}
