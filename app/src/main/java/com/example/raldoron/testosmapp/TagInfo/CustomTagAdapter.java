package com.example.raldoron.testosmapp.TagInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.raldoron.testosmapp.R;

/**
 * Created by Raldoron on 21.12.15.
 */
public class CustomTagAdapter extends BaseAdapter {
    Context context;
    TagInfoData tagInfoData;


    public CustomTagAdapter(Context context, TagInfoData tagInfoData){
        this.context = context;
        this.tagInfoData = tagInfoData;
    }

    @Override
    public int getCount() {
        return tagInfoData.getSize();
    }

    @Override
    public Object getItem(int position) {
        return tagInfoData.getTag(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.listview_row_item, null);
        }

        if (tagInfoData.getTag(position) != null){
            TextView valueTextView = (TextView) view.findViewById(R.id.valueTextView);
            valueTextView.setText(tagInfoData.getTag(position).getValue());
        }

        return view;
    }
}
