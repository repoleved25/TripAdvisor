package com.androidhive.googleplacesandmaps;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;

public class MainActivity extends FragmentActivity {
	private MainFragment mainFragment;
	
	
	//private SignInButton btnSignIn;
	//private boolean mIntentInProgress;
	 
    //private boolean mSignInClicked;
 
    //private ConnectionResult mConnectionResult;
    //private static final int RC_SIGN_IN = 0;

	
    
    
	private static final String TAG ="MainActivityLogin";
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	    	Log.d(TAG,"OnCreate");
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	    	Log.d(TAG,"OnCreate else");
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	    Log.d(TAG,"Out of if-else");       	
	}
	
}
