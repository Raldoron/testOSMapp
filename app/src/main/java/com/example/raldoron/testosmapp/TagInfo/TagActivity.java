package com.example.raldoron.testosmapp.TagInfo;

import android.os.Bundle;
import android.widget.TextView;

import com.example.raldoron.testosmapp.BaseActivity;
import com.example.raldoron.testosmapp.R;

/**
 * Created by Raldoron on 21.12.15.
 */
public class TagActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        int position = getIntent().getExtras().getInt("item");
        String description = getIntent().getExtras().getString("descript");

        TextView tagValue = (TextView) findViewById(R.id.tagValue);
        //tagValue.setText(Integer.toString(position));
        tagValue.setText(description);
    }
}
