package com.example.jsonsample;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	HttpClient client;
	JSONObject jsonObj;
	
	TextView output,tweets;
	
	final static String URL_STRING="http://docs.blackberry.com/sampledata.json";//"http://www.discovered.us/products.json";//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		output=(TextView) findViewById(R.id.output);
		tweets=(TextView) findViewById(R.id.tweets);
		
		client=new DefaultHttpClient();
		
		GetTweets tweets=new GetTweets();
		tweets.execute();
		
	}
	
	public class GetTweets extends AsyncTask<Void, String, String>{
		
		@Override
		protected void onPreExecute() {
			output.setText("Executing wait");
		}
		
		protected String doInBackground(Void... params) {
			
			String jstr="";
			
			try{
				JSONArray jarray=tweets();
				
				if( jarray != null ){
					int count=jarray.length();
					
					publishProgress("JSON Length="+Integer.toString(count));
					
					JSONObject jobj=new JSONObject();
					
					for(int i=0;i<count;i++){
						jobj=jarray.getJSONObject(i);
						
						//publishProgress(jobj.toString());
						
						publishProgress("Vehicle Type : "+jobj.getString("vehicleType"));
						
						SystemClock.sleep(300);
						
						for(int j=0;j<jobj.getJSONArray("approvedOperators").length();j++){
							
							JSONObject jo=jobj.getJSONArray("approvedOperators").getJSONObject(j);
							
							publishProgress("Approved Operator: "+jo.getString("name")+" , Experience Points : "+jo.getString("experiencePoints"));
							SystemClock.sleep(400);
							
						}
					}
				}
				
				else{
					publishProgress("JSON Length=0");
				}
			}catch (JSONException e){ }
			
			return jstr;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			tweets.setText(values[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			output.setText("Executing Completed");
		}
	}
	
	public JSONArray tweets() {
		
		JSONArray jarray=null;
		
		try{
			StringBuilder builder=new StringBuilder(URL_STRING);
								// URL_STRING="http://docs.blackberry.com/sampledata.json"
			HttpGet get=new HttpGet(builder.toString());
			HttpResponse response;
			
			response=client.execute(get);
			int status=response.getStatusLine().getStatusCode();
			
			if( status==200 ){
				
				HttpEntity entity=response.getEntity();
				String data = EntityUtils.toString(entity);
				
				jarray=new JSONArray(data);
			}
		}catch(ClientProtocolException pe){}
		catch (JSONException e) {}
		catch(IOException ie){}
		
		return jarray;
	}
	
}
