package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.model.Value;

import java.util.List;

/**
 * Created by Apresh on 6/7/2015.
 */
public class ValueAdapter extends ArrayAdapter{
    Context context;
    List<Value> valuesList;
    public Value value;

    public ValueAdapter(Context context, List<Value> propertyList) {
        super(context,0, propertyList);
        this.context = context;
        this.valuesList = propertyList;
    }
    @Override
    public int getCount() {
        return (this.valuesList != null ? this.valuesList.size():0);
    }


    @Override
    public Value getItem(int position) {
        return this.valuesList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    public List<Value> getValuesList(){
        return  this.valuesList;
    }
    public void refreshSlection(int position){
        for(int i=0;i<valuesList.size();i++){
            if(position == i){
                valuesList.get(i).setIsChecked(true);
            }else{
                valuesList.get(i).setIsChecked(false);
            }
        }
        notifyDataSetChanged();
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_property_item, null);
        }
        TextView propertyName = (TextView) convertView.findViewById(R.id.propertyName);
        Value value = (Value) getItem(position);

        propertyName.setText(value.getValueName());
        if(value.isChecked()){
            this.value = value;
            propertyName.setBackgroundColor(context.getResources().getColor(R.color.theme_green_color));
        }else{
            propertyName.setBackgroundColor(context.getResources().getColor(R.color.white_color));
        }

        return convertView;
    }



}


