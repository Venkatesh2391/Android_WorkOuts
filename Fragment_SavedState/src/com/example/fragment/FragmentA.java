package com.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentA extends Fragment implements OnClickListener{
	
	Button b1;
	int counter=0;
	Communicator comm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_a,container,false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState!=null ){
			counter=savedInstanceState.getInt("counter");
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		b1=(Button) getActivity().findViewById(R.id.button1);
		
		comm=(Communicator)getActivity();
		
		b1.setOnClickListener(this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("counter",counter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		counter++;
		comm.respond("Button CLicked "+counter+" times");
		
	}
}
