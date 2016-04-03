package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Product;

import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class OrderProductsAdapter extends ArrayAdapter {


    ImageLoader imageLoader;
    List<Product> products;
    public Context context;

    public OrderProductsAdapter(Context context, List<Product> products) {
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
    public Product getItem(int position) {
        return this.getProducts().get(position);
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
            convertView = infalInflater.inflate(R.layout.row_order_product,null);
            holder.product_title = (TextView) convertView.findViewById(R.id.product_title);
            holder.product_cost = (TextView) convertView.findViewById(R.id.product_cost);
            holder.productPreview = (ImageView) convertView.findViewById(R.id.product_preview);
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        Product product = getItem(position);
        holder.product_title.setText(product.getProductName());
        holder.product_cost.setText("Rs."+product.getTotalCost());
        imageLoader.DisplayImage(product.getImagePath(), holder.productPreview);

        /*int orderSellerId;
        int sellerId;
        int productId;
        double totalCost;
        int orderStatus;
        String productName;
        String imagePath;
        */
        return convertView;
    }
}

