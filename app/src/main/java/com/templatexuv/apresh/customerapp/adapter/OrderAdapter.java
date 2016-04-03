package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.CheckOutFragment;
import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.datalayer.CartDA;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.model.Order;
import com.templatexuv.apresh.customerapp.util.CartListener;

import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class OrderAdapter extends ArrayAdapter {
    ImageLoader imageLoader;
    List<Order> orderItemsList;
    public Context context;

    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0);
        this.context = context;
        this.orderItemsList = orders;
        this.imageLoader = CustomerApplication.getApplicationInstance().getImageLoader();
    }


    public List<Order> getOrderItems(){
        return orderItemsList;
    }


    public static class ProductViewHolder {
        ImageView order_icon;
        TextView product_orderId;
        TextView order_Code;
        TextView order_cost;
        TextView product_OrderedOn;
    }

    @Override
    public int getCount() {
        return this.orderItemsList != null ? this.orderItemsList.size() : 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            holder = new ProductViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_order,null);
            holder.order_icon = (ImageView) convertView.findViewById(R.id.order_icon);
            holder.product_orderId = (TextView) convertView.findViewById(R.id.product_orderId);
            holder.order_Code = (TextView) convertView.findViewById(R.id.order_Code);
            holder.order_cost = (TextView) convertView.findViewById(R.id.order_cost);
            holder.product_OrderedOn = (TextView) convertView.findViewById(R.id.product_OrderedOn);
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }

        final Order order = orderItemsList.get(position);
        holder.product_orderId.setText(order.getOrderId());
        holder.order_Code.setText(order.getOrderCode());
        holder.order_cost.setText("Rs:" + order.getTotalCost());
       /* String url = order.get
        imageLoader.DisplayImage(url, holder.order_icon);
*/
        holder.product_OrderedOn.setText(order.getOrderDate());

        return convertView;
    }
}

