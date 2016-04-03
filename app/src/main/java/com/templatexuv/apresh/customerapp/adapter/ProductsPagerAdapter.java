package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.templatexuv.apresh.customerapp.ProductDetailsFragment;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Product;

import java.util.List;


/**
 * Created by Apresh on 6/25/2015.
 */
public class ProductsPagerAdapter extends FragmentStatePagerAdapter {

    private final ImageLoader imgLoader;
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Product> productList;
    String sellerId;

    public ProductsPagerAdapter(Context context, FragmentManager fm, List<Product> productList, String sellerId) {
        super(fm);
        imgLoader = new ImageLoader(context);
        mContext = context;
        this.productList = productList;
        this.sellerId = sellerId;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList != null ? productList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("prodcut", productList.get(position));
        bundle.putString("sellerId", sellerId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
