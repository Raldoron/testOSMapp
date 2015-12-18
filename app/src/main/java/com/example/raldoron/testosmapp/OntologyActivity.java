package com.example.raldoron.testosmapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Raldoron on 07.12.15.
 */
public class OntologyActivity extends BaseActivity {

    final String TAG = getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ontology_activity);

        Toast.makeText(getApplicationContext(), "Ontology", Toast.LENGTH_LONG);

        TextView resTextView = (TextView) findViewById(R.id.resultTextView);

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Constants.TaginfoAPI_URI).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        //  /api/4/key/values?key=highway&page=1&rp=10&sortname=count_ways&sortorder=desc

        TagInfoAPI tagInfoAPI = retrofit.create(TagInfoAPI.class);
        Call<TagOSM> call = tagInfoAPI.getValuesForKey("highway", 1, 10, "count_ways", "desc");
        call.enqueue(new Callback<TagOSM>() {
            @Override
            public void onResponse(retrofit.Response<TagOSM> response, Retrofit retrofit) {
                Log.d("CallBack", " response is " + response.message());
                Log.d("CallBack", response.body().toString());
                Log.d("CallBack", response.raw().body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("CallBack", " Throwable is " +t);
            }
        });
        /*
        Call<List<TagOSM>> call = tagInfoAPI.getValuesForKey("highway", 1, 10, "count_way", "desc");
        try {
            List<TagOSM> tagOSMs = call.execute().body();
            for (TagOSM tagOSM : tagOSMs){
                Log.d(TAG, tagOSM.value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        /*
        tagInfoAPI.getValuesForKey("highway", 1, 10, "count_way", "desc", new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                String res = response.toString();
                System.out.println(res);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        */

    }

}
