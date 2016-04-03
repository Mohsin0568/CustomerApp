package com.templatexuv.apresh.customerapp.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.templatexuv.apresh.customerapp.CustomerApplication;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DB_NAME="DistributorApp";
	public static final int DB_VERSION=1;

	private AtomicInteger mOpenCounter = new AtomicInteger();

	private static DatabaseHelper dbHelper;
	private SQLiteDatabase mDatabase;

	public static final String TABLE_CATEGORIES="categories";
	public static final String TABLE_SUB_CATEGORIES="subcategories";
	public static final String TABLE_CHILD_CATEGORIES="childcategories";
	public static final String TABLE_CART="cart";

    //categories
	public static final String CATEGORY_ID = "category_id";
	public static final String CATEGORY_ONE_ID = "category_one_id";
    public static final String CATEGORY_ONE_NAME="category_one_name";

	//subcategories
	public static final String SUB_CATEGORY_ID = "sub_category_id";
	public static final String CATEGORY_TWO_ID = "category_two_id";
	public static final String CATEGORY_TWO_NAME="category_two_name";

	//childcategories
	public static final String CHILD_CATEGORY_ID = "child_category_id";
	public static final String CATEGORY_THREE_ID = "category_three_id";
	public static final String CATEGORY_THREE_NAME="category_three_name";

	//cart
	public static final String CART_ID = "cart_id";
	public static final String PRODUCT_DISPLAY_NAME = "product_display_name";
	public static final String PRODUCT_IMAGE_URL="product_image_url";
	public static final String PRODUCT_COST="product_cost";
	public static final String QUANTITY="quantity";
	public static final String SELLER_INFO="seller_info";
	public static final String DELIVERY_BY="delivery_by";

	public static final String CREATE_CATEGORIES_TABLE =
			"create table "+TABLE_CATEGORIES + "("
					+ CATEGORY_ID +" INTEGER PRIMARY KEY,"
					+ CATEGORY_ONE_ID +" TEXT ,"
					+ CATEGORY_ONE_NAME + " TEXT);";

	public static final String CREATE_SUB_CATEGORIES_TABLE =
			"create table "+TABLE_SUB_CATEGORIES + "("
					+ SUB_CATEGORY_ID +" INTEGER PRIMARY KEY,"
					+ CATEGORY_ONE_ID +" TEXT ,"
					+ CATEGORY_TWO_ID +" TEXT ,"
					+ CATEGORY_TWO_NAME + " TEXT);";

	public static final String CREATE_CHILD_CATEGORIES_TABLE =
			"create table "+TABLE_CHILD_CATEGORIES + "("
					+ CHILD_CATEGORY_ID +" INTEGER PRIMARY KEY,"
					+ CATEGORY_TWO_ID +" TEXT ,"
					+ CATEGORY_THREE_ID +" TEXT ,"
					+ CATEGORY_THREE_NAME + " TEXT);";

	public static final String CREATE_CART_TABLE =
			"create table "+TABLE_CART + "("
					+ CART_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ PRODUCT_DISPLAY_NAME +" TEXT ,"
					+ PRODUCT_IMAGE_URL +" TEXT,"
					+ PRODUCT_COST +" REAL ,"
					+ QUANTITY + " TEXT,"
					+ SELLER_INFO + " TEXT,"
					+ DELIVERY_BY + " TEXT);";

	public DatabaseHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CATEGORIES_TABLE);
		db.execSQL(CREATE_SUB_CATEGORIES_TABLE);
		db.execSQL(CREATE_CHILD_CATEGORIES_TABLE);
		db.execSQL(CREATE_CART_TABLE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public static synchronized DatabaseHelper getInstance() {
		if (dbHelper == null) {
			dbHelper = new DatabaseHelper(CustomerApplication.getContext());
		}
		return dbHelper;
	}

	public synchronized SQLiteDatabase openDatabase() {
		try {
			if(mOpenCounter.incrementAndGet() == 1) {
				// Opening new database
				mDatabase = dbHelper.getWritableDatabase();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDatabase;
	}

	public synchronized void closeDatabase() {
		try {
			if(mOpenCounter.decrementAndGet() == 0) {
				// Closing database
				if(mDatabase!=null && mDatabase.isOpen())
					mDatabase.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
