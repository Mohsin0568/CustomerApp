package com.templatexuv.apresh.customerapp.webacces;


public class Response 
{
	public int method;
	public boolean isError;
	public String message;
	public Object data;

	public Response(int method,String message, boolean isError, Object data)
	{
		this.method = method;
		this.message = message;
		this.isError = isError;
		this.data = data;
	}
}
