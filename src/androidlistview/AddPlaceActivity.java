package androidlistview;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidhive.googleplacesandmaps.R;
import com.google.gson.Gson;

public class AddPlaceActivity extends Activity implements OnItemSelectedListener{
	private Spinner s;
	private EditText a,b,c,d,e;
	private Button f;
	String placename,type;
	double latitude,longitude;
	private Insertclass o;
	int mintime,maxtime;
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplace);
		addItemsOnSpinner();
		Log.d("AddPlaceActivity","hello gundu");
		a = (EditText) findViewById(R.id.placename);
		b = (EditText) findViewById(R.id.latitude);
		c = (EditText) findViewById(R.id.longitude);
		d = (EditText) findViewById(R.id.mintimetaken);
		e = (EditText) findViewById(R.id.maxtimetaken);
		f = (Button) findViewById(R.id.button1);
		a.setText("Pune");
		b.setText("18.5203");
		c.setText("73.8567");
		d.setText("2");
		e.setText("10");
		f.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				placename=a.getText().toString();
				latitude=Double.parseDouble(b.getText().toString());
				longitude=Double.parseDouble(c.getText().toString());
				mintime=Integer.parseInt(d.getText().toString());
				maxtime=Integer.parseInt(e.getText().toString());
				LongOperation lo=new LongOperation();
				lo.execute();
			}
		});
		
	  }
	
	private void addItemsOnSpinner() {
		// TODO Auto-generated method stub
		s = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,R.array.types);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			s.setAdapter(dataAdapter);
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		 type = parent.getItemAtPosition(position).toString();
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	 private class LongOperation extends AsyncTask<Void, Void, Void> {
		  ProgressDialog pdia;
		  String url_select = "http://myfirstelasticbeans-env007.elasticbeanstalk.com/getdetail";
		private InputStream inputStream=null;
		 @Override
	    	protected void onPreExecute() {
	    		// TODO Auto-generated method stub
	    		super.onPreExecute();
	    		
	    		pdia=ProgressDialog.show(AddPlaceActivity.this,"Progess","Connecting...");
	    		
	    	}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 HttpClient httpClient = new DefaultHttpClient();
			 HttpPost httppost = new HttpPost(url_select);
             httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
             Insertclass in=new Insertclass();
             Gson gson = new Gson();
             String json = new Gson().toJson(s);
             StringEntity se = null;
			try {
				se = new StringEntity(json);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             httppost.setEntity(se);
             try {
            	 HttpResponse httpResponse =httpClient.execute(httppost);
            	 inputStream = httpResponse.getEntity().getContent();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             if(inputStream == null)
            	 System.out.println("Null");
			return null;
		}
		 
	 }
	

}
