package com.androidhive.googleplacesandmaps;


import java.util.HashMap;

import cognito.UserPref.Planet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import androidlistview.AndroidListViewActivity;
import androidlistview.MainActivity5;


public class InitialActivity extends Activity {
    private EditText city;
    private Button submit;
    private CheckBox current_loc;
    double latitude,longitude;
    String types = "cafe|restaurant";
    double radius = 1000; 
    PlacesList nearPlaces;
    GooglePlaces googlePlaces;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        submit=(Button)findViewById(R.id.submitbtn);
        submit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                try{
                	Intent i = new Intent(getApplicationContext(),
        					SearchPageActivity.class);
                	Bundle bu = new Bundle();
                    current_loc = (CheckBox) findViewById(R.id.checkBox1);
                    if(!current_loc.isChecked()) {
                        city = (EditText) findViewById(R.id.city_name);
                        String cityname = city.getText().toString();
                        LoadPlaces l1=new LoadPlaces();
                        Log.d("TAGin","Before execute");
                        Geolatlong locationAddress = new Geolatlong();
                        locationAddress.getAddressFromLocation(cityname,getApplicationContext(), new GeocoderHandler());
                        //l1.execute(cityname);
                        Log.d("TAGin","After execute");
                      //  Geolatlong citylatlong=new Geolatlong();
                        
                        //Geolatlong locationAddress = new Geolatlong();
                        //locationAddress.getAddressFromLocation(cityname,getApplicationContext(), new GeocoderHandler());
                        double a = locationAddress.lat;
                        double b= locationAddress.lon;
                        locationAddress.flag=1;
                        Log.d("Afterwhile",""+a);
                        bu.putDouble("la", a);
                        bu.putDouble("lo", b);
                        bu.putBoolean("check", false);
                        i.putExtras(bu);
                        startActivity(i);
                        locationAddress.lat=0.0;
                        locationAddress.lon=0.0;
                        //System.out.println(a);
                        //System.out.println(b);
                       // System.out.println(cityname);
                     	
                        
                       
                        }
                    
                    
                    else
                    {
                    	bu.putBoolean("check", true);
                    	i.putExtras(bu);
                        startActivity(i);
                    	Log.w("tag","enter");
                       
                    }
                   
                }
                catch(Exception ex)
                {
                    //content.setText(" url exeption! " );
                }
            }

        });
					
        final EditText time_edit=(EditText) findViewById(R.id.time_edit);
        Button packages=(Button) findViewById(R.id.time_submit);
        packages.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(InitialActivity.this,AndroidListViewActivity.class);
				i.putExtra("time", time_edit.getText());
				startActivity(i);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_main1, menu);
        return true;
    }
     
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.menu_settings:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
        	Intent i=new Intent(InitialActivity.this,SettingActivity.class);
	    	  startActivity(i);
          
          return true;


        default:
            return super.onOptionsItemSelected(item);
        }
    }
    public void showtoast(Address address,Context context)
    {
        Toast.makeText(context, "Your Location is - \nLat: " + address.getLatitude() + "\nLong: " + address.getLongitude(), Toast.LENGTH_LONG).show();
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            //latLongTV.setText(locationAddress);
        }
    }
    
    class LoadPlaces extends AsyncTask<String, String, Geolatlong> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		/**
		 * getting Places JSON
		 * */
		protected Geolatlong doInBackground(String... args) {
			// creating Places class object
			String cityname=args[0];
			
			Log.d("In async",cityname);
			Geolatlong locationAddress = new Geolatlong();
			try {
				 
                 	

			} catch (Exception e) {
				e.printStackTrace();
			}
			return locationAddress;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * and show the data in UI
		 * Always use runOnUiThread(new Runnable()) to update UI from background
		 * thread, otherwise you will get error
		 * **/
		protected void onPostExecute(Geolatlong locationAddress) {
			// dismiss the dialog after getting all products
			
			// updating UI from Background Thread
			
			

		}

	}
}

