package com.example.raldoron.testosmapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raldoron.testosmapp.TagInfo.TagInfoAPI;
import com.example.raldoron.testosmapp.TagInfo.TagInfoData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
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
        //Call<List<TagInfoData>> call = tagInfoAPI.getValuesForKey("highway", 1, 10, "count_ways", "desc");

        Call<ResponseBody> call = tagInfoAPI.getValuesForKey("highway", 1, 10, "count_ways", "desc");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBody> response, Retrofit retrofit) {
                //System.out.println(response.body().string());
                Log.d(TAG, response.body().toString());
                try {
                    Log.d(TAG, new String(response.body().bytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "log", Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });


    }

}
