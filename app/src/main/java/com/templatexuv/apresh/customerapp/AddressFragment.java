package com.templatexuv.apresh.customerapp;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.adapter.AddressAdapter;
import com.templatexuv.apresh.customerapp.adapter.SellerAdapter;
import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.model.AllAddress;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.model.Seller;
import com.templatexuv.apresh.customerapp.util.AddressListener;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class AddressFragment extends BaseFragment implements DataListener,AddressListener{

    private static final String SCREEN_NAME= "screen_name";
    private  float mActionBarHeight;
    private Button button_sellers;
    private ListView listView;
    private SellerAdapter sellerAdapter;
    private List<Seller> sellers;
    //private OnScrollEndListener mCallback;
    private TextView noitems;
    private List<Cart> cartItemsList;
    private long userId;
    private ProgressBar progressBar;
    private long cartId;
    private RelativeLayout parentView;
    private ArrayList<Address> addressList;
    private AddressAdapter addressAdapter;
    private Button buttonAddAddress;
    private Button buttonProceed;
    private Address checkedAddress;

    public static AddressFragment newInstance(List<Cart> cartItems,List<Address> addressList) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("addressList",(ArrayList)addressList);
        args.putParcelableArrayList("cartItemsList", (ArrayList) cartItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
        setHasOptionsMenu(true);

        if(getActivity() instanceof  MainActivity) {
            ((MainActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.address));
        }else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.address));
        }
        else if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.address));
        else if(getActivity() instanceof ProductDetailsActivity)
            ((ProductDetailsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.address));

        Bundle bundle = getArguments();
        if(bundle!=null) {
            addressList = bundle.getParcelableArrayList("addressList");
            cartItemsList = bundle.getParcelableArrayList("cartItemsList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_address, container, false);
        parentView = (RelativeLayout) rootView.findViewById(R.id.parentView);
        listView = (ListView) rootView.findViewById(R.id.listView);
        noitems = (TextView) rootView.findViewById(R.id.noitems);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);
        buttonAddAddress = (Button) rootView.findViewById(R.id.buttonAddAddress);
        buttonProceed = (Button) rootView.findViewById(R.id.buttonProceed);

            if(getActivity() instanceof ProductsActivity){
                parentView.setPadding(0, ((ProductsActivity) getActivity()).getSupportActionBar().getHeight(), 0, 0);
            }else if(getActivity() instanceof ProductDetailsActivity){
                parentView.setPadding(0,((ProductDetailsActivity)getActivity()).getSupportActionBar().getHeight(),0,0);
            }
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedAddress!=null)
                pushFragment(CheckOutFragment.newInstance(cartItemsList,checkedAddress));
                else
                    Toast.makeText(getActivity(),"Please select an address.",Toast.LENGTH_SHORT).show();
            }
        });

        buttonAddAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                pushFragment(SaveAddressFragment.newInstance("Save Address", true, null));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addressAdapter.setSelectedIndex(position);
                addressAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(addressList!=null && addressList.size()>0) {
            addressAdapter = new AddressAdapter(getActivity(),addressList,AddressFragment.this);
            listView.setAdapter(addressAdapter);
            buttonProceed.setVisibility(View.VISIBLE);
        }else{
            noitems.setVisibility(View.VISIBLE);
            buttonProceed.setVisibility(View.GONE);
        }

        if(CustomerApplication.getApplicationInstance().isFetchAddress()){
            List params = constructGetAllAddressParameters();
            HttpWorker worker = new HttpWorker(AddressFragment.this, getActivity(),progressBar);
            worker.execute(params);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mCallback = (OnScrollEndListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }*/
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
        progressBar.setVisibility(View.GONE);
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



    @Override
    public void dataDownloaded(Response response) {

        try {

                String message = "";
                String screenName = null;

                if(response!=null && response.method == ServiceMethods.WS_DELETE_ADDRESS) {
                    if (response != null) {
                        message = response.message != null ? response.message : message;
                    }

                if (response != null && response.data != null && (response.data instanceof AllAddress) && (((AllAddress) response.data).issuccess())) {

                    Toast.makeText(getActivity(),"Item removed Successfully.",Toast.LENGTH_SHORT).show();
                    addressAdapter.removeItem(cartId);
                    addressAdapter.notifyDataSetChanged();
                    if(addressAdapter.getAddresses().size()==0){
                        noitems.setVisibility(View.VISIBLE);
                        buttonProceed.setVisibility(View.GONE);
                    }else{
                        buttonProceed.setVisibility(View.VISIBLE);
                    }

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }else if(response!=null && response.method == ServiceMethods.WS_GET_ALL_ADDRESS) {

                    if (response != null) {
                        message = response.message != null ? response.message : message;
                    }

                    if (response != null && response.data != null && (response.data instanceof AllAddress) && (((AllAddress) response.data).issuccess())) {

                        ArrayList<Address> addressList = ((AllAddress) response.data).getAddresses();
                        if(addressList!=null && addressList.size()>0) {
                            addressAdapter = new AddressAdapter(getActivity(),addressList,AddressFragment.this);
                            listView.setAdapter(addressAdapter);
                            buttonProceed.setVisibility(View.VISIBLE);
                            noitems.setVisibility(View.GONE);
                        }

                    } else {
                        showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                                getActivity().getResources().getString(R.string.ok), null, screenName);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRemoved(long cartId) {
        this.cartId = cartId;
        List params = constructRemoveCartParameters(cartId);
        HttpWorker worker = new HttpWorker(AddressFragment.this, getActivity(),progressBar);
        worker.execute(params);
    }

    @Override
    public void onEditAddress(Address address) {
        pushFragment(SaveAddressFragment.newInstance("Edit Address", false,address));
    }

    @Override
    public void onAddressChecked(Address address) {
        this.checkedAddress = address;
    }

    public List constructRemoveCartParameters(long addressId){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_DELETE_ADDRESS);
        params.add("GET");
        params.add(ServiceMethods.WS_DELETE_ADDRESS);
        params.add(new String[]{"addressId"});
        params.add(new String[]{"" + addressId});
        return params;
    }
    public List constructGetAllAddressParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_ADDRESS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_ALL_ADDRESS);
        params.add(new String[]{"userId"});
        params.add(new String[]{"" + userId});
        return params;
    }

}