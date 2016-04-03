package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.model.Category;
import com.templatexuv.apresh.customerapp.model.ChildCategory;
import com.templatexuv.apresh.customerapp.model.SubCategory;

import java.util.List;

/**
 * Created by Apresh on 9/12/2015.
 */

public class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Category> categoryList;
    private int parentPosition;
    private int childPosition;

    public SecondLevelAdapter(Context context,List<Category> dataList,int parentPosition,int childPosition) {
        this.context = context;
        this.categoryList = dataList;
        this.parentPosition = parentPosition;
        this.childPosition = childPosition;
    }

    @Override
    public Category getGroup(int groupPosition) {

        return null;
    }

    public void refreshData(List<Category> dataList){
        this.categoryList = dataList;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        Log.v("ParentCount", categoryList.get(parentPosition).getCatTwos().size() + "");
        return this.categoryList.get(parentPosition).getCatTwos()!=null?1:0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_subcategory_item, null);

        }

        TextView text = (TextView) convertView.findViewById(R.id.categoryName);
        ImageView icon_right = (ImageView) convertView.findViewById(R.id.icon_right);
        Log.v("groupPosition",groupPosition+"");
        SubCategory subcategory = this.categoryList.get(parentPosition).getCatTwos().get(childPosition);
        text.setText(subcategory.getCatTwoName());

        if (isExpanded) {
            icon_right.setImageResource(R.drawable.icon_minsu);
        } else {
            icon_right.setImageResource(R.drawable.icon_plus);
        }

        return convertView;
    }

    @Override
    public ChildCategory getChild(int groupPosition, int childPos) {
        return this.categoryList.get(parentPosition).getCatTwos().get(childPosition).getCatThrees().get(childPos);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_childcategory_item, null);

        }
        TextView text = (TextView) convertView.findViewById(R.id.categoryName);
        ChildCategory childcategory = getChild(groupPosition,childPosition);
        text.setText(childcategory.getCatThreeName());
        convertView.setTag(childcategory);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.v("ChildrenCount","parentPosition"+parentPosition+":"+"groupPosition"+groupPosition);
        Log.v("ChildrenCount",(this.categoryList.get(parentPosition).getCatTwos().get(childPosition).getCatThrees()!=null?this.categoryList.get(parentPosition).getCatTwos().get(childPosition).getCatThrees().size():0)+"");
        return this.categoryList.get(parentPosition).getCatTwos().get(childPosition).getCatThrees()!=null?this.categoryList.get(parentPosition).getCatTwos().get(childPosition).getCatThrees().size():0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}