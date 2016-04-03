package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.R;

import com.templatexuv.apresh.customerapp.model.Category;
import com.templatexuv.apresh.customerapp.model.ChildCategory;
import com.templatexuv.apresh.customerapp.util.ChildListener;
import com.templatexuv.apresh.customerapp.util.SecondLevelExpandableListView;
import com.templatexuv.apresh.customerapp.util.SubCategoryListener;

import java.util.List;

/**
 * Created by Apresh on 9/12/2015.
 */
public class ParentAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<Category> categoryList;
    private SecondLevelAdapter secondLevelAdapter;
    private ChildListener childListener;
    SubCategoryListener subCategoryListener;

    public ParentAdapter(Context context,List<Category> dataList,SubCategoryListener subCategoryListener) {
        this.context = context;
        this.categoryList = dataList;
        this.childListener = (ChildListener)subCategoryListener;
        this.subCategoryListener = subCategoryListener;
    }

    @Override
    public Object getChild(int arg0, int arg1) {
        return arg1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        secondLevelAdapter =new SecondLevelAdapter(context, categoryList,groupPosition,childPosition);
        secondLevelELV.setAdapter(secondLevelAdapter);
        secondLevelELV.setGroupIndicator(null);


        secondLevelELV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPositi, long id) {
                Log.v("secondLevelELV", "groupPosition" + groupPosition + ":" + "childPosition" + childPosition);
                subCategoryListener.subCategoryClick(v, groupPosition, childPosition, secondLevelAdapter);
                return false;
            }
        });
        secondLevelELV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ChildCategory childCategory = (ChildCategory)v.getTag();
                childListener.onChildSelect(childCategory.getCatThreeId(),childCategory.getCatThreeName());
                return false;
            }
        });
        return secondLevelELV;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categoryList.get(groupPosition).getCatTwos()!=null? categoryList.get(groupPosition).getCatTwos().size():0;
    }

    @Override
    public Category getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_category_item, null);

        }
        TextView text = (TextView) convertView.findViewById(R.id.categoryName);
        ImageView icon_left = (ImageView) convertView.findViewById(R.id.icon_left);
        ImageView icon_right = (ImageView) convertView.findViewById(R.id.icon_right);

        Category category = getGroup(groupPosition);
        text.setText(category.getCatOneName());

        int drawable = R.drawable.icon_male;
        switch (groupPosition) {
              case 0:
                drawable =R.drawable.icon_male;
                break;
            case 1:
                drawable =R.drawable.icon_female;
                break;
            case 2:
                drawable =R.drawable.icon_kids;
                break;
            case 3:
                drawable =R.drawable.icon_furniture;
                break;
        }
        icon_left.setImageResource(drawable);

        if (isExpanded) {
            icon_right.setImageResource(R.drawable.icon_minsu);
        } else {
            icon_right.setImageResource(R.drawable.icon_plus);
        }

        return convertView;
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