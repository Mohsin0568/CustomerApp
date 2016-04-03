package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.model.Category;

import java.util.List;

/**
 * Created by Apresh on 6/7/2015.
 */
public class CategoryAdapter extends ArrayAdapter{
    Context context;
    List<Category> categoryList;
    public CategoryAdapter(Context context, List<Category> categoryList) {
        super(context,0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }
    @Override
    public int getCount() {
        return (this.categoryList != null ? this.categoryList.size():0);
    }

    @Override
    public Object getItem(int position) {
        return this.categoryList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_category_item, null);
        }
        TextView tvcategory_name = (TextView) convertView.findViewById(R.id.categoryName);
        Category category = (Category) getItem(position);
        tvcategory_name.setText(category.getCatOneName());
        convertView.setTag(category);
        return convertView;
    }
}


