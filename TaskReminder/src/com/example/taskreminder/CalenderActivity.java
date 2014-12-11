package com.example.taskreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class CalenderActivity extends Activity implements OnDateChangeListener{
	
	CalendarView calender=null;
	
	EditText selected_date;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 
		  super.onCreate(savedInstanceState);
		  
		  setContentView(R.layout.calenderr_fragment);
		  
		  calender=(CalendarView) findViewById(R.id.calender1);
		  calender.setOnDateChangeListener(this);
		  
		  selected_date=(EditText) findViewById(R.id.selected_date);
	 }

	 @Override
	 public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2, int arg3) {
		 //Toast.makeText(getBaseContext(), arg1+"/"+arg2+"/"+arg3, Toast.LENGTH_LONG).show();
		 
		 String date=arg1+"/"+arg2+"/"+arg3;
		 
		 selected_date.setText(date);
		 
		// Intent intent = new Intent(CalenderActivity.this,MainActivity.class);
		// intent.putExtra("date", date);
		// startActivity(intent);
		 
	 }

}
