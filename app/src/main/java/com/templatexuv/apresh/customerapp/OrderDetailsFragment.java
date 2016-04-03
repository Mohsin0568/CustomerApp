package com.templatexuv.apresh.customerapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.CartAdapter;
import com.templatexuv.apresh.customerapp.adapter.OrderAdapter;
import com.templatexuv.apresh.customerapp.adapter.OrderProductsAdapter;
import com.templatexuv.apresh.customerapp.adapter.SellerAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CartDA;
import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.model.AllPaymentResponse;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.model.Order;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.Seller;
import com.templatexuv.apresh.customerapp.util.CartListener;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.EndlessOnScrollListener;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsFragment extends BaseFragment{

    private ListView listView;

    private OrderProductsAdapter orderProductAdapter;
    private Order order;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
            ((BaseActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.order));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_details, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);


        order = getArguments().getParcelable("orderItem");

        //Header View
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.row_order_product, null);
        TextView product_orderId = (TextView) view.findViewById(R.id.product_orderId);
        TextView order_Code = (TextView) view.findViewById(R.id.order_Code);
        TextView order_cost = (TextView) view.findViewById(R.id.order_cost);
        TextView product_OrderedOn = (TextView) view.findViewById(R.id.product_OrderedOn);
        listView.addHeaderView(view);

        product_orderId.setText(order.getOrderId());
        order_Code.setText(order.getOrderCode());
        order_cost.setText("Rs:" + order.getTotalCost());
        product_OrderedOn.setText(order.getOrderDate());

        View address_View  = LayoutInflater.from(getActivity()).inflate(R.layout.row_address, null);
        TextView name = (TextView) address_View.findViewById(R.id.name);
        TextView address = (TextView) address_View.findViewById(R.id.address);
        TextView landmark = (TextView) address_View.findViewById(R.id.landmark);
        TextView phone = (TextView) address_View.findViewById(R.id.phone);
        TextView city              = (TextView) address_View.findViewById(R.id.city);
        TextView pincode           =  (TextView) address_View.findViewById(R.id.pincode);

        listView.addFooterView(address_View);

        orderProductAdapter = new OrderProductsAdapter(getActivity(),order.getProducts());
        listView.setAdapter(orderProductAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main, menu);
        menu.clear();;

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

}