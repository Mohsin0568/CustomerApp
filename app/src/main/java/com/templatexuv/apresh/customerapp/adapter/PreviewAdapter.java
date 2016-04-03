package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;

import java.util.List;

/**
 * Created by Apresh on 6/7/2015.
 */
public class PreviewAdapter extends ArrayAdapter{
    Context context;
    List<String> preivewList;
    int selectedPosition;

    public PreviewAdapter(Context context, List<String> preivewList) {
        super(context,0, preivewList);
        this.context = context;
        this.preivewList = preivewList;
    }
    @Override
    public int getCount() {
        return (this.preivewList != null ? this.preivewList.size():0);
    }

    @Override
    public String getItem(int position) {
        return this.preivewList.get(position);
    }

    public void updateSelection(int position){

        notifyDataSetChanged();

        this.selectedPosition = position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_item, null);
            viewHolder = new ViewHolder();
            viewHolder.previewView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.selection  = (View) convertView.findViewById(R.id.selection);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(position == selectedPosition){
            viewHolder.selection.setBackgroundColor(getContext().getResources().getColor(R.color.theme_green_color));
        }else{
            viewHolder.selection.setBackgroundColor(getContext().getResources().getColor(R.color.white_color));
        }
        CustomerApplication.getApplicationInstance().getImageLoader().DisplayImage(getItem(position), viewHolder.previewView);
        viewHolder.previewView.setTag(getItem(position));
        return convertView;
    }

    private static class ViewHolder{
        ImageView previewView;
        View selection;
    }
}


