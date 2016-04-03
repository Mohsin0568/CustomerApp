package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.MainActivity;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class ProductsAdapter extends ArrayAdapter {


    RecyclerView recyclerView;
    ImageLoader imageLoader;
    List<Product> products;
    public Context context;

    public ProductsAdapter(Context context, List<Product> products) {
        super(context, 0);
        this.context = context;
        this.products = products;
        this.imageLoader = CustomerApplication.getApplicationInstance().getImageLoader();
    }

    public static class ProductViewHolder {
        ImageView productPreview;
        TextView product_title;
        TextView product_cost;
    }

    public void removeProducts() {
        this.products.clear();
        notifyDataSetChanged();
    }

    public List<Product> getProducts(){
        return this.products;
    }
    public void addProducts(List<Product> productList) {
        this.products.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.products != null ? this.products.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            holder = new ProductViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_product,null);
            holder.product_title = (TextView) convertView.findViewById(R.id.product_title);
            holder.product_cost = (TextView) convertView.findViewById(R.id.product_cost);
            holder.productPreview = (ImageView) convertView.findViewById(R.id.product_preview);
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        holder.product_title.setText(products.get(position).getProdName());
        holder.product_cost.setText("Rs."+products.get(position).getPrice());
        if(products.get(position).getImageURL()!=null) {
            for (int i = 0; i < products.get(position).getImageURL().size(); i++) {
                String url = products.get(position).getImageURL().get(i);
                if(url!=null){
                   String filename = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
                    if(filename!=null && filename.equalsIgnoreCase("thumbnail")) {
                        Log.v("Productadapter",filename);
                        imageLoader.DisplayImage(url, holder.productPreview);
                    }
                }
                break;
            }
        }

        return convertView;
    }
}

