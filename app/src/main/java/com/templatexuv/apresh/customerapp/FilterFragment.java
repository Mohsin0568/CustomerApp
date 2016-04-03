package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.ValuesAdapter;
import com.templatexuv.apresh.customerapp.model.AllProperty;
import com.templatexuv.apresh.customerapp.model.BaseModel;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.model.Value;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement th
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends BaseFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    // TODO: Rename and change types of parameters
    ArrayList<Property> propAndValues;
    HashMap<String,Property> selectionPropValues;
    private LinearLayout dynamiclay;
    private ProgressBar progressBar;
    private Button button_apply;
    private TextView noFilters;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(ArrayList<Property> propertyValues) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, propertyValues);
        fragment.setArguments(args);
        return fragment;
    }

    public FilterFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            propAndValues = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
        selectionPropValues = new HashMap<String,Property>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView       = inflater.inflate(R.layout.fragment_filter2, container, false);
        dynamiclay          = (LinearLayout) rootView.findViewById(R.id.dynamiclay);
        progressBar         = (ProgressBar) rootView.findViewById(R.id.progressBar);
        noFilters           = (TextView) rootView.findViewById(R.id.noFilters);
        button_apply        = (Button) rootView.findViewById(R.id.button_apply);
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerApplication.getApplicationInstance().setSelectionPropValues(selectionPropValues);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (propAndValues != null) {
            button_apply.setVisibility(View.VISIBLE);
            for (Property property : propAndValues) {

                LinearLayout contentLay = new LinearLayout(getActivity());
                contentLay.setOrientation(LinearLayout.HORIZONTAL);
                contentLay.setGravity(Gravity.CENTER_HORIZONTAL);
                contentLay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView headingView = new TextView(getActivity());
                headingView.setTextSize(16);
                headingView.setTextColor(getActivity().getResources().getColor(R.color.black_color));
                headingView.setPadding(10, 20, 10, 0);
                headingView.setGravity(Gravity.RIGHT);
                headingView.setText(property.getPropertyName());


                contentLay.addView(headingView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                Spinner spinner = (Spinner) getActivity().getLayoutInflater().inflate(R.layout.layout_spinner, null);
                contentLay.addView(spinner, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ValuesAdapter valuesAdapter = new ValuesAdapter(getActivity(), property.getPropValues(), property.getPropertyName(), property.getPropertyId());
                valuesAdapter.setDropDownViewResource(R.layout.adapter_value_item);
                spinner.setAdapter(valuesAdapter);

                spinner.setSelected(false);

                dynamiclay.addView(contentLay, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                Property propertyObj = new Property();
                propertyObj.setPropertyName(property.getPropertyName());
                propertyObj.setPropertyId(property.getPropertyId());
                ArrayList<Value> values = new ArrayList<Value>();
                Value value = new Value();
                value.setValueId(property.getPropValues().get(0).getValueId());
                value.setValueName(property.getPropValues().get(0).getValueName());
                values.add(value);
                property.setPropValues(values);
                selectionPropValues.put(property.getPropertyName(), propertyObj);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            Value value = (Value) view.getTag();
                            String selection = value.getValueName();
                            Property property = new Property();
                            property.setPropertyName(value.getPropertyName());
                            property.setPropertyId(value.getPropertyId());
                            ArrayList<Value> values = new ArrayList<Value>();
                            values.add(value);
                            property.setPropValues(values);
                            selectionPropValues.put(value.getPropertyName(), property);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }
        }else{
            noFilters.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }


}