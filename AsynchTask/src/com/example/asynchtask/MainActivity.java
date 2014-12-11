package com.example.asynchtask;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	Button start_process;
	TextView output;
	
	Button new_note;
	
	LinearLayout hold_pan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		start_process=(Button) findViewById(R.id.start_process);
		output=(TextView) findViewById(R.id.output);
		
		hold_pan=(LinearLayout) findViewById(R.id.hold_pan);
	}
	
	public void click_Action(View v){
		
		if( v==start_process ) {
			
			output.setText("Operation in Progress plz.wait......");
			
			new long_operation().execute();
		}
	}
	
	public void add_Button(String ip){
		
		LayoutParams dimen=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		dimen.setMargins(10,10,0,0);
		
		Button new_note=new Button(this);
		
		new_note.setLayoutParams(dimen);
		new_note.setText(ip);
		
		hold_pan.addView(new_note);
		
	}
	
	
	
class long_operation extends AsyncTask<Void, Integer, String> {
		
	 	@Override
	 	protected void onPreExecute() {}
	 
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			for (int i = 1; i <= 10; i++) {
			    publishProgress(i);
	            SystemClock.sleep(1000);
            }
            return "Executed";
		}
		
		@Override
        protected void onProgressUpdate(Integer... values) {
			output.setText("Completed "+(values[0]*10)+" %");
			add_Button("Added N-"+values[0]);
		}
		
		@Override
        protected void onPostExecute(String result) {
            output.setText(result);
        }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
