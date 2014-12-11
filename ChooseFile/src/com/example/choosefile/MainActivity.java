package com.example.choosefile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;







import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements android.view.View.OnClickListener{
	
	Button browse;
	TextView file_dir;
	ImageView image;
	
	private static final int FILE_SELECT_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		file_dir=(TextView) findViewById(R.id.file_dir);
		image=(ImageView) findViewById(R.id.image);
		
		browse=(Button) findViewById(R.id.browse);
		browse.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		showFileChooser();
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
	        	
	            Uri uri = data.getData();
	            String path="";
	            
	            Bitmap bitmap = null;
				try {
					InputStream is = getContentResolver().openInputStream(uri);
					bitmap=BitmapFactory.decodeStream(is);
					is.close();
				} 
				catch (FileNotFoundException e) {}
				catch (IOException e) {}  
				
	            file_dir.setText("Path= "+path);
	            image.setImageBitmap(bitmap);
	            //image.setImageURI(uri);
	            
		        /*Bitmap bitmap=BitmapFactory.decodeFile(path);
		        ByteArrayOutputStream bos=new ByteArrayOutputStream();
		        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		        u_image=bos.toByteArray();*/
		        
	        }
	        break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}
	


	public String getRealPathFromURI(Context context, Uri contentUri) {
	  Cursor cursor = null;
	  try { 
	    String[] proj = { MediaStore.Images.Media.DATA };
	    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	  } finally {
	    if (cursor != null) {
	      cursor.close();
	    }
	  }
	}


	
}
