package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.templatexuv.apresh.customerapp.adapter.CustomPagerAdapter;
import com.templatexuv.apresh.customerapp.adapter.PreviewAdapter;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.util.CirclePageIndicator;
import com.templatexuv.apresh.customerapp.util.HorizontalListView;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoomFragment extends Fragment {


    private Product product;
    private ViewPager pager;
    private CustomPagerAdapter customPagerAdapter;
    private CirclePageIndicator circlePageIndicator;
    private int position;
    private OnFragmentInteractionListener mCallback;
    private HorizontalListView previewListView;
    private PreviewAdapter previewAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZoomFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoomFragment newInstance(String param1, String param2) {
        ZoomFragment fragment = new ZoomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ZoomFragment() {
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
        View view = inflater.inflate(R.layout.fragment_zoom, container, false);
        pager  = (ViewPager) view.findViewById(R.id.pager);

        List<String> productUrls = product.getImageURL();
        if(productUrls!=null && productUrls.size()>0) {
            for (int i = 0; i < productUrls.size(); i++) {
                String imageUrl = productUrls.get(i);
                Log.v("imageUrl", imageUrl);
                if (imageUrl != null) {
                    String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));
                    if (filename != null && filename.equalsIgnoreCase("thumbnail")) {
                        Log.v("ProductDetails", filename);
                        productUrls.remove(imageUrl);
                        break;
                    }
                }
            }
            Collections.sort(productUrls);
            customPagerAdapter = new CustomPagerAdapter(getActivity(),productUrls);
            circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
            previewListView      = (HorizontalListView)view.findViewById(R.id.previewList);
            pager.setAdapter(customPagerAdapter);
            pager.setCurrentItem(position);
            circlePageIndicator.setViewPager(pager);
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                previewAdapter.updateSelection(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        previewAdapter = new PreviewAdapter(getActivity(),product.getImageURL());
        previewListView.setAdapter(previewAdapter);
        previewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pager.setCurrentItem(position);
                previewAdapter.updateSelection(position);
                }
        });
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
