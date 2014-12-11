package com.example.vcf;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	android.app.FragmentManager manager;
	
	TextView vc_title;
	
	Button vc_signin,confirm_signin,vc_signup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manager=getFragmentManager();
		
		vc_title=(TextView) findViewById(R.id.vc_title);
		
		place_ops_pan();
	}
	
	public void place_ops_pan(){
		
		Ops_Act oa=new Ops_Act();
		android.app.FragmentTransaction transaction = manager.beginTransaction();
		
		LinearLayout ls_hp2;
		ls_hp2=(LinearLayout) findViewById(R.id.ls_hold_pan2);
		
		if( ls_hp2 != null ){// && ls_hp2.isVisible() 
			transaction.add(R.id.ls_hold_pan2,oa,"OP");
		}
		else{
			transaction.add(R.id.hold_pan,oa,"OP");
		}
		
		transaction.commit();
		
	}
	
	public void click_Action(View v){
		
		LinearLayout ls_hp2=(LinearLayout) findViewById(R.id.ls_hold_pan2);
		
		vc_signin=(Button) findViewById(R.id.vc_signin);
		vc_signup=(Button) findViewById(R.id.vc_signup);
		confirm_signin=(Button) findViewById(R.id.confirm_signin);
		
		android.app.FragmentTransaction transaction = manager.beginTransaction();
		
		Ops_Act oa=new Ops_Act();
		Signin_Act sia=new Signin_Act();
		Signup_Act sua=new Signup_Act();
		
		
		Ops_Act rop=(Ops_Act) manager.findFragmentByTag("OP");
		Signin_Act rsip=(Signin_Act) manager.findFragmentByTag("SiP");
		Signup_Act rsup=(Signup_Act) manager.findFragmentByTag("SuP");
		
		if( rop !=null ){ transaction.remove(rop); }
		if( rsip !=null ){ transaction.remove(rsip); }
		if( rsup !=null ){ transaction.remove(rsup); }
		
		if( v==vc_signin ){
			
			vc_title.setText("Signin_Clickedd");
			
			if( ls_hp2 != null ){// && ls_hp2.isVisible() 
				transaction.add(R.id.ls_hold_pan1,oa,"OP");
				transaction.add(R.id.ls_hold_pan2,sia,"SiP");
				transaction.addToBackStack("SiP");
			}
			else{
				transaction.add(R.id.hold_pan,sia,"SiP");
				transaction.addToBackStack("SiP");
			}
		}
		
		else if ( v==vc_signup ){
			
			vc_title.setText("Signup Clicked");
			
			if( ls_hp2 != null ){// && ls_hp2.isVisible() 
				transaction.add(R.id.ls_hold_pan1,oa,"OP");
				transaction.add(R.id.ls_hold_pan2,sua,"SuP");
				transaction.addToBackStack("SuP");
			}
			else{
				transaction.add(R.id.hold_pan,sua,"SuP");
				transaction.addToBackStack("SuP");
			}
			
		}
		
		else if ( v==confirm_signin ){
			vc_title.setText("Confirm_Signin clicked");
		}
		
		
		transaction.commit();
		
		
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
