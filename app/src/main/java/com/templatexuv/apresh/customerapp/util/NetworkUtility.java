package com.templatexuv.apresh.customerapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Description Class : Checking Network Connections
 * @author Apresh
 *
 */
public class NetworkUtility 
{
	/**
	 * Method to check Network Connections 
	 * @param context
	 * @return boolean value
	 */
	public static boolean isNetworkConnectionAvailable(Context context)
	{
		boolean isNetworkConnectionAvailable = false;
		
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		
		if(activeNetworkInfo != null) 
		{
		    isNetworkConnectionAvailable = activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
		}
		return isNetworkConnectionAvailable;
	}
}
