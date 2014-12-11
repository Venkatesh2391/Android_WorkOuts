package com.example.taskreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
	
	public static final String DATE="date";
	public static final String TIME="time";
	public static final String NOTE="note";
	public static final String TABLE_NAME="tasks_table";
	public static final String DATABASE_NAME="tasks_database";
	public static final int DATABASE_VERSION=1;
	
	public static final String IMAGE="image";
	
	public static final String TABLE_CREATE="create table tasks_table (date text not null,time text not null,note text not null,image blob);";
	
	DataBaseHelper dbhelper;
	Context ctx;
	
	SQLiteDatabase db;
	
	public DataHandler(Context c){
		ctx=c;
		dbhelper=new DataBaseHelper(ctx);
	}
	
	private static class DataBaseHelper extends SQLiteOpenHelper{
		
		public DataBaseHelper(Context ct) {
			super(ct,DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				db.execSQL(TABLE_CREATE);
			}catch(SQLException e){}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
	
	public long insertData(String date,String time,String note,byte[] image){
		
		ContentValues content = new ContentValues();
		content.put(DATE, date);
		content.put(TIME, time);
		content.put(NOTE, note);
		content.put(IMAGE, image);
		return db.insertOrThrow(TABLE_NAME, null, content);
	}
	
	public Cursor returnData(){
		return db.query(TABLE_NAME, new String[] {DATE,TIME,NOTE,IMAGE}, null, null, null, null, null);
	}

}
