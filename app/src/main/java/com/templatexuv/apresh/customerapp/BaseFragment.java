package com.templatexuv.apresh.customerapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Apresh on 5/3/2015.
 */
public class BaseFragment extends Fragment {

    public static float px=1.0f;
    private Dialog dialog;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertBuilder;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int selectedPostion;

    public TextView orderCount;
    public static int hot_number;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                getActivity().getResources().getDisplayMetrics());
         fragmentManager = getActivity().getSupportFragmentManager();

    }

    /**
     * Implementing Runnable for runOnUiThread(), This will show a progress
     * dialog
     */
   /* public void showLoader(final String msg) {
        getActivity().runOnUiThread(new Runloader(msg));
    }
*/
    public void showAlertDialog(String strTitle, String strMessage,
                                String firstBtnName, String secondBtnName, String from) {
        getActivity().runOnUiThread(new RunshowCustomDialogs(strTitle, strMessage,
                firstBtnName, secondBtnName, from));
    }



    class Runloader implements Runnable {
        private String strrMsg;

        public Runloader(String strMsg) {
            this.strrMsg = strMsg;
        }

        @Override
        public void run() {
            try {
                if (dialog == null) {
                    dialog = new Dialog(getActivity(),
                            R.style.Theme_Dialog_Translucent);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(
                                    android.graphics.Color.TRANSPARENT));
                }
                dialog.setContentView(R.layout.dialog_loading);
                dialog.setCancelable(true);
                if(dialog!= null && dialog.isShowing())
                    dialog.dismiss();
                dialog.show();
                ProgressBar spinner=(ProgressBar) dialog.findViewById(R.id.progressBar);
                TextView tvLoading = (TextView) dialog
                        .findViewById(R.id.tvLoading);
                if (!strrMsg.equalsIgnoreCase(""))
                    tvLoading.setText(strrMsg);

            } catch (Exception e) {
            }
        }
    }

  /*  public void hideloader() {
        try {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();

            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    class RunshowCustomQuantityDialog implements Runnable {
        private String strTitle;// Title of the dialog
        private String strMessage;// Message to be shown in dialog
        private String firstBtnName;

        public RunshowCustomQuantityDialog(String strTitle, String strMessage, String firstBtnName)
        {

            this.strTitle 		= strTitle;
            this.strMessage 	= strMessage;
            this.firstBtnName 	= firstBtnName;
        }

        @Override
        public void run()
        {
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();

            alertBuilder = new AlertDialog.Builder(getActivity());
            alertBuilder.setCancelable(false);

            final LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_notification, null);
//            LinearLayout llAlertDialogMain  = (LinearLayout)linearLayout.findViewById(R.id.llAlertDialogMain);
            TextView dialogtvTitle = (TextView)linearLayout.findViewById(R.id.tvTitle);
            TextView tvMessage = (TextView)linearLayout.findViewById(R.id.tvMessage);
            Button btnOk = (Button)linearLayout.findViewById(R.id.btnOk);
            View viewLine = (View)linearLayout.findViewById(R.id.viewLine);
            LinearLayout llTitle  = (LinearLayout)linearLayout.findViewById(R.id.llTitle);
            // llAlertDialogMain.setLayoutParams(new LinearLayout.LayoutParams((int)(SmsTemplateConstants.DEVICE_DISPLAY_WIDTH-70*px), ViewGroup.LayoutParams.WRAP_CONTENT));
            llTitle.setVisibility(View.GONE);
            if(strTitle != null && !strTitle.equalsIgnoreCase(""))
            {
                llTitle.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                dialogtvTitle.setText("" + strTitle);
            }
            tvMessage.setText("" + strMessage);

            btnOk.setText(firstBtnName);
            btnOk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });

            try
            {
                alertDialog = alertBuilder.create();
                alertDialog.setView(linearLayout,0,0,0,0);
                alertDialog.setInverseBackgroundForced(true);
                alertDialog.show();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    class RunshowCustomDialogs implements Runnable {
        private String strTitle;// Title of the dialog
        private String strMessage;// Message to be shown in dialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;

        public RunshowCustomDialogs(String strTitle, String strMessage, String firstBtnName, String secondBtnName,String from)
        {


            this.strTitle 		= strTitle;
            this.strMessage 	= strMessage;
            this.firstBtnName 	= firstBtnName;
            this.secondBtnName 	= secondBtnName;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        @SuppressLint("NewApi")
        @Override
        public void run()
        {
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();

            alertBuilder = new AlertDialog.Builder(getActivity());
            alertBuilder.setCancelable(false);

            final LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_notification, null);
//            LinearLayout llAlertDialogMain  = (LinearLayout)linearLayout.findViewById(R.id.llAlertDialogMain);
            TextView dialogtvTitle = (TextView)linearLayout.findViewById(R.id.tvTitle);
            TextView tvMessage = (TextView)linearLayout.findViewById(R.id.tvMessage);
            Button btnYes = (Button)linearLayout.findViewById(R.id.btnYes);
            Button btnNo  = (Button)linearLayout.findViewById(R.id.btnNo);
            View viewLine = (View)linearLayout.findViewById(R.id.viewLine);
            LinearLayout llTitle  = (LinearLayout)linearLayout.findViewById(R.id.llTitle);
            // llAlertDialogMain.setLayoutParams(new LinearLayout.LayoutParams((int)(SmsTemplateConstants.DEVICE_DISPLAY_WIDTH-70*px), ViewGroup.LayoutParams.WRAP_CONTENT));
            llTitle.setVisibility(View.GONE);
            if(strTitle != null && !strTitle.equalsIgnoreCase(""))
            {
                llTitle.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                dialogtvTitle.setText("" + strTitle);
            }
            tvMessage.setText("" + strMessage);

            btnYes.setText(firstBtnName);
            btnYes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    onButtonYesClick(from);

                }
            });
            btnNo.setVisibility(View.GONE);
            if(secondBtnName != null && secondBtnName.trim().length() > 0)
            {
                btnNo.setText(secondBtnName);
                btnNo.setVisibility(View.VISIBLE);
                btnNo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        onButtonNoClick(from);

                    }
                });
            }

            try
            {
                alertDialog = alertBuilder.create();
                alertDialog.setView(linearLayout,0,0,0,0);
                alertDialog.setInverseBackgroundForced(true);
                alertDialog.show();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void onButtonYesClick(String from) {
        if(from.equalsIgnoreCase("Splash")){
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().finish();
        }else if(from.equalsIgnoreCase("Register")||from.equalsIgnoreCase("Seller") || from.equalsIgnoreCase("Comments")
                ){
            getActivity().getSupportFragmentManager().popBackStack();
        }
        else if(from.equalsIgnoreCase(getString(R.string.categories))){
                getActivity().getSupportFragmentManager().popBackStack();
            }else if(from.equalsIgnoreCase(getString(R.string.update_product))) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, new MyProductsFragment());
            fragmentTransaction.commit();
        }else if(from.equalsIgnoreCase("Save Address")|| from.equalsIgnoreCase("Edit Address")) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            fragmentManager.popBackStack();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentByTag("AddressFragment");
            if(fragment == null) {
                fragment = new AddressFragment();
               pushFragment(fragment,"AddressFragment");
            }
        }
    }

    public void onButtonNoClick(String from)
    {

    }
    /** Show Toast **/
    public void showToast(CharSequence text, int duration) {
        Toast.makeText(getActivity(), text, duration).show();
    }

    public void pushFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void pushFragment(Fragment fragment,String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
   public void replaceFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void replaceAddFragmentByTag(Fragment fragment,String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void replaceFragment(Fragment fragment,String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment, tag);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    /**
     * Hides virtual keyboard
     *
     */
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void navigatetoLoginScreen(){

        (getActivity()).getSupportFragmentManager().popBackStack();
        /*(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance(getActivity().getString(R.string.login)))
                .addToBackStack(null)
                .commit();*/
    }

    // call the updating code on the main thread,
    // so we can call this asynchronously
    public void updateHotCount(final int new_hot_number) {
        hot_number = new_hot_number;
        if (orderCount == null) return;
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {*/
                if (new_hot_number == 0)
                    orderCount.setVisibility(View.INVISIBLE);
                else {
                    orderCount.setVisibility(View.VISIBLE);
                    orderCount.setText(Integer.toString(new_hot_number));
                }
           // }
        //});
    }



}
