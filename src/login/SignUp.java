package login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidhive.googleplacesandmaps.MainActivity;
import com.androidhive.googleplacesandmaps.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements OnClickListener
{
	private EditText user, pass,pass2,email;
	private Button signup;
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String SIGNUP_URL = "http://tripadvisor.comeze.com/signup.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG = "In Sign up";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		user = (EditText)findViewById(R.id.username);
		pass = (EditText)findViewById(R.id.password);
		pass2 =(EditText)findViewById(R.id.passwordAgain);
		email=(EditText)findViewById(R.id.email);
		signup = (Button)findViewById(R.id.signup);
		Log.d(TAG,"before");
		signup.setOnClickListener(this);
		Log.d(TAG,"after");
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		String password,password1;
		
		
		password=pass.getText().toString();
		password1=pass2.getText().toString();
		Log.d(TAG,password);
		Log.d(TAG,password1);
		if(password.equals(password1))
		{
			new AttemptSignup().execute();
		}
		else
		{
			Toast.makeText(SignUp.this, "Password not matching", Toast.LENGTH_LONG).show();
		}
	}
	
	class AttemptSignup extends AsyncTask<String, String, String> 
	{
		/**
		* Before starting background thread Show Progress Dialog
		* */
		boolean failure = false;
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUp.this);
			pDialog.setMessage("Attempting for Signup...");
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
			String emailid = email.getText().toString();
			Log.d(TAG,"In doInBackground");
			try 
			{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				params.add(new BasicNameValuePair("email", emailid));
				Log.d("request!", "starting");
				JSONObject json = jsonParser.makeHttpRequest(
				SIGNUP_URL, "POST", params);
				// checking log for json response
				Log.d("Login attempt", json.toString());
				// success tag for json
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) 
				{
					Log.d("Successfully Signed up!", json.toString());
					Intent ii = new Intent(SignUp.this,MainActivity.class);
					finish();
					// this finish() method is used to tell android os that we are done with current 
					//activity now! Moving to other activity
					startActivity(ii);
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
				Toast.makeText(SignUp.this, message, Toast.LENGTH_LONG).show();
			}
		}
	}

}
