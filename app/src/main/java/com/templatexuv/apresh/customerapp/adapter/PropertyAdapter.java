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
public class PropertyAdapter extends ArrayAdapter{
    Context context;
    List<Property> propertyList;

    public PropertyAdapter(Context context, List<Property> propertyList) {
        super(context,0, propertyList);
        this.context = context;
        this.propertyList = propertyList;
    }
    @Override
    public int getCount() {
        return (this.propertyList != null ? this.propertyList.size():0);
    }

    public void refreshSlection(int position){
        for(int i=0;i<propertyList.size();i++){
            if(position == i){
                propertyList.get(i).setIsChecked(true);
            }else{
                propertyList.get(i).setIsChecked(false);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public Property getItem(int position) {
        return this.propertyList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_property_item, null);
        }
        TextView propertyName = (TextView) convertView.findViewById(R.id.propertyName);
        Property property = (Property) getItem(position);

        propertyName.setText(property.getPropertyName());
        if(property.isChecked()){
            propertyName.setBackgroundColor(context.getResources().getColor(R.color.theme_green_color));
        }else{
            propertyName.setBackgroundColor(context.getResources().getColor(R.color.white_color));
        }

        return convertView;
    }



}


