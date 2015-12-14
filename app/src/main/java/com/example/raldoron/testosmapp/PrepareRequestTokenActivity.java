package com.example.raldoron.testosmapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

/**
 * Created by Raldoron on 02.11.15.
 */
public class PrepareRequestTokenActivity extends Activity {

    final String TAG = getClass().getName();

    private OAuthConsumer consumer;
    private OAuthProvider provider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
            this.provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL, Constants.ACCESS_URL, Constants.AUTHORIZE_URL);
        }catch (Exception e){
            Log.e(TAG, "Error creating consumer / provider", e);
        }
        Log.i(TAG, "Starting task to retrieve request token.");
        new OAuthRequestTokenTask(this, consumer, provider).execute();
    }

    /**
     * Called when the OAuthRequestTokenTask finishes (user has authorized the request token).
     * The callback URL will be intercepted here.
     */

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final Uri uri = intent.getData();
        if ( uri != null && uri.getScheme().equals(Constants.CALLBACK_SCHEME)){
            Log.i(TAG, "Callback received : " + uri);
            Log.i(TAG, "Retrieving Access Token");
            new RetrieveAccessTokenTask(this, consumer, provider, preferences).execute(uri);
            finish();
        }
    }

    public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {
        private Context context;
        private OAuthConsumer consumer;
        private OAuthProvider provider;
        private SharedPreferences preferences;

        public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider, SharedPreferences preferences){
            this.context = context;
            this.consumer = consumer;
            this.provider = provider;
            this.preferences = preferences;
        }

        @Override
        protected Void doInBackground(Uri... params) {
            final Uri uri = params[0];
            final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

            try {
                provider.retrieveAccessToken(consumer, oauth_verifier);

                final SharedPreferences.Editor editor = preferences.edit();
                editor.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
                editor.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
                editor.commit();

                String token = preferences.getString(OAuth.OAUTH_TOKEN, "");
                String secret = preferences.getString(OAuth.OAUTH_TOKEN_SECRET, "");

                consumer.setTokenWithSecret(token, secret);
                //context.startActivity(new Intent(context, MapActivity.class));

                setResult(RESULT_OK);
                Log.i(TAG, "OAuth - Access Token Retrieved");
                finish();

            }catch (Exception e){
                Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
            }
            return null;
        }
    }
}
