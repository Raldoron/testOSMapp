package com.example.raldoron.testosmapp.AddPOI;

import android.util.Log;

import com.example.raldoron.testosmapp.Constants;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Raldoron on 01.12.15.
 */
public class OSMApi {

    private final static String TAG = OSMApi.class.getSimpleName();
    private static OSMApi instance;

    /**
     * replies the {@see OsmApi} for the URL given by the preference
     * <code>osm-server.url</code>
     *
     * @return the OsmApi
     * @exception IllegalStateException
     *                thrown, if the preference <code>osm-server.url</code> is
     *                not set
     *
     */
    static public OSMApi getOsmApi() {
        if (instance == null) {
            instance = new OSMApi();
        }
        return instance;
    }

    private OSMApi() {}


    private StringWriter stringWriter = new StringWriter();
    private OSMWriter osmWriter = new OSMWriter(new PrintWriter(stringWriter));

    private String toXml(OSMNode node){
        stringWriter.getBuffer().setLength(0);
        //osmWriter.setChangeset();
        osmWriter.osmChangeHeader();
        osmWriter.visit(node);
        osmWriter.footer();
        osmWriter.out.flush();
        Log.d(TAG, stringWriter.toString());
        return stringWriter.toString();
    }

    /**
     * Returns the base URL for API requests, including the negotiated version
     * number.
     *
     * @return base URL string
     */
    public String getBaseUrl() {
        String url = Constants.OSM_API_BASE_URL + "/" + Constants.OSM_API_VERSION + "/";
        return url;
    }
    private String sendRequest(String requestMethod, String urlSuffix, String requestBody){
        StringBuffer resp = new StringBuffer();
        while (true) {
            try {
                URL url = new URL(new URL(getBaseUrl()), urlSuffix);
                Log.d(TAG, requestMethod + " " + url + "... ");
                HttpURLConnection activeConnection = (HttpURLConnection) url.openConnection();
                activeConnection.setConnectTimeout(15000);
                activeConnection.setRequestMethod(requestMethod);
                activeConnection.setDoOutput(true);
                activeConnection.setRequestProperty("Content-type",
                        "text/xml");
                OutputStream out = activeConnection.getOutputStream();
                if (requestBody != null) {
                    BufferedWriter bwr = new BufferedWriter(
                            new OutputStreamWriter(out, "UTF-8"));
                    bwr.write(requestBody);
                    bwr.flush();
                }
                out.close();

                activeConnection.connect();

                int retCode = activeConnection.getResponseCode();
                Log.d(TAG, retCode + ": " + activeConnection.getResponseMessage());
                activeConnection.disconnect();

                switch (retCode){
                    case HttpURLConnection.HTTP_OK:
                        return resp.toString();
                }

            } catch (Exception e) {

            }

        }

    }

    public void createNode(OSMNode node){
        String ret = "";
        try {
            ret = sendRequest("PUT", "node/create", toXml(node));
        }catch (Exception e){

        }
    }
}
