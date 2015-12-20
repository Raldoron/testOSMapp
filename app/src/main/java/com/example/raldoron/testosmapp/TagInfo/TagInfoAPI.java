package com.example.raldoron.testosmapp.TagInfo;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Raldoron on 17.12.15.
 */
public interface TagInfoAPI {
    @GET("api/4/key/values") //key=highway&page=1&rp=10&sortname=count_ways&sortorder=desc
    //http://taginfo.openstreetmap.org/api/4/key/values?key=highway&page=1&rp=10&sortname=count_ways&sortorder=desc
    Call<ResponseBody> getValuesForKey(@Query("key") String key, @Query("page") int page, @Query("rp") int rp, @Query("sortname") String sortname, @Query("sortorder") String sortorder);
    //Call<List<TagInfoData>> getValuesForKey(@Query("key") String key, @Query("page") int page, @Query("rp") int rp, @Query("sortname") String sortname, @Query("sortorder") String sortorder);
    //void getValuesForKey(@Query("key") String key, @Query("page") int page, @Query("rp") int rp, @Query("sortname") String sortname, @Query("sortorder") String sortorder, Callback<Response> rb);
}
