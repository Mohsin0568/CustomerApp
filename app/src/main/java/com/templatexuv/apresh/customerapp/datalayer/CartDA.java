package com.templatexuv.apresh.customerapp.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.database.DatabaseHelper;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.model.Category;
import com.templatexuv.apresh.customerapp.model.ChildCategory;
import com.templatexuv.apresh.customerapp.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class CartDA {

	
public long insertCart(Cart cart){
	synchronized (CustomerApplication.DATABASE_LOCK) {
		
		SQLiteDatabase sqLiteDatabase = null;
		long ret = 0;
		
		try {
			sqLiteDatabase  = DatabaseHelper.getInstance().openDatabase();

			ContentValues cv = new ContentValues();
          /*  cv.put(DatabaseHelper.CART_ID, cart.getCart_id());
			cv.put(DatabaseHelper.PRODUCT_DISPLAY_NAME, cart.getProduct_display_name());
			cv.put(DatabaseHelper.PRODUCT_COST, cart.getProduct_cost());
			cv.put(DatabaseHelper.PRODUCT_IMAGE_URL, cart.getProduct_imageUrl());
			cv.put(DatabaseHelper.QUANTITY, cart.getQuantity());
			cv.put(DatabaseHelper.SELLER_INFO, cart.getSeller_info());
			cv.put(DatabaseHelper.DELIVERY_BY, cart.getDelivery_by());*/

			ret = sqLiteDatabase.insert(DatabaseHelper.TABLE_CART, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
            DatabaseHelper.getInstance().closeDatabase();
        }
		return ret;
	}
}


public List<Cart> selectAllCart(){
	List<Cart> carts = null;
	SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
	Cart cart = null;

	Cursor cr = null;

	try {
		 cr = sqLiteDatabase.rawQuery("SELECT * from "+DatabaseHelper.TABLE_CART,null);
		if(cr.moveToFirst()){
            carts = new ArrayList<Cart>();
			do{
                cart = new Cart();
				/*cart.cart_id	                = cr.getString(cr.getColumnIndex(DatabaseHelper.CART_ID));
                cart.product_display_name       = cr.getString(cr.getColumnIndex(DatabaseHelper.PRODUCT_DISPLAY_NAME));
				cart.product_imageUrl	        = cr.getString(cr.getColumnIndex(DatabaseHelper.PRODUCT_IMAGE_URL));
				cart.product_cost			    = cr.getDouble(cr.getColumnIndex(DatabaseHelper.PRODUCT_COST));
				cart.quantity 					= cr.getInt(cr.getColumnIndex(DatabaseHelper.QUANTITY));
				cart.seller_info 				= cr.getString(cr.getColumnIndex(DatabaseHelper.SELLER_INFO));
				cart.delivery_by 				= cr.getString(cr.getColumnIndex(DatabaseHelper.DELIVERY_BY));
*/
                carts.add(cart);
		}while(cr.moveToNext());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		if (cr != null && !cr.isClosed())
			cr.close();
		DatabaseHelper.getInstance().closeDatabase();
	}
	return carts;

}

	public long checkCart(String productName){
		long count =0;
		SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
		Cart cart = null;

		Cursor cr = null;

		try {
			cr = sqLiteDatabase.query(DatabaseHelper.TABLE_CART,null,DatabaseHelper.PRODUCT_DISPLAY_NAME +"='"+productName+"'",null,null,null,null);
			count = cr.getCount();
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cr != null && !cr.isClosed())
				cr.close();
			DatabaseHelper.getInstance().closeDatabase();
		}
		return count;

	}

	public long deleteCart(int cartId){

		SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();

		long ret=0;
		try {
			ret = sqLiteDatabase.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.CART_ID + "=" + cartId, null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			DatabaseHelper.getInstance().closeDatabase();
		}
		return ret;

	}


}
