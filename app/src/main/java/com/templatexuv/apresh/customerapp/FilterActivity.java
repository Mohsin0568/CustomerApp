package com.templatexuv.apresh.customerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.model.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements PropertyFragment.OnFragmentInteractionListener,ValueFragment.OnFragment2InteractionListener {

    private Toolbar mToolbar;
    private Button buttonCancel,button_apply;
    private HashMap<String,Property> selectionPropValues;
    private Value value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        button_apply = (Button) findViewById(R.id.buttonApply);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Filter");
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        selectionPropValues = new HashMap<>();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Property> propertyList =  CustomerApplication.getApplicationInstance().getPropAndValues();
                for(int i=0;i<propertyList.size();i++){

                        propertyList.get(i).setIsChecked(false);

                }
                CustomerApplication.getApplicationInstance().setPropAndValues(propertyList);
                finish();
                CustomerApplication.getApplicationInstance().setSelectionPropValues(null);
            }
        });
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(value!=null) {
                    String selection = value.getValueName();
                    Property property = new Property();
                    property.setPropertyName(value.getPropertyName());
                    property.setPropertyId(value.getPropertyId());
                    ArrayList<Value> values = new ArrayList<Value>();
                    values.add(value);
                    property.setPropValues(values);
                    selectionPropValues.put(value.getPropertyName(), property);
                    CustomerApplication.getApplicationInstance().setSelectionPropValues(selectionPropValues);
                    finish();
                }else{
                    Toast.makeText(FilterActivity.this,"Please select your filter Criteria.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onFragment2Interaction(Value value) {

        this.value = value;
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
