package com.example.autorefresh;

import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	String time;
	
	TextView time_display;
	
	Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    time_display=(TextView) findViewById(R.id.time_display);
	    
	    Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
	    time_display.setText("Time : "+today.format("%k:%M:%S"));
	    
	    this.mHandler = new Handler();
	    m_Runnable.run();
	}
	
	private final Runnable m_Runnable = new Runnable() {
		public void run(){
			
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			
			time=today.format("%k:%M:%S");
			
			time_display.setText("Time : "+time);
	            
			//Toast.makeText(MainActivity.this,time,Toast.LENGTH_SHORT).show();
	        MainActivity.this.mHandler.postDelayed(m_Runnable,1000);            
	    }
	};
}