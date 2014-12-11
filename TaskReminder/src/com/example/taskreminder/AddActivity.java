package com.example.taskreminder;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AddActivity extends Fragment {
	
	CalendarView calender;
	
	EditText date,time,note;
	Button add_image,add_note;
	ImageView image;
	EditText selected_date;
	
	LinearLayout timer;
	
	TextView image_uri,notifier;
	
	String s_image_uri="",notifier_t="";
	
	int cal_visibility=0,tmr_visibility=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.add_fragment,container,false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState!=null ){
			cal_visibility=savedInstanceState.getInt("calender_visibility");
			tmr_visibility=savedInstanceState.getInt("timer_visibility");
			s_image_uri=savedInstanceState.getString("img_uri");
			notifier_t=savedInstanceState.getString("notifier");
		}
			
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		note=(EditText) getActivity().findViewById(R.id.note);
		add_image=(Button) getActivity().findViewById(R.id.add_image);
		add_note=(Button) getActivity().findViewById(R.id.add_note);
		image=(ImageView) getActivity().findViewById(R.id.image);
		calender=(CalendarView) getActivity().findViewById(R.id.calender);
		timer=(LinearLayout) getActivity().findViewById(R.id.timer);
		
		image_uri=(TextView) getActivity().findViewById(R.id.image_uri);
		
		notifier=(TextView) getActivity().findViewById(R.id.notifier);
		
		if( cal_visibility==1 ){
			calender.setVisibility(View.VISIBLE);
			note.setVisibility(View.GONE);
			add_image.setVisibility(View.GONE);
			add_note.setVisibility(View.GONE);
			image.setVisibility(View.GONE);
			
		}
		
		if( tmr_visibility==1 ){
			timer.setVisibility(View.VISIBLE);
		}
		
		if( s_image_uri != null ){ 
			if( s_image_uri.length()>0 ){ 
				image_uri.setText(s_image_uri);//image_uri.setVisibility(View.VISIBLE);
				Uri uri=Uri.parse(s_image_uri);
				image.setImageURI(uri);
			}
		}
		
		if( notifier_t != null ){ notifier.setText(notifier_t); }
		
		date=(EditText) getActivity().findViewById(R.id.date);
		date.setInputType(0);
		time=(EditText) getActivity().findViewById(R.id.time);
		time.setInputType(0);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		calender=(CalendarView) getActivity().findViewById(R.id.calender);
		timer=(LinearLayout) getActivity().findViewById(R.id.timer);
		image_uri=(TextView) getActivity().findViewById(R.id.image_uri);
		notifier=(TextView) getActivity().findViewById(R.id.notifier);
		
		int cal_vis=0,tmr_vis=0;
		String img_uri="";
		if(image_uri.getText().toString().length() > 0 ){ img_uri=image_uri.getText().toString(); }
		
		if( calender.getVisibility() == View.VISIBLE ){ cal_vis=1; }
		if( timer.getVisibility() == View.VISIBLE ){ tmr_vis=1; }
		
		
		outState.putString("img_uri",img_uri);
		outState.putInt("calender_visibility",cal_vis);
		outState.putInt("timer_visibility",tmr_vis);
		outState.putString("notifier",notifier.getText().toString());
		
	}

}
