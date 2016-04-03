package com.templatexuv.apresh.customerapp.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.database.DatabaseHelper;
import com.templatexuv.apresh.customerapp.model.Category;
import com.templatexuv.apresh.customerapp.model.ChildCategory;
import com.templatexuv.apresh.customerapp.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryDA {

	
public long insertCategory(Category category){
	synchronized (CustomerApplication.DATABASE_LOCK) {
		
		SQLiteDatabase sqLiteDatabase = null;
		long ret = 0;
		
		try {
			sqLiteDatabase  = DatabaseHelper.getInstance().openDatabase();
			
			ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.CATEGORY_ONE_ID, category.getCatOneId());
			cv.put(DatabaseHelper.CATEGORY_ONE_NAME, category.getCatOneName());

			ret = sqLiteDatabase.insert(DatabaseHelper.TABLE_CATEGORIES, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
            DatabaseHelper.getInstance().closeDatabase();
        }
		return ret;
	}
}

	public long insertSubCategory(SubCategory category){
		synchronized (CustomerApplication.DATABASE_LOCK) {

			SQLiteDatabase sqLiteDatabase = null;
			long ret = 0;

			try {
				sqLiteDatabase  = DatabaseHelper.getInstance().openDatabase();

				ContentValues cv = new ContentValues();
				cv.put(DatabaseHelper.CATEGORY_TWO_ID, category.getCatTwoId());
				cv.put(DatabaseHelper.CATEGORY_ONE_ID, category.getCatOneId());
				cv.put(DatabaseHelper.CATEGORY_TWO_NAME, category.getCatTwoName());

				ret = sqLiteDatabase.insert(DatabaseHelper.TABLE_SUB_CATEGORIES, null, cv);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				DatabaseHelper.getInstance().closeDatabase();
			}
			return ret;
		}
	}

	public long insertChildCategory(ChildCategory category){
		synchronized (CustomerApplication.DATABASE_LOCK) {

			SQLiteDatabase sqLiteDatabase = null;
			long ret = 0;

			try {
				sqLiteDatabase  = DatabaseHelper.getInstance().openDatabase();

				ContentValues cv = new ContentValues();
				cv.put(DatabaseHelper.CATEGORY_THREE_ID, category.getCatThreeId());
				cv.put(DatabaseHelper.CATEGORY_TWO_ID, category.getCatTwoId());
				cv.put(DatabaseHelper.CATEGORY_THREE_NAME, category.getCatThreeName());

				ret = sqLiteDatabase.insert(DatabaseHelper.TABLE_CHILD_CATEGORIES, null, cv);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				DatabaseHelper.getInstance().closeDatabase();
			}
			return ret;
		}
	}

public List<Category> selectAllCategory(){
	List<Category> categories = null;
	List<SubCategory> subcategories = null;
	List<ChildCategory> childcategories = null;
	SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
    Category category = null;
	SubCategory subcategory = null;
	ChildCategory childcategory = null;
	Cursor cr = null;
	Cursor cr2 = null;
	Cursor cr3 = null;
	try {
		 cr = sqLiteDatabase.rawQuery("SELECT * from "+DatabaseHelper.TABLE_CATEGORIES,null);
		if(cr.moveToFirst()){
            categories = new ArrayList<Category>();
			do{
                category = new Category();
                category.catOneId        = cr.getString(cr.getColumnIndex(DatabaseHelper.CATEGORY_ONE_ID));
                category.catOneName      = cr.getString(cr.getColumnIndex(DatabaseHelper.CATEGORY_ONE_NAME));

				cr2 = sqLiteDatabase.rawQuery("SELECT * from " + DatabaseHelper.TABLE_SUB_CATEGORIES + " WHERE "+DatabaseHelper.CATEGORY_ONE_ID+" ="+category.catOneId, null);
				if(cr2.moveToFirst()){
					subcategories = new ArrayList<SubCategory>();
					do{
						subcategory = new SubCategory();
						subcategory.catTwoId        = cr2.getString(cr2.getColumnIndex(DatabaseHelper.CATEGORY_TWO_ID));
						subcategory.catTwoName      = cr2.getString(cr2.getColumnIndex(DatabaseHelper.CATEGORY_TWO_NAME));

						cr3 = sqLiteDatabase.rawQuery("SELECT * from " + DatabaseHelper.TABLE_CHILD_CATEGORIES+ " WHERE "+DatabaseHelper.CATEGORY_TWO_ID+" ="+subcategory.catTwoId, null);
						if(cr3.moveToFirst()){
							childcategories = new ArrayList<ChildCategory>();
							do{
								childcategory = new ChildCategory();
								childcategory.catThreeId        = cr3.getString(cr3.getColumnIndex(DatabaseHelper.CATEGORY_THREE_ID));
								childcategory.catThreeName      = cr3.getString(cr3.getColumnIndex(DatabaseHelper.CATEGORY_THREE_NAME));

								childcategories.add(childcategory);
							}while(cr3.moveToNext());
							subcategory.setCatThrees(childcategories);
						}

						subcategories.add(subcategory);
					}while(cr2.moveToNext());
					category.setCatTwos(subcategories);
				}

                categories.add(category);
		}while(cr.moveToNext());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		if (cr != null && !cr.isClosed())
			cr.close();
		DatabaseHelper.getInstance().closeDatabase();
	}
	return categories;

}
}
