package com.example.bindservice;

import com.example.bindservice.MyService.LocalBinder;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	MyService mservice;
	boolean status;
	
	Button start,stop,factorial;
	EditText number;
	TextView output;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		start=(Button) findViewById(R.id.start);
		stop=(Button) findViewById(R.id.stop);
		factorial=(Button) findViewById(R.id.factorial);
		number=(EditText) findViewById(R.id.number);
		output=(TextView) findViewById(R.id.output);
		
	}
	
	private ServiceConnection sc=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			LocalBinder binder=(LocalBinder)service;
			mservice=binder.getService();
			status=true;
			
		}
	};
	
	public void click_Action(View v){
		
		if( v==start ){
			
			Intent i=new Intent(this,MyService.class);
			bindService(i, sc, Context.BIND_AUTO_CREATE);
			status=true;
			Toast.makeText(getBaseContext(), "Service Binded", Toast.LENGTH_LONG).show();
		}
		
		else if( v==stop ){
			
			if( status==true ){
				unbindService(sc);
				status=false;
				Toast.makeText(getBaseContext(), "Service UnBinded", Toast.LENGTH_LONG).show();
			}
		}
		
		else if( v==factorial ){
			if( status ){
				int num=Integer.parseInt(number.getText().toString());
				int res=mservice.findFactorial(num);
				output.setText("Factorial = "+res);
				Toast.makeText(getBaseContext(), "Factorial = "+res, Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(getBaseContext(), "First Bind the Service", Toast.LENGTH_LONG).show();
			}
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
