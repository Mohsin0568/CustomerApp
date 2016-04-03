package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.util.AddressListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class AddressAdapter extends ArrayAdapter {

    List<Address> addresses;
    public Context context;
    AddressListener addressListener;
    int selectedIndex = -1;

    private boolean[] array;

    public AddressAdapter(Context context, List<Address> addresses, AddressListener addressListener) {
        super(context, 0);
        this.context = context;
        this.addresses = addresses;
        this.addressListener = addressListener;
        array = new boolean[addresses.size()];
    }

    public static class ProductViewHolder {
        ImageView product_imageUrl;
        TextView name;
        TextView address;
        TextView landmark;
        TextView phone;
        TextView city;
        TextView pincode;
        ImageView remove_product;
        ImageView edit_address;
        CheckBox checkbox;
    }

    public boolean removeItem(long addressId){
        for(Address address:addresses){
            if(addressId == address.getAddressId()){
                addresses.remove(address);
                return true;
            }
        }
        return false;
    }
    public void setSelectedIndex(int index){
        selectedIndex = index;
    }
    public List<Address> getAddresses(){
        return this.addresses;
    }
    @Override
    public int getCount() {
        return this.addresses != null ? this.addresses.size() : 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            holder = new ProductViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_address,null);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.landmark = (TextView) convertView.findViewById(R.id.landmark);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.city              = (TextView) convertView.findViewById(R.id.city);
            holder.pincode           =  (TextView) convertView.findViewById(R.id.pincode);
            holder.remove_product    =  (ImageView) convertView.findViewById(R.id.remove_address);
            holder.edit_address      =  (ImageView) convertView.findViewById(R.id.edit_address);
            holder.checkbox      =  (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        final Address address = addresses.get(position);

        try {
            holder.name.setText(URLDecoder.decode(address.getName(), "UTF-8"));
            holder.address.setText(URLDecoder.decode(address.getAddress(), "UTF-8"));
            holder.landmark.setText(URLDecoder.decode(address.getLandmark(), "UTF-8"));
            holder.phone.setText(URLDecoder.decode(address.getPhone(), "UTF-8"));
            holder.city.setText(URLDecoder.decode(address.getCity(), "UTF-8"));
            holder.pincode.setText(URLDecoder.decode(address.getPinCode(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.remove_product.setTag(address.getAddressId());
        holder.remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // cartDA.deleteCart(Integer.parseInt((String) v.getTag()));
                    //cartItemsList.remove(position);
                    addressListener.onItemRemoved((long) v.getTag());
                    // notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.edit_address.setTag(address);
        holder.edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // cartDA.deleteCart(Integer.parseInt((String) v.getTag()));
                    //cartItemsList.remove(position);
                    addressListener.onEditAddress((Address) v.getTag());
                    // notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.checkbox.setChecked(array[position]);
        holder.checkbox.setTag(address);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Address selecteddAddress = (Address) v.getTag();
                for (int i = 0; i < array.length; i++) {
                    if (i == position) {
                        array[i] = true;
                    } else {
                        array[i] = false;
                    }
                }
                if (((CheckBox) v).isChecked()) {
                    addressListener.onAddressChecked(selecteddAddress);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}

