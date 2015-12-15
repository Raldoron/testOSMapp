package com.example.raldoron.testosmapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Raldoron on 07.12.15.
 */
public class OntologyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ontology_activity);
        Toast.makeText(this, "Ontology", Toast.LENGTH_LONG);
    }
}
