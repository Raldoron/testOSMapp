package com.example.raldoron.testosmapp.AddPOI;

/**
 * Created by Raldoron on 02.12.15.
 */
public class OSMFacade {

    private static OSMApi _osmApi = OSMApi.getOsmApi();

    public OSMFacade(){

    }

    public boolean createPOI(OSMNode poi){
        _osmApi.createNode(poi);
        return true;
    }
}
