package com.androidhive.googleplacesandmaps;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import login.JSONParser;
import login.SignUp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import cognito.CognitoSyncClientManager;

import cognito.UserPref;

//import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
//import com.amazonaws.services.cognitosync.AmazonCognitoSyncClient;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainFragment extends Fragment implements OnClickListener{
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	
	
	private Button bLogin;
	private Button signup;
	private EditText user, pass;
	JSONParser jsonParser = new JSONParser();
	private ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://tripadvisor.comeze.com/login.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// To maintain FB Login session
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		Log.d(TAG,"OnCreate");
		
		
		
		
		/*SessionState state = Session.getActiveSession().getState();
    	if(state.isOpened())
        {
    		
    		//new getCognito().execute();
        	//Intent i=new Intent(getActivity(),InitialActivity.class);
        	//startActivity(i);
        }*/
    	
		
	}
	
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.activity_main, container, false);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    Log.d(TAG,"OnCreateView");
	  //  new getCognito().execute();
	    
	    
	    
	    user = (EditText)view.findViewById(R.id.username);
	    user.setText("ankit");
		pass = (EditText)view.findViewById(R.id.password);
		pass.setText("password");
		bLogin = (Button)view.findViewById(R.id.login);
		signup=(Button)view.findViewById(R.id.signup);
		Log.d(TAG,"before");
		bLogin.setOnClickListener(this);
		signup.setOnClickListener(this);
	    
//	    Button uf=(Button)view.findViewById(R.id.pref);
//	    
//	    
//	    	uf.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				
//				Log.d(TAG,"OnClick-Pref");
//				Intent i=new Intent(getActivity(),UserPref.class);
//                startActivity(i);
//			}
//		});
	    
	    
	    return view;
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto‐generated method stub
		switch (v.getId()) 
		{
			case R.id.login:
			{
				Log.d(TAG,"IN click");
				new AttemptLogin().execute();
				break;
			}
			case R.id.signup:
			{
				Log.d(TAG,"IN signup");
				Intent i = new Intent(getActivity(),SignUp.class);
				startActivity(i);
				break;
			}
			// here we have used, switch case, because on login activity you may
			//also want to show registration button, so if the user is new ! we can go the 
			//registration activity , other than this we could also do this without switch 
			//case.
			default:
			break;
		}

}
	
	
	
	private void setFacebookSession(Session session) {
		// TODO Auto-generated method stub
		Log.i(TAG, "facebook token");
		Log.i(TAG, "facebook token: " + session.getAccessToken());
        CognitoSyncClientManager.addLogins("graph.facebook.com", session.getAccessToken());
		
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        
	        Request.newMeRequest(session, new Request.GraphUserCallback() {

				// callback after Graph API response with user
				// object
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						// Set view visibility to true
						//otherView.setVisibility(View.VISIBLE);
						// Set User name 
						System.out.println("Hello " + user.getName());
						// Set Gender
						//gender.setText("Your Gender: "
							//	+ user.getProperty("gender").toString());
						//location.setText("Your Current Location: "
							//	+ user.getLocation().getProperty("name")
								//		.toString());
					}
				}
			}).executeAsync();
	        
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	    }
	   
	}
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    
	    public void call(Session session, SessionState state, Exception exception) {
	    	 
	        onSessionStateChange(session, state, exception);
	        Log.d(TAG,"session status callback all job done");
	        if(state.isOpened())
	        {
	        	 
	        	new getCognito().execute();
//	        	Intent i=new Intent(getActivity(),InitialActivity.class);
//	        	startActivity(i);
	        }
	    }
	};

	
	 @Override
     public void onResume() {
         super.onResume();
         uiHelper.onResume();
     }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         uiHelper.onActivityResult(requestCode, resultCode, data);
     }

     @Override
     public void onPause() {
         super.onPause();
         uiHelper.onPause();
     }

     @Override
     public void onDestroy() {
         super.onDestroy();
         uiHelper.onDestroy();
     }

     @Override
     public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
         uiHelper.onSaveInstanceState(outState);
     }
     
     
     
     
     
     private class getCognito extends AsyncTask<Void, Void, Session> {
         
    	 final ProgressDialog dialog = ProgressDialog.show(getActivity(),
                 "Loading", "Please wait");
         @Override
         protected Session doInBackground(Void... params) {
        	 Log.d(TAG,"session status callback all job done11");
        	CognitoSyncClientManager.init(getActivity());
        	 final Session session = Session.getActiveSession();
      	    
        	 try 
        	 {
        		 
        		 
        	 
	     	    	Log.d(TAG,"sess my");
	                 setFacebookSession(session);
        	 }
        	 catch (Exception ex)
    		 {
    			 ex.printStackTrace();
    		 }
			return session;
             
         }

         @Override
         protected void onPostExecute(Session result) {
             
             Log.w(TAG,"inside func onPostExecute");
             dialog.dismiss();
	            if(result!=null)
	            {
	            	Log.w(TAG,"start userpref");
	            	//intent for userpref
	            	if (result != null) {
	       			 Intent i=new Intent(getActivity(),UserPref.class);
	       			 startActivity(i);
	       		 }
	            }
	            else
	            {
	            	Log.w(TAG,"start initial");
	            	// intent for main act
	            }
             
             }
         }
     
     
     class AttemptLogin extends AsyncTask<String, String, String> 
 	{
 		/**
 		* Before starting background thread Show Progress Dialog
 		* */
 		boolean failure = false;
 		@Override
 		protected void onPreExecute()
 		{
 			super.onPreExecute();
 			pDialog = new ProgressDialog(getActivity());
 			pDialog.setMessage("Attempting for login...");
 			pDialog.setIndeterminate(false);
 			pDialog.setCancelable(true);
 			pDialog.show();
 		}
 		@Override
 		protected String doInBackground(String... args) 
 		{
 			// TODO Auto‐generated method stub
 			// here Check for success tag
 			int success;
 			String username = user.getText().toString();
 			String password = pass.getText().toString();
 			try 
 			{
 				List<NameValuePair> params = new ArrayList<NameValuePair>();
 				params.add(new BasicNameValuePair("username", username));
 				params.add(new BasicNameValuePair("password", password));
 				Log.d("request!", "starting");
 				JSONObject json = jsonParser.makeHttpRequest(
 				LOGIN_URL, "POST", params);
 				// checking log for json response
 				Log.d("Login attempt", json.toString());
 				// success tag for json
 				success = json.getInt(TAG_SUCCESS);
 				if (success == 1) 
 				{
 					Log.d("Successfully Login!", json.toString());
 					//finish();
 					// this finish() method is used to tell android os that we are done with current 
 					//activity now! Moving to other activity
 					Intent i=new Intent(getActivity(),InitialActivity.class);
 					startActivity(i);
 					return json.getString(TAG_MESSAGE);
 				}
 				else
 				{
 					return json.getString(TAG_MESSAGE);
 				}
 			}
 			catch (JSONException e) 
 			{
 				e.printStackTrace();
 			}
 			return null;
 		}
 		/**
 		* Once the background process is done we need to Dismiss the progress dialog asap
 		* **/
 		protected void onPostExecute(String message)
 		{
 			pDialog.dismiss();
 			if (message != null)
 			{
 				Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
 			}
 		}
 	}

     
}



