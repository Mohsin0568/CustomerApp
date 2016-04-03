package com.templatexuv.apresh.customerapp.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class LogUtils 
{
	private static boolean isLogEnabled = true;
	public static final String SERVICE_LOG_TAG = "SmsTemplateServiceLog";
	public static final String APP_TAG = "SmsTemplate";
	
	public static void error(String tag, String msg)
	{
		//Errors should be print, no need this condition
		if(isLogEnabled)
		{
			if(msg != null)			
				Log.e(tag, msg);
			else
				Log.e(tag, "null");
		}
	}
	
	public static void info(String tag, String msg)
	{
		if(isLogEnabled)
		{
			if(msg != null)			
				Log.i(tag, msg);
			else
				Log.i(tag, "null");
		}
	}
	
	public static void verbose(String tag, String msg)
	{
		if(isLogEnabled)
		{
			if(msg != null)			
				Log.v(tag, msg);
			else
				Log.v(tag, "null");
		}
	}
	
	public static void debug(String tag, String msg)
	{
		if(isLogEnabled)
		{
			if(msg != null)			
				Log.d(tag, msg);
			else
				Log.d(tag, "null");
		}
	}
	
	/**
	 * This method stores InputStream data into a file called 'Response.xml' in Application cache directory 
	 * @param is
	 */
	public static void convertResponseToFile(InputStream is) throws IOException
	{
		 BufferedInputStream bis = new BufferedInputStream(is);
		 FileOutputStream fos = new FileOutputStream("data/data/com.templatexuv.apresh/Response.xml");
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 
		 byte byt[] = new byte[1024];
		 int noBytes;
		 
		 while((noBytes = bis.read(byt)) != -1)
			 bos.write(byt,0,noBytes);
		 
		 bos.flush();
		 bos.close();
		 fos.close();
		 bis.close();
	 }
	public static void writeToSdCard(InputStream is) throws IOException
	{
		 BufferedInputStream bis = new BufferedInputStream(is);
		 FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/request.xml");
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 
		 byte byt[] = new byte[1024];
		 int noBytes;
		 
		 while((noBytes = bis.read(byt)) != -1)
			 bos.write(byt,0,noBytes);
		 
		 bos.flush();
		 bos.close();
		 fos.close();
		 bis.close();
	 }
	
	public static String convertStreamToString(InputStream inputStream) throws IOException
	{
		//Initialize variables
		String responce = "";
		
		if (inputStream != null)
		{
			Writer writer = new StringWriter();
		    char[] buffer = new char[1024];
		    try
		    {
		       //Reader
		       Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		       int n;
		       while ((n = reader.read(buffer)) != -1)
		       {
		    	   //writing
		            writer.write(buffer, 0, n);
		       }
		       responce =  writer.toString();
		       writer.close();
		    }
		    finally 
		    {
		    	//closing InputStream
		    	inputStream.close();
		    }
		    
		    return responce;
		}
		else 
		{       
			return "";
		}
    }
}
