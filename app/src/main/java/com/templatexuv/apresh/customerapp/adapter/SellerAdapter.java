package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Seller;

import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class SellerAdapter extends ArrayAdapter {


    RecyclerView recyclerView;
    ImageLoader imageLoader;
    List<Seller> sellers;
    public Context context;

    public SellerAdapter(Context context, List<Seller> sellers) {
        super(context, 0);
        this.context = context;
        this.sellers = sellers;
        this.imageLoader = CustomerApplication.getApplicationInstance().getImageLoader();
    }

    public static class ProductViewHolder {
        ImageView sellerPreview;
        TextView seller_brand;
        TextView seller_id;
        RatingBar ratingBar;
    }

    public void removeSellers() {
        this.sellers.clear();
        notifyDataSetChanged();
    }

    public List<Seller> getSellers(){
        return this.sellers;
    }
    @Override
    public int getCount() {
        return this.sellers != null ? this.sellers.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            holder = new ProductViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_seller,null);
            holder.seller_brand = (TextView) convertView.findViewById(R.id.seller_brand);
            holder.seller_id = (TextView) convertView.findViewById(R.id.seller_id);
            holder.sellerPreview = (ImageView) convertView.findViewById(R.id.seller_preview);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        holder.seller_brand.setText(sellers.get(position).getSellerBrand());
        holder.seller_id.setText("SellerId:" + sellers.get(position).getSellerId());
        holder.ratingBar.setRating(Float.parseFloat(sellers.get(position).getRatings()));
       // String url = sellers.get(position).getImageURL().size() > 0 ? sellers.get(position).getImageURL().get(0) : "";
        //imageLoader.DisplayImage(url, holder.sellerPreview);

        return convertView;
    }
}

