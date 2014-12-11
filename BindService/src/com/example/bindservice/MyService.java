package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
	
	private final IBinder mbinder=new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mbinder;
	}
	
	public class LocalBinder extends Binder{
		
		public MyService getService(){
			return MyService.this;
		}
		
	}
	
	public int findFactorial(int ip){
		int fact=1;
		for(int i=1;i<=ip;i++){
			fact=fact*i;
		}
		return fact;
	}
	
}
