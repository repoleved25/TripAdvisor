package androidlistview;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidhive.googleplacesandmaps.R;
import com.google.gson.Gson;

public class AndroidListViewActivity extends ListActivity {
	private sender pm;
	private int isconnected=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        String[] adobe_products = getResources().getStringArray(R.array.packages);
        
        // Binding Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item1, R.id.label, adobe_products));
        
        LongOperation lo=new LongOperation();
        lo.execute();
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	  
        	  // selected item 
        	  if(isconnected==1)
        	  {
        	  // Launching new Activity on selecting single List Item
        	  Intent i = new Intent(getApplicationContext(), SingleListItem.class);
        	  // sending data to new activity
        	  i.putExtra("packagenumber", position);
        	  i.putExtra("object", pm);
        	  
        	  startActivity(i);
        	  }
        	  else
        	  {
        		  Toast.makeText(getApplicationContext(), "please ensure an internet connection", Toast.LENGTH_LONG).show();;
        	  }
        	
          }
        });
    }
    private class LongOperation extends AsyncTask<Void, Void, Void> {
    	
    	
    	String url_select = "http://myfirstelasticbeans-env007.elasticbeanstalk.com/getdetail";
    	 //InputStream inputStream = null;
    	    String response=null;
    	    ProgressDialog pdia;
    	    
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		
    		pdia=ProgressDialog.show(AndroidListViewActivity.this,"Progess","Getting Packages...");
    		
    	}
        @Override
        protected Void doInBackground(Void... params) {
        	try {
        		
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();
                //client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "android");
                
                HttpGet httpGet = new HttpGet(url_select);
                httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
               // httpPost.setEntity(new UrlEncodedFormEntity(null));
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
               // response = httpEntity.getContent().toString();
                 response = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e1) {
                Log.e("UnsupportedEncodingException", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
        	
			return null;  
          
        }

        protected void onPostExecute(Void result) {
        	//System.out.println(response);
        	System.out.println("not exception 0071");
        	pdia.dismiss();
        	if(response!=null)
        	{
        		Gson gson = new Gson();
				//JsonObject js=new JsonObject();
				 pm=gson.fromJson(response,sender.class);
				//GraphCity gc=new GraphCity();
				//gc =gson.fromJson(response,GraphCity.class);
				System.out.println(pm.getG().getPlace(1).getName());
    			
        	}
        	else {
        		isconnected=0;
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        	 return;
        }     
    }
	
}