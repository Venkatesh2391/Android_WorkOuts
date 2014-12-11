package com.example.taskreminder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class MainActivity extends ActionBarActivity implements OnDateChangeListener{
	
	android.app.FragmentManager manager;
	
	LinearLayout add_reminder_pan,view_reminders_pan,ls_add_reminder_pan,ls_view_reminder_pan,my_reminders_pan,timer;
	
	EditText date,time,note;
	Button add_image,add_note,set_time;
	ImageView image;
	TextView image_uri,notifier;
	
	Spinner hours,minutes,am_pm;
	
	CalendarView calender=null;
	
	String selected_date="",selected_time="";

	DataHandler handler;
	
	String c_date="",c_time="",c_note="",c_image_uri="";
	
	private static final int FILE_SELECT_CODE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AddActivity aAct=new AddActivity();
		ViewActivity vAct=new ViewActivity();
		
		add_reminder_pan=(LinearLayout) findViewById(R.id.add_reminder_pan);
		view_reminders_pan=(LinearLayout) findViewById(R.id.view_reminders_pan);
		
		manager=getFragmentManager();
		android.app.FragmentTransaction transaction = manager.beginTransaction();
		
		AddActivity raAct=(AddActivity) manager.findFragmentByTag("AddReminder");
		ViewActivity rvAct=(ViewActivity) manager.findFragmentByTag("ViewReminder");
		
		if( raAct ==null  && rvAct ==null ){
			transaction.add(R.id.add_reminder_pan,aAct, "AddReminder");
			transaction.add(R.id.view_reminders_pan,vAct, "ViewReminder");
			transaction.addToBackStack("AddReminder");
			transaction.addToBackStack("ViewReminder");
			transaction.commit();
		}
		
		new load_reminders().execute();
		
	}
	
	class load_reminders extends AsyncTask<Void, Void, Void> {
		
		String r_date="",r_time="",r_note="";
		byte []r_image=null;
		int no_of_entries=0;
		
		@Override
		protected Void doInBackground(Void... params) {
			handler=new DataHandler(getBaseContext());
			handler.open();
			Cursor c=handler.returnData();
			
			if( c.moveToFirst()){
				do{
					//display_reminder(c.getString(0),c.getString(1),c.getString(2),c.getBlob(3));
					r_date=c.getString(0);r_time=c.getString(1);r_note=c.getString(2);r_image=c.getBlob(3);
					no_of_entries++;
					publishProgress();
					SystemClock.sleep(1000);
				}while(c.moveToNext());
			}
			
			handler.close();
			
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			display_reminder(r_date,r_time,r_note,r_image);
			Toast.makeText(getBaseContext(),"Reminders = "+no_of_entries+" , Date: "+r_date+" , Time: "+r_time+" , Note: "+r_note, Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			
		}
	}
	
	public void display_reminder(String date,String time,String note,byte[] image){
		
		my_reminders_pan=(LinearLayout) findViewById(R.id.my_reminders_pan);
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(5,15,5, 0);
		
		LinearLayout data_pan = new LinearLayout(this);
		data_pan.setOrientation(LinearLayout.VERTICAL);
		data_pan.setLayoutParams(layoutParams);
		data_pan.setBackgroundColor(Color.rgb(20, 120, 100));
	    
		TextView name_email=new TextView(this);
		name_email.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		name_email.setText(" Date: "+date+" , Time: "+time+" , Note: "+note);
		//name_email.setTextSize(20);
		
		data_pan.addView(name_email);
		
		my_reminders_pan.addView(data_pan);
		
	}
	
	public void click_Action(View v){
		
		date=(EditText) findViewById(R.id.date);
		time=(EditText) findViewById(R.id.time);
		
		note=(EditText) findViewById(R.id.note);
		
		set_time=(Button)findViewById(R.id.set_time);
		
		add_image=(Button) findViewById(R.id.add_image);
		add_note=(Button) findViewById(R.id.add_note);
		
		image=(ImageView) findViewById(R.id.image);
		
		image_uri=(TextView) findViewById(R.id.image_uri);
		notifier=(TextView) findViewById(R.id.notifier);
		
		timer=(LinearLayout) findViewById(R.id.timer);
		
		hours=(Spinner) findViewById(R.id.hours);
		minutes=(Spinner) findViewById(R.id.minutes);
		am_pm=(Spinner) findViewById(R.id.am_pm);
		
		if( v==date ){
			
			Intent intent = new Intent(MainActivity.this,CalenderActivity.class);
			
			calender=(CalendarView) findViewById(R.id.calender);
			calender.setOnDateChangeListener(this);
			
			if( calender.getVisibility() != View.VISIBLE ){
				calender.setVisibility(View.VISIBLE);
				note.setVisibility(View.GONE);
				add_image.setVisibility(View.GONE);
				add_note.setVisibility(View.GONE);
				image.setVisibility(View.GONE);
			}
			else{
				calender.setVisibility(View.GONE);
				note.setVisibility(View.VISIBLE);
				add_image.setVisibility(View.VISIBLE);
				add_note.setVisibility(View.VISIBLE);
				image.setVisibility(View.VISIBLE);
			}
		}
		
		else if( v==time ){
			if( timer.getVisibility() != View.VISIBLE ){
				timer.setVisibility(View.VISIBLE);
			}
			else{	timer.setVisibility(View.GONE);	}
		}	
		
		else if( v==set_time ){
			
			if( hours.getSelectedItem().toString().equals("HH")==false && minutes.getSelectedItem().toString().equals("MM")==false ){
				
				int h=Integer.parseInt(hours.getSelectedItem().toString());
				int m=Integer.parseInt(minutes.getSelectedItem().toString());
				
				time.setText(h+":"+m+" "+am_pm.getSelectedItem().toString());
				
				if( am_pm.getSelectedItem().toString().equals("PM") ){ h=h+12; }
				selected_time=h+":"+m;
				
				timer.setVisibility(View.GONE);
			}
		}
		
		else if( v==add_image ){
			showFileChooser();
		}
		
		else if( v==add_note ){
			c_date=date.getText().toString();
			c_time=selected_time;
			c_note=note.getText().toString();
			c_image_uri=image_uri.getText().toString();
			
			if( c_date.length()>0 && c_time.length()>0 ){
				notifier.setText("Saving plz. wait...");
				new save_into_database().execute();
				
				image.setImageResource(0);
				image_uri.setText("");
				note.setText("");date.setText("");time.setText("Set Time");;
			}
			else{
				notifier.setText("Error: plz provide date,time");
			}
			
		}
	}
	
	class save_into_database extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			handler=new DataHandler(getBaseContext());
			handler.open();
			
			byte[] c_image=null;
			Bitmap bitmap = null;
			try {
				InputStream is = getContentResolver().openInputStream(Uri.parse(c_image_uri));
				if( is != null ){
					bitmap=BitmapFactory.decodeStream(is);
				}
				is.close();
			} 
			catch (FileNotFoundException e) {}
			catch (IOException e) {}  
			
			if( bitmap != null){
				ByteArrayOutputStream bos=new ByteArrayOutputStream();
		        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		        c_image=bos.toByteArray();
			}
			
			long id=handler.insertData(c_date,c_time,c_note,c_image);
			handler.close();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			notifier.setText("Reminder Successfully Saved");
			SystemClock.sleep(1000);
			notifier.setText(" ");
		}
		
		
	}
	
	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
	    intent.setType("*/*"); 
	    intent.addCategory(Intent.CATEGORY_OPENABLE);

	    try {
	        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload."),FILE_SELECT_CODE);
	    } catch (android.content.ActivityNotFoundException ex) {
	        Toast.makeText(this, "Please install a File Manager.",Toast.LENGTH_SHORT).show();
	    }
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch (requestCode) {
	        case FILE_SELECT_CODE:
	        if (resultCode == RESULT_OK) {
	            Uri uri = data.getData();
	            image_uri.setText(uri.toString());//image_uri.setVisibility(View.VISIBLE);
	            image.setImageURI(uri);
	        }
	        break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {
		selected_date=dayOfMonth+"/"+month+"/"+year;
		date.setText(selected_date);
		calender.setVisibility(View.GONE);
		note.setVisibility(View.VISIBLE);
		add_image.setVisibility(View.VISIBLE);
		add_note.setVisibility(View.VISIBLE);
		image.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
