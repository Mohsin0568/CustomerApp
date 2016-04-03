package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.templatexuv.apresh.customerapp.adapter.ParentAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CategoryDA;
import com.templatexuv.apresh.customerapp.model.AllCategory;
import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.Category;
import com.templatexuv.apresh.customerapp.model.ChildCategory;
import com.templatexuv.apresh.customerapp.model.SubCategory;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link com.templatexuv.apresh.customerapp.SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends BaseFragment implements DataListener {
    /**
     * Use this factory method to create a new instance of
     *
     * @return A new instance of fragment SplashFragment.
     */
    private CategoryDA categoryDA;
    private List<Category> categoryList;
    private Handler handler;
    private  long userId;
    private ProgressBar progressBar;

    // TODO: Rename and change types and number of parameters
    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();

        return fragment;
    }

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDA = new CategoryDA();
        handler = new Handler();
        userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
        processCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
    public void onResume() {
        super.onResume();

        ((LaunchActivity) getActivity()).onSectionAttached(null, false);


    }
    @Override
    public void onDetach() {
        super.onDetach();

    }


    private void processCategories(){

        List<Category> categories = categoryDA.selectAllCategory();
        if(categories==null){
            List params = constructParameters();
            HttpWorker worker = new HttpWorker(SplashFragment.this,getActivity(),progressBar);
            worker.execute(params);
           //showLoader(getString(R.string.processing));
        }else {
            if(userId>0) {
                List params = constructGetCartParameters();
                HttpWorker worker = new HttpWorker(SplashFragment.this, getActivity(), progressBar);
                worker.execute(params);
            }else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchMainScreen();
                    }
                },2000);
            }


        }


            /*if (SharedPrefUtils.readBooleanPreferenceValue(Constants.PREF_IS_LOGGED_IN)) {
                try {
                    getActivity().finish();
                    getActivity().getSupportFragmentManager().popBackStack();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    (getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, LoginFragment.newInstance(getActivity().getString(R.string.login)))
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
    }
    public List constructGetCartParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_NO_OF_PRODUCTS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_NO_OF_PRODUCTS);
        params.add(new String[]{"customerId"});
        params.add(new String[]{"" + userId});
        return params;
    }
    public List constructParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_ALL_CATEGORIES);
        params.add("GET");
        params.add(ServiceMethods.WS_GETALL_CATEGORIES);
        return params;
    }
    @Override
    public void dataDownloaded(Response response) {

        try {
            progressBar.setVisibility(View.GONE);
            if(response!=null && response.method == ServiceMethods.WS_GETALL_CATEGORIES) {
                String message = "";
              //  String screenName = getActivity().getString(R.string.categories);
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }
                if (response != null && response.data != null && (response.data instanceof AllCategory) && ((AllCategory) response.data).issuccess()) {

                    categoryList = ((AllCategory) response.data).getCategories();
                    if(categoryList!=null && categoryList.size()>0) {
                        for(Category category : categoryList){
                            categoryDA.insertCategory(category);
                            for(SubCategory subcategory : category.getCatTwos()){
                                subcategory.setCatOneId(category.getCatOneId());
                                categoryDA.insertSubCategory(subcategory);
                                for(ChildCategory childcategory : subcategory.getCatThrees()){
                                    childcategory.setCatTwoId(subcategory.getCatTwoId());
                                    categoryDA.insertChildCategory(childcategory);
                                }
                            }
                        }
                        launchMainScreen();
                    }
                } else {
                    showAlertDialog(getResources().getString(R.string.app_name), message,
                            getResources().getString(R.string.ok), null, "Splash");
                }
            }else  if(response!=null && response.method == ServiceMethods.WS_GET_NO_OF_PRODUCTS) {
                String message = "";
                String screenName = null;

                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllProduct) && ((((AllProduct) response.data).issuccess()) || !(((AllProduct) response.data).issuccess()))) {

                    updateHotCount(((AllProduct) response.data).getNoOfProducts());
                    launchMainScreen();

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, "Splash");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void launchMainScreen(){
        try {

            try {
                getActivity().getSupportFragmentManager().popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);

            getActivity().finish();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
