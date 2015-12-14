package com.example.raldoron.testosmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import oauth.signpost.OAuth;

/**
 * Created by Raldoron on 03.12.15.
 */
public class CheckAuthorisation {

    final String TAG = getClass().getName();

    private Context context;
    private SharedPreferences sharedPreferences;
    private String oAuthToken;
    private String oAuthTokenSecret;

    public CheckAuthorisation(Context mContext){
        context = mContext;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        oAuthToken = sharedPreferences.getString(OAuth.OAUTH_TOKEN, null);
        oAuthTokenSecret = sharedPreferences.getString(OAuth.OAUTH_TOKEN_SECRET, null);
    }

    public boolean check(){
        if (oAuthToken != null && oAuthTokenSecret != null){
            Toast.makeText(context,"OAuth - User authorised!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "OAuth - User authorised!");
            return true;
        }else {
            Toast.makeText(context,"OAuth - Guest!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "OAuth - Guest!");
            return false;
        }
    }


    /*
    //from MapActivity
    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    String oAuthToken = mPrefs.getString(OAuth.OAUTH_TOKEN, null);
    String oAuthTokenSecret = mPrefs.getString(OAuth.OAUTH_TOKEN_SECRET, null);
    if(oAuthToken != null && oAuthTokenSecret != null){
        Toast.makeText(this, "OAuth - User authorised!", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "OAuth - User authorised!");
    }
    */
}
