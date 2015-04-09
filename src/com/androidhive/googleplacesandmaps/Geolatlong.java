package com.androidhive.googleplacesandmaps;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Geolatlong {

    private static final String TAG = "GeocodingLocation";
     public static double lat=0;
     public static double lon=0;
     public static int flag=1;
    

    public static void getAddressFromLocation(final String locationAddress,
                                              final Context context, final Handler handler) {
        final SearchPageActivity obj=new SearchPageActivity();
        
        
          
           
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLatitude()).append("\n");
                        sb.append(address.getLongitude()).append("\n");
                        lat=address.getLatitude();
                        lon=address.getLongitude();
                        Log.d("TAG2",""+lat);
                        Log.d("TAG2",""+lon);
                        flag=2;
                        result = sb.toString();
                        //obj.showtoast(address,context);
                       // Toast.makeText(context, "Your Location is - \nLat: " + address.getLatitude() + "\nLong: " + address.getLongitude(), Toast.LENGTH_LONG).show();
                        Log.d("TAG1",result);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                    flag=2;
                    //Toast.makeText(context, "Problem with network conectivity, please try again ", Toast.LENGTH_LONG).show();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
           
     
    }
    
    
//   class LoadPlaces extends AsyncTask<String, String, String> {
//
//		String types;
//		/**
//		 * Before starting background thread Show Progress Dialog
//		 * */
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(MainActivity.this);
//			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(false);
//			pDialog.show();
//		}
//
//		/**
//		 * getting Places JSON
//		 * */
//		protected String doInBackground(String...args) {
//			// creating Places class object
//			googlePlaces = new GooglePlaces();
//			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//			Log.w("Hour",""+hour);
//			if(hour>=6&&hour<12)
//			{
//				types="amusement_park|zoo|park|aquarium|cafe|food"
//                        + "|restaurant|church|hindu_temple|place_of_worship|"
//                        + "mosque|synogogue|museum|art_gallery";
//			}
//			if(hour>=12&&hour<16)
//			{
//				types="amusement_park|zoo|park|aquarium|cafe|bar|food"
//                        + "|restaurant|church|hindu_temple|place_of_worship|"
//                        + "mosque|synogogue|museum|art_gallery";
//			}
//			if(hour>=16&&hour<20)
//			{
//				types="cafe|food|restaurant|night_club|casino"
//                        + "|bowling_alley|movie_theater|shopping_mall|museum|art_gallery";
//			}
//			if(hour>=20||hour<6)
//			{
//				types="cafe|bar|food|restaurant|hotels";
//			}
//			try {
//				// Separeate your place types by PIPE symbol "|"
//				// If you want all types places make it as null
//				// Check list of types supported by google
//				// 
//				/*set the user preferences given by mahurkar here*/
//				  // Listing places only cafes, restaurants
//				
//				// Radius in meters - increase this value if you don't find any places
//				double radius = 15000; // 1000 meters 
//				
//				// get nearest places
//				nearPlaces = googlePlaces.search(la,
//						lo, radius, types);
//				
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		/**
//		 * After completing background task Dismiss the progress dialog
//		 * and show the data in UI
//		 * Always use runOnUiThread(new Runnable()) to update UI from background
//		 * thread, otherwise you will get error
//		 * **/
//		protected void onPostExecute(String file_url) {
//			// dismiss the dialog after getting all products
//			pDialog.dismiss();
//			// updating UI from Background Thread
//			
//			runOnUiThread(new Runnable() {
//				public void run() {
//					/**
//					 * Updating parsed Places into LISTVIEW
//					 * */
//					// Get json response status
//					String status = nearPlaces.status;
//					
//					// Check for all possible status
//					if(status.equals("OK")){
//						// Successfully got places details
//						if (nearPlaces.results != null) {
//							// loop through each place
//							for (Place p : nearPlaces.results) {
//								HashMap<String, String> map = new HashMap<String, String>();
//								
//								// Place reference won't display in listview - it will be hidden
//								// Place reference is used to get "place full details"
//								map.put(KEY_REFERENCE, p.reference);
//								
//								// Place name
//								map.put(KEY_NAME, p.name);
//								
//								
//								// adding HashMap to ArrayList
//								placesListItems.add(map);
//							}
//							// list adapter
//							ListAdapter adapter = new SimpleAdapter(MainActivity.this, placesListItems,
//					                R.layout.list_item,
//					                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
//					                        R.id.reference, R.id.name });
//							
//							// Adding data into listview
//							lv.setAdapter(adapter);
//						}
//					}
//					else if(status.equals("ZERO_RESULTS")){
//						// Zero results found
//						alert.showAlertDialog(MainActivity.this, "Near Places",
//								"Sorry no places found. Try to change the types of places",
//								false);
//					}
//					else if(status.equals("UNKNOWN_ERROR"))
//					{
//						alert.showAlertDialog(MainActivity.this, "Places Error",
//								"Sorry unknown error occured.",
//								false);
//					}
//					else if(status.equals("OVER_QUERY_LIMIT"))
//					{
//						alert.showAlertDialog(MainActivity.this, "Places Error",
//								"Sorry query limit to google places is reached",
//								false);
//					}
//					else if(status.equals("REQUEST_DENIED"))
//					{
//						alert.showAlertDialog(MainActivity.this, "Places Error",
//								"Sorry error occured. Request is denied",
//								false);
//					}
//					else if(status.equals("INVALID_REQUEST"))
//					{
//						alert.showAlertDialog(MainActivity.this, "Places Error",
//								"Sorry error occured. Invalid Request",
//								false);
//					}
//					else
//					{
//						alert.showAlertDialog(MainActivity.this, "Places Error",
//								"Sorry error occured.",
//								false);
//					}
//				}
//			});
//
//		}

//	}*/
}