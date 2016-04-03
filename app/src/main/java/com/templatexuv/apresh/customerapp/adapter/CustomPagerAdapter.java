package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;

import java.util.List;

/**
 * Created by Apresh on 6/25/2015.
 */
public class CustomPagerAdapter extends PagerAdapter {

    private final ImageLoader imgLoader;
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> imageUrl;

    public CustomPagerAdapter(Context context,List<String> imageUrl) {
        imgLoader = CustomerApplication.getApplicationInstance().getImageLoader();
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageUrl = imageUrl;
    }

    @Override
    public int getCount() {
        return this.imageUrl!=null ? this.imageUrl.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.adapter_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        try {
             imgLoader.DisplayImage(imageUrl.get(position), imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
