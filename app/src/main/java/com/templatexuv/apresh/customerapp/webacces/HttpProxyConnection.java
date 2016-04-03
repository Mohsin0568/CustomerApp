package com.templatexuv.apresh.customerapp.webacces;

import android.content.Context;
import android.util.Log;


import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.LogUtils;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class HttpProxyConnection {

	// Given a string representation of a URL, sets up a connection and gets
	// an String.
	public String sendRequest(int requestType,String urlString,
			String[] keys,Object[] values){
        String response = null;
		HttpURLConnection conn        = null;
		InputStream reader      = null;
		try {

            String bodyParams = "";

            ////Code for building json data
            if (requestType == 1){
                if (keys != null)
                    bodyParams = buildBodyParams(keys, values);
                urlString = urlString + bodyParams;
             }else{
                if (keys != null)
                    bodyParams = buildJsonBodyParams(keys, values);


            }
            LogUtils.verbose(LogUtils.APP_TAG, "URL: " + urlString);
            LogUtils.verbose(LogUtils.APP_TAG, "bodyParams: " + bodyParams);
            URL url = new URL(Constants.BASE_URL+urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(Constants.SOCKET_TIMEOUT /* milliseconds */);
			conn.setConnectTimeout(Constants.CONNECTION_TIMEOUT /* milliseconds */);

            if (requestType == 1){
                conn.setRequestMethod("GET");
            }else
            {
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os));
                writer.write(bodyParams);
                writer.flush();
                writer.close();
                os.close();
            }
			// Starts the query
			conn.connect();
			reader = new BufferedInputStream(conn.getInputStream());
		    response = getStringFromInputStream(reader);
            LogUtils.verbose(LogUtils.APP_TAG, "Response: " + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(conn != null)
				conn.disconnect();
		}

	 return response;
	}


    private String buildBodyParams(String[] keys,Object[] values){

         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append("?");
            for (int entry=0;entry<keys.length;entry++) {
                try{
                    String key = URLEncoder.encode(keys[entry] == null ? "" : keys[entry], "UTF-8");
                    Object value = URLEncoder.encode((String)values[entry] == null ? "" : (String)values[entry], "UTF-8");
                    stringBuilder.append(key).append("=");
                    stringBuilder.append(value).append("&");
               }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }


       return stringBuilder.toString();
    }

    private String buildJsonBodyParams(String[] keys,Object[] values){

        JSONObject jsonObject = new JSONObject();

        for (int entry=0;entry<keys.length;entry++) {
            try{

               // if(!keys[entry].equals("imagepath"))
               // jsonObject.put(keys[entry], values[entry]);
               // else{
                    String key = URLEncoder.encode(keys[entry] == null ? "" : keys[entry], "UTF-8");
                    String value = (String)values[entry];
                     value = URLEncoder.encode(value == null ? "" :value, "UTF-8");
                    jsonObject.put(key, value);
               // }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


        return jsonObject.toString();
    }


    public String readJsonFile(Context context,String filename) {


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Log.v("reader", stringBuilder.toString());

           return stringBuilder.toString();
        } catch (IOException e) {
            //log the exception
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
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

    public String uploadFileRequest(String sourceFileUri,int requestType,String urlString,
                                    String[] keys,Object[] values) {
        String response = null;
        HttpURLConnection conn        = null;
        InputStream reader      = null;
        try {

            String bodyParams = "";



            ////Code for building json data
            if (requestType == 1){
                if (keys != null)
                    bodyParams = buildBodyParams(keys, values);
                urlString = urlString + bodyParams;
            }else{
                if (keys != null)
                    bodyParams = buildJsonBodyParams(keys, values);
            }


            LogUtils.verbose(LogUtils.APP_TAG, "URL: " + urlString);
            LogUtils.verbose(LogUtils.APP_TAG, "bodyParams: " + bodyParams);
            URL url = new URL(Constants.BASE_URL+urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(Constants.SOCKET_TIMEOUT /* milliseconds */);
            conn.setConnectTimeout(Constants.CONNECTION_TIMEOUT /* milliseconds */);

                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os));
                writer.write(bodyParams);
                writer.flush();
                writer.close();
                os.close();

            // Starts the query
            conn.connect();
            reader = new BufferedInputStream(conn.getInputStream());
            response = getStringFromInputStream(reader);
            LogUtils.verbose(LogUtils.APP_TAG, "Response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(conn != null)
                conn.disconnect();
        }

        return response;
    }

    /*public String uploadFileRequest(String sourceFileUri,int requestType,String urlString,
                              String[] keys,Object[] values) {

        String response = null;
        InputStream reader      = null;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        try {

            String bodyParams = "";

            if (requestType == 1){
                if (keys != null)
                    bodyParams = buildBodyParams(keys, values);
                urlString = urlString + bodyParams;
            }else{
                if (keys != null)
                    bodyParams = buildJsonBodyParams(keys, values);


            }

            // open a URL connection to the Servlet
            URL url = new URL(Constants.BASE_URL+urlString);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");

            //Code for writing json data
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            //Code for writing  files
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", sourceFileUri);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os));
            LogUtils.verbose(LogUtils.APP_TAG, "bodyParams: " + bodyParams);
            writer.write(bodyParams);
            writer.flush();
            writer.close();

            File sourceFile = new File(sourceFileUri);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);



            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + sourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                fileInputStream.close();
                dos.flush();
                dos.close();


            reader = new BufferedInputStream(conn.getInputStream());
            response = getStringFromInputStream(reader);
            LogUtils.verbose(LogUtils.APP_TAG, "Response: " + response);



        } catch (MalformedURLException ex) {


            ex.printStackTrace();


            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            return null;
        } catch (Exception e) {


            e.printStackTrace();


            *//*Log.e("Upload file to server Exception", "Exception : "
                    + e.getMessage(), e);*//*
            return null;
        }

        return response;


    }*/


}