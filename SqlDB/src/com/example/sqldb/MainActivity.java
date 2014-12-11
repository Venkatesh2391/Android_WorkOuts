package com.example.sqldb;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.example.sqldb.R;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	LinearLayout main_pan;
	Button save,load,upload;
	EditText id,name;
	
	DataHandler handler;
	
	byte []image;
	String upload_file_path;
	Uri upload_file_uri;
	
	private static final int FILE_SELECT_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		main_pan=(LinearLayout) findViewById(R.id.entries_layout);
		save=(Button) findViewById(R.id.save);
		load=(Button) findViewById(R.id.load);
		
		upload=(Button) findViewById(R.id.upload);
		id=(EditText) findViewById(R.id.id);
		name=(EditText) findViewById(R.id.name);
		
		save.setOnClickListener(this);
		load.setOnClickListener(this);
		upload.setOnClickListener(this);
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
	        	upload_file_uri = data.getData();
	            Toast.makeText(this,"File Uri: " + upload_file_uri.toString(),Toast.LENGTH_LONG).show();
	        }
	        break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if( v==upload ){
			upload_file_uri=null;
			showFileChooser();
		}
		
		if( v== save ){
			String u_email=id.getText().toString();
			String u_name=name.getText().toString();
			
			byte[] u_image=null;
			
			if(upload_file_uri != null){
				Bitmap bitmap = null;
				try {
					InputStream is = getContentResolver().openInputStream(upload_file_uri);
					
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
			        u_image=bos.toByteArray();
				}
			}
			
			handler=new DataHandler(getBaseContext());
			handler.open();
			
			long id=handler.insertData(u_email, u_name,u_image);
			Toast.makeText(getBaseContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
			handler.close();
			
			upload_file_uri=null;
		}
		
		else if( v==load ){
			
			handler=new DataHandler(getBaseContext());
			handler.open();
			
			int no_of_entries=0;
			
			Cursor c=handler.returnData();
			
			if( c.moveToFirst()){
				do{
					display_db(c.getString(0),c.getString(1),c.getBlob(2));
					no_of_entries++;
				}while(c.moveToNext());
			}
			
			handler.close();
			Toast.makeText(getBaseContext(), "Data Retrieved Successfully - "+no_of_entries, Toast.LENGTH_LONG).show();
			
		}
		
	}
	
	
	public void display_db(String name,String email, byte[] bimg){
		
		LinearLayout data_pan = new LinearLayout(this);
		data_pan.setOrientation(LinearLayout.VERTICAL);
		data_pan.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		data_pan.setBackgroundColor(Color.rgb(20, 120, 100));
	    
		TextView name_email=new TextView(this);
		name_email.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		name_email.setText("Email : "+email+" ,  Name : "+name);
		//name_email.setTextSize(20);
		
		data_pan.addView(name_email);
		
		if( bimg != null){
			ImageView image_v=new ImageView(this);
			image_v.setLayoutParams(new LinearLayout.LayoutParams(100,120));
			
			Bitmap bm=BitmapFactory.decodeByteArray(bimg, 0, bimg.length);
			image_v.setImageBitmap(bm);
			
			data_pan.addView(image_v);
		}
		
		main_pan.addView(data_pan);
		
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
