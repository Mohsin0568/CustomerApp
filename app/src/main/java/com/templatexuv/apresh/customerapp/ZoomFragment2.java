package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.templatexuv.apresh.customerapp.adapter.CustomPagerAdapter;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.util.CirclePageIndicator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZoomFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZoomFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoomFragment2 extends Fragment {


    private Product product;
    private ViewPager pager;
    private CustomPagerAdapter customPagerAdapter;
    private CirclePageIndicator circlePageIndicator;
    private int position;
    private OnFragmentInteractionListener mCallback;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZoomFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoomFragment2 newInstance(String param1, String param2) {
        ZoomFragment2 fragment = new ZoomFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ZoomFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable("prodcut");
            position = getArguments().getInt("position");
        }
        if(mCallback!=null){
            mCallback.onZoomFragmentInitialized();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoom2, container, false);
        pager  = (ViewPager) view.findViewById(R.id.pager);
        customPagerAdapter = new CustomPagerAdapter(getActivity(),product.getImageURL());
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        pager.setAdapter(customPagerAdapter);
        pager.setCurrentItem(position);
        circlePageIndicator.setViewPager(pager);
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onZoomFragmentInitialized();
    }

}
