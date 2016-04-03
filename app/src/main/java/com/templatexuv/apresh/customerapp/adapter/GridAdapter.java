package com.templatexuv.apresh.customerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Product;

import java.util.List;

/**
 * Created by Apresh on 11/10/2015.
 */
public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder>  {

    ImageLoader imageLoader;
    List<Product> products;
    GridItemClickListener gridItemClickListener;

    public GridAdapter(List<Product> products,GridItemClickListener gridItemClickListener) {
        super();
        this.products = products;
        this.imageLoader = CustomerApplication.getApplicationInstance().getImageLoader();
        this.gridItemClickListener = gridItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_product, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v,gridItemClickListener,position);
        Log.v("onCreateViewHolder", "" + position);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Product product = products.get(position);
        viewHolder.product_title.setText(products.get(position).getProdName());
        viewHolder.product_cost.setText("Rs." + products.get(position).getPrice());
     //   String url = products.get(position).getImageURL().size() > 0 ? products.get(position).getImageURL().get(0) : "";
       // imageLoader.DisplayImage(url, viewHolder.productPreview);
        if(product.getImageURL()!=null) {
            for (int i = 0; i < product.getImageURL().size(); i++) {
                String imageUrl = product.getImageURL().get(i);
                Log.v("imageUrl",imageUrl);
                if(imageUrl!=null){
                    String filename = imageUrl.substring(imageUrl.lastIndexOf("/")+1,imageUrl.lastIndexOf("."));
                    if(filename!=null && filename.equalsIgnoreCase("thumbnail")) {
                        Log.v("Gridadapter",filename);
                        imageLoader.DisplayImage(imageUrl, viewHolder.productPreview);
                        break;
                    }
                }

            }
        }

        Log.v("onBindViewHolder", "" + position);
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
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView productPreview;
        TextView product_title;
        TextView product_cost;
        GridItemClickListener gridItemClickListener;
        int position;

        public ViewHolder(View itemView,GridItemClickListener gridItemClickListener,int position) {
            super(itemView);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_cost = (TextView) itemView.findViewById(R.id.product_cost);
            productPreview = (ImageView) itemView.findViewById(R.id.product_preview);
            this.gridItemClickListener = gridItemClickListener;
            this.position = position;
            Log.v("ViewHolder",""+position);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            gridItemClickListener.onGridItemClick(getLayoutPosition());
        }
    }

    public interface GridItemClickListener{
        public void onGridItemClick(int position);
    }
}