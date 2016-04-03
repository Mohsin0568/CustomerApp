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
import com.templatexuv.apresh.customerapp.util.CartListener;

import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class CartAdapter extends ArrayAdapter {

    private final CartDA cartDA;
    ImageLoader imageLoader;
    List<Cart> cartItemsList;
    public Context context;
    CartListener cartListener;
    int unavailableProdId ;
    String unavailableMessage ="";

    boolean isCheckoutPAge = false;
    public CartAdapter(Context context, List<Cart> carts,CartListener cartListener) {
        super(context, 0);
        this.context = context;
        this.cartItemsList = carts;
        this.imageLoader = CustomerApplication.getApplicationInstance().getImageLoader();
        cartDA = new CartDA();
        this.cartListener = cartListener;
        if(cartListener instanceof CheckOutFragment)
        this.isCheckoutPAge = true;
    }

    public void setUnavailablePositionMessage(int unavailablePosition,String unavailableMessage){
        this.unavailableProdId = unavailablePosition;
        this.unavailableMessage = unavailableMessage;
        notifyDataSetChanged();
    }

    public List<Cart> getCartItems(){
        return cartItemsList;
    }

    public double getCartItemsTotal(){
        double total =0;
        for(Cart cart :cartItemsList){
            total = total + cart.getPrice();
        }
        return total;
    }


    public static class ProductViewHolder {
        ImageView product_imageUrl;
        TextView product_display_name;
        TextView product_cost;
        EditText quantity;
        TextView product_level_info;
        TextView delivery_by;
        ImageView remove_product;
        TextView unavailableView;
    }

    public boolean removeItem(int cartId){
        for(Cart cart: cartItemsList){
            if(cartId == cart.getCartId()){
                cartItemsList.remove(cart);
                return true;
            }
        }
        return false;
    }
    public List<Cart> getCartProducts(){
        return this.cartItemsList;
    }
    @Override
    public int getCount() {
        return this.cartItemsList != null ? this.cartItemsList.size() : 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            holder = new ProductViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_card,null);
            holder.product_imageUrl = (ImageView) convertView.findViewById(R.id.product_imageUrl);
            holder.product_display_name = (TextView) convertView.findViewById(R.id.product_display_name);
            holder.product_cost = (TextView) convertView.findViewById(R.id.product_cost);
            holder.quantity = (EditText) convertView.findViewById(R.id.quantity);
            holder.product_level_info = (TextView) convertView.findViewById(R.id.product_level_info);
            holder.delivery_by = (TextView) convertView.findViewById(R.id.delivery_by);
            holder.remove_product =  (ImageView) convertView.findViewById(R.id.remove_address);
            holder.unavailableView = (TextView) convertView.findViewById(R.id.unavailableView);
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        final Cart cart = cartItemsList.get(position);
        holder.product_display_name.setText(cart.getProductName());
        holder.product_cost.setText("Rs:" + cart.getPrice());
        String url = cart.getImageURL();
        imageLoader.DisplayImage(url, holder.product_imageUrl);
       /* if(cart.getImageURL()!=null) {
            for (int i = 0; i < cart.getImageURL().size(); i++) {
                String url = cart.getImageURL().get(i);
                if(url!=null){
                    String filename = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
                    if(filename!=null && filename.equalsIgnoreCase("thumbnail"))
                        imageLoader.DisplayImage(url, holder.product_imageUrl);    }
                break;
            }
        }*/
        holder.quantity.setText("" + cart.getCartQuantity());

        holder.product_level_info.setVisibility(View.GONE);
       // holder.product_level_info.setText(cart.getCategoryOneName()+">"+cart.getCategoryTwoName()+">"+cart.getCategoryThreeName());
        holder.delivery_by.setText("");
        holder.delivery_by.setVisibility(View.GONE);
        holder.remove_product.setTag(cart.getCartId());
        holder.remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // cartDA.deleteCart(Integer.parseInt((String) v.getTag()));
                    //cartItemsList.remove(position);
                    cartListener.onItemRemoved((int) v.getTag());
                    // notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if(isCheckoutPAge) {
            holder.quantity.setEnabled(false);
            holder.remove_product.setVisibility(View.INVISIBLE);
            if (unavailableProdId == cart.getProductId()) {
                holder.unavailableView.setVisibility(View.VISIBLE);
                holder.unavailableView.setText(unavailableMessage);
                //holder.quantity.setBackgroundResource(R.drawable.edittext_round_bg);

               // holder.quantity.setTag(cart.getProductQuantity());
            /*holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String string = ((EditText)v).getText().toString();
                    if(string.length()>0) {
                        Integer squantity = Integer.parseInt(string);
                        int productQuantity = (int) v.getTag();
                        if (squantity > productQuantity) {
                            Toast.makeText(context, "Quantity exceeds available quantity.", Toast.LENGTH_SHORT).show();
                        } else {

                            cartItemsList.get(position).setCartQuantity(squantity);
                        }
                    }
                }
            });*/
            } else {
                holder.unavailableView.setVisibility(View.GONE);

//            holder.quantity.setFocusable(false);
                //holder.quantity.setBackgroundResource(R.color.transparent_color);
            }
        }else{
            holder.remove_product.setVisibility(View.VISIBLE);

            if (cart.getCartQuantity()==0) {
                holder.unavailableView.setVisibility(View.VISIBLE);
                holder.unavailableView.setText(R.string.valid_quantity);
            }
            else if (cart.getCartQuantity() > cart.getProductQuantity()) {
                holder.unavailableView.setVisibility(View.VISIBLE);
                holder.unavailableView.setText(R.string.cartQuantityExceeds);
                /*String cartQuantity = holder.quantity.getText().toString();
                if(cartQuantity.length()>0) {
                    this.cartItemsList.get(position).setCartQuantity(Integer.parseInt(cartQuantity));
                    notifyDataSetChanged();
                }*/
                // holder.quantity.setTag(cart.getProductQuantity());
            holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String string = ((EditText)v).getText().toString();
                    if(string.length()>0) {

                        try {
                            Integer squantity = Integer.parseInt(string);
                            // int productQuantity = (int) v.getTag();
                            if(cartItemsList!=null && cartItemsList.size()>0)
                            cartItemsList.get(position).setCartQuantity(squantity);
                            // notifyDataSetChanged();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            }else{

                holder.unavailableView.setVisibility(View.GONE);
                holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String string = ((EditText) v).getText().toString();
                        if (string.length() > 0) {
                            try {
                                Integer squantity = Integer.parseInt(string);
                                // int productQuantity = (int) v.getTag();
                                if(cartItemsList!=null && cartItemsList.size()>0)
                                cartItemsList.get(position).setCartQuantity(squantity);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            // notifyDataSetChanged();
                        }
                    }
                });
            }
        }
        return convertView;
    }
}

