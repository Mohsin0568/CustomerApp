package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.model.Value;

import java.util.List;

/**
 * Created by Apresh on 6/7/2015.
 */
public class OptionsAdapter extends ArrayAdapter{
    Context context;
    String[] options;
    int selectedposition;
    public OptionsAdapter(Context context,String[] options,int selectedposition) {
        super(context,0, options);
        this.context = context;
        this.options = options;
        this.selectedposition = selectedposition;
    }
    @Override
    public int getCount() {
        return (this.options != null ? this.options.length:0);
    }

    @Override
    public String getItem(int position) {
        return this.options[position];
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_value_item, null);
        }
        TextView optionName = (TextView) convertView.findViewById(R.id.valueName);
        optionName.setText(getItem(position));
        if(position == selectedposition){
            optionName.setTypeface(null, Typeface.BOLD);
        }else{
            optionName.setTypeface(null, Typeface.NORMAL);
        }
        return convertView;
    }



}


