package com.example.sqldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
	
	public static final String NAME="name";
	public static final String EMAIL="email";
	public static final String TABLE_NAME="mytable";
	public static final String DATABASE_NAME="mydatabase";
	public static final int DATABASE_VERSION=1;
	
	public static final String IMAGE="image";
	//public static final String TAG="image_tag";
	
	public static final String TABLE_CREATE="create table mytable (name text not null,email text not null,image blob);";
	
	DataBaseHelper dbhelper;
	Context ctx;
	
	SQLiteDatabase db;
	
	public DataHandler(Context c){
		ctx=c;
		dbhelper=new DataBaseHelper(ctx);
	}
	
	private static class DataBaseHelper extends SQLiteOpenHelper{
		
		public DataBaseHelper(Context ct) {
			// TODO Auto-generated constructor stub
			super(ct,DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			try{
				db.execSQL(TABLE_CREATE);
			}catch(SQLException e){}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			db.execSQL("DROP TABLE IF EXISTS mytable");
			onCreate(db);
		}
	}//innerclass
	
	public DataHandler open(){
		db=dbhelper.getWritableDatabase();
		return this;
	}
	public void close(){
		dbhelper.close();
	}
	
	public long insertData(String email,String name,byte[] image){
		
		ContentValues content = new ContentValues();
		content.put(EMAIL, email);
		content.put(NAME, name);
		content.put(IMAGE, image);
		return db.insertOrThrow(TABLE_NAME, null, content);
	}
	
	public Cursor returnData(){
		
		return db.query(TABLE_NAME, new String[] {NAME,EMAIL,IMAGE}, null, null, null, null, null);
		
	}

	

}
