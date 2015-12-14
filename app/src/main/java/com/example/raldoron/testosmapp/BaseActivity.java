package com.example.raldoron.testosmapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by ekozoch on 15.12.15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        View basicView = getLayoutInflater().inflate(R.layout.activity_basic, null);
        LinearLayout basicLayout = (LinearLayout) basicView.findViewById(R.id.layout_basic);
        // Вставляем в BasicLayout childView
        getLayoutInflater().inflate(layoutResID, basicLayout, true);
        super.setContentView(basicView);
        initBasicView();
    }

    private void initBasicView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
