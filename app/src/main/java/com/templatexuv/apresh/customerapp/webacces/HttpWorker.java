package com.templatexuv.apresh.customerapp.webacces;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.MainActivity;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.model.BaseModel;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.NetworkUtility;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpWorker extends AsyncTask<List,Void,Response> {
    List params ;
    DataListener listener ;
    Context context;
    boolean isNetwork;
    private Dialog dialog;
    ProgressBar progressBar;

    public HttpWorker(DataListener listener,Context context,ProgressBar progressBar) {
        super();
        this.context = context;
        this.listener = listener;
        this.progressBar = progressBar;
    }
    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (progressBar != null && progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        listener.dataDownloaded(response);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(!NetworkUtility.isNetworkConnectionAvailable(context))
        {
            Toast.makeText(context,context.getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }else{
            isNetwork =true;
            if (progressBar != null && !progressBar.isShown()) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
    private void showLoader(){
        try {
            if (dialog == null) {
                dialog = new Dialog(context,
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideloader() {
        try {
             if (dialog != null && dialog.isShowing())
                 dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response doInBackground(List... lists) {
        Log.d("Inside", "DoInBackgroud Method");
        Response response = null;
        if(isNetwork) {
            HttpURLConnection connection;
            int methodType = 1;
            InputStream reader;
            String jsonresponse = null;
            String url = lists[0].get(0).toString();
            String method = lists[0].get(1).toString();
            int servicemethod = (int) lists[0].get(2);
            String bodyParams = null;
            if (lists[0].size() > 3) {
                String[] keys = (String[]) lists[0].get(3);
                Object[] values = (Object[]) lists[0].get(4);

                if (method.equals("GET")) {
                    bodyParams = Parameters.constructBodyParameters(keys, values);
                    if(bodyParams!=null)
                    url = url + bodyParams;
                    methodType = 1;
                    Log.d("Mehod Type", "GET");
                } else if (method.equals("POST")) {
                    bodyParams = Parameters.constructJsonParameters(keys, values);
                    methodType = 2;
                    Log.d("Mehod Type", "POST");
                }

            }
            try {
                Log.v("URL", "" + Constants.BASE_URL + url+"Body"+bodyParams);
                URL connUrl = new URL(Constants.BASE_URL + url);
                connection = (HttpURLConnection) connUrl.openConnection();
                connection.setReadTimeout(Constants.SOCKET_TIMEOUT /* milliseconds */);
                connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT /* milliseconds */);

                if (methodType == 1) {
                    connection.setRequestMethod("GET");
                } else {
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    if (bodyParams != null)
                        bw.write(bodyParams);
                    bw.flush();
                    bw.close();
                    os.close();
                }

                connection.connect();
                reader = new BufferedInputStream(connection.getInputStream());
                jsonresponse = getStringFromInputStream(reader);
                Log.v("Response", jsonresponse);

            } catch (Exception e) {
                Log.d("Efanta Exception", "Got exception in doInBackground method " + e);
                e.printStackTrace();
            }

            response = new Response(servicemethod, context.getString(R.string.unable_to_connect_server_please_try_again),true,null);
            ResponseHandler handler = new ResponseHandler();
            if (jsonresponse != null)
                handler.parseResponse(servicemethod, jsonresponse, response);
        }
        return response;
    }

    private String getStringFromInputStream(InputStream inputStream)
            throws IOException {

        byte[] b = new byte[1024];
        int size;
        StringBuilder returnString = new StringBuilder();
        while ((size = inputStream.read(b)) != -1) {
            returnString.append(new String(b, 0, size));

        }
        return returnString.toString();

    }
}
