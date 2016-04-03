package com.templatexuv.apresh.customerapp.util;

import android.view.View;

import com.templatexuv.apresh.customerapp.adapter.SecondLevelAdapter;

/**
 * Created by Apresh on 9/25/2015.
 */
public interface SubCategoryListener {

    public void subCategoryClick(View v,int grandparentPosition, int parentPosition,SecondLevelAdapter secondLevelAdapter);
}
