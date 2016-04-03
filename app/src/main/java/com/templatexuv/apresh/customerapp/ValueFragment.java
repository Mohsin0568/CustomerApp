package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.templatexuv.apresh.customerapp.adapter.PropertyAdapter;
import com.templatexuv.apresh.customerapp.adapter.ValueAdapter;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.model.Value;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragment2InteractionListener}
 * interface.
 */
public class ValueFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragment2InteractionListener mListener;
    private ValueAdapter valueAdapter;

    // TODO: Rename and change types of parameters
    public static ValueFragment newInstance(String param1, String param2) {
        ValueFragment fragment = new ValueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ValueFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

      //  setListAdapter(new ArrayAdapter<Value>(getActivity(),
      //          android.R.layout.simple_list_item_1, android.R.id.text1,CustomerApplication.getApplicationInstance().getPropAndValues().get(0).getPropValues() ));
        valueAdapter = new ValueAdapter(getActivity(),CustomerApplication.getApplicationInstance().getPropAndValues().get(0).getPropValues());
        setListAdapter(valueAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mListener.onFragment2Interaction(valueAdapter.value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },500);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragment2InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragment2Interaction(DummyContent.ITEMS.get(position).id);
            if(valueAdapter!=null){
                valueAdapter.refreshSlection(position);
                mListener.onFragment2Interaction(valueAdapter.getValuesList().get(position));
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragment2InteractionListener {
        // TODO: Update argument type and name
        public void onFragment2Interaction(Value value);
    }

}
