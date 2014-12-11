package com.example.calenderexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
 
public class MainActivity extends ActionBarActivity implements OnDateChangeListener {
 
	 private CalendarView calenderview=null;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 
		  super.onCreate(savedInstanceState);
		  
		  setContentView(R.layout.activity_main);
		  
		  calenderview=(CalendarView)findViewById(R.id.calender);
		  calenderview.setOnDateChangeListener(this);
	 }
 
	 @Override
	 public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2, int arg3) {
		 Toast.makeText(getBaseContext(), arg1+"/"+arg2+"/"+arg3, Toast.LENGTH_LONG).show();
	 }
}