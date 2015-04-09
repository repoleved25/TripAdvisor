package cognito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.Dataset.SyncCallback;
import com.amazonaws.mobileconnectors.cognito.Record;
import com.amazonaws.mobileconnectors.cognito.SyncConflict;
import com.amazonaws.mobileconnectors.cognito.exceptions.DataStorageException;
import com.amazonaws.services.cognitoidentity.model.NotAuthorizedException;
import com.androidhive.googleplacesandmaps.InitialActivity;
import com.androidhive.googleplacesandmaps.R;
//import com.facebook.Session;

public class UserPref extends Activity {
  
  private ListView mainListView ;
  private Planet[] planets ;
  private ArrayAdapter<Planet> listAdapter;
  private static final String TAG = "PlanetActivity";
  private Dataset dataset;
  private CognitoSyncManager client;
  
  
  /** Called when the activity is first created. */
  @SuppressWarnings("deprecation")
@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.checklist);
    
    // Find the ListView resource. 
    mainListView = (ListView) findViewById( R.id.mainListView );
    
    // When item is tapped, toggle checked properties of CheckBox and Planet.
    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick( AdapterView<?> parent, View item, 
                               int position, long id) {
        Planet planet = listAdapter.getItem( position );
        planet.toggleChecked();
        PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
        viewHolder.getCheckBox().setChecked( planet.isChecked() );
      }
    });

    
    // Create and populate planets.
    planets = (Planet[]) getLastNonConfigurationInstance() ;
    if ( planets == null ) {
      planets = new Planet[] { 
          new Planet("Adventure"), new Planet("ArtsPlaces"), new Planet("ReligiousPlaces"), 
          new Planet("NightLife"), new Planet("Food")
      };  
    }
    ArrayList<Planet> planetList = new ArrayList<Planet>();
    planetList.addAll( Arrays.asList(planets) );
    
    // Set our custom array adapter as the ListView's adapter.
    listAdapter = new PlanetArrayAdapter(this, planetList);
    mainListView.setAdapter( listAdapter );  
    
    
   // new getCognito().execute();
 
    refreshDatasetMetadata();
    
    
    
    /*findViewById(R.id.update).setOnClickListener(new OnClickListener() {
        @Override
        
        public void onClick(View v) {
        	
        	
        }
    });*/
    
  
/*    findViewById(R.id.sync).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            refreshDatasetMetadata();
        }
    });*/
    
    
  }
  
  private void refreshListData() {
	  client = CognitoSyncClientManager.getInstance();
	//  dataset=client.openOrCreateDataset("data"+CognitoSyncClientManager.getProvider().getIdentityId());
	  dataset=client.openOrCreateDataset("data5");
	  Log.d("UserPref", "identity "+CognitoSyncClientManager.getProvider().getCachedIdentityId());
	  System.out.println("hi "+dataset.getLastSyncCount());
	  int i,count=0;
      for(i=0;i<listAdapter.getCount();i++)
	  {
    	  System.out.println("hi "+dataset.get(listAdapter.getItem(i).getName()));
		  if(dataset.get(listAdapter.getItem(i).getName()).equals("1"))
    	  {
			  listAdapter.getItem(i).setChecked(true);
			  count++;
    	  }
    	  else
    	  {
    		  listAdapter.getItem(i).setChecked(false);
    	  }
	  }
      System.out.println(count);
      listAdapter.notifyDataSetChanged();
	 /* new RefreshAsync().execute();*/

  }
  
  
  private void refreshDatasetMetadata() {
  	Log.w("RefreshData","inside func refreshdatametadata");
      new RefreshDatasetMetadataTask().execute();
      Log.w("RefreshData","inside func refreshdatametadata after");
  }
  
  
  private class RefreshDatasetMetadataTask extends AsyncTask<Void, Void, Void> {
      ProgressDialog dialog;
      boolean authError;

      @Override
      protected void onPreExecute() {
    	  client = CognitoSyncClientManager.getInstance();
      	Log.w("RefreshData","onpreexecute");
          dialog = ProgressDialog.show(UserPref.this,
                  "Syncing...", "Please wait");
          Log.w("RefreshData","onpreexecute after");
      }

      @Override
      protected Void doInBackground(Void... params) {
          try {
          	Log.w(TAG,"before refresh datasets");
              client.refreshDatasetMetadata();
          	
              Log.w(TAG,"after refresh datasets");
          } catch (DataStorageException dse) {
              Log.e(TAG, "failed to refresh dataset metadata", dse);
          } catch (NotAuthorizedException e) {
              Log.e(TAG, "failed to refresh dataset metadata", e);
              authError = true;
          }
          return null;
      }

      @Override
      protected void onPostExecute(Void result) {
          dialog.dismiss();
          Log.w(TAG,"inside func onPostExecute");
          if (!authError) {
          	Log.w(TAG,"if");
              refreshListData();
          }
          else {
          	Log.w(TAG,"else");
              // Probably an authentication (or lackthereof) error
//              new AlertDialog.Builder(UserPref.this)
//                      .setTitle("There was an error")
//                      .setIcon(android.R.drawable.ic_dialog_alert)
//                      .setMessage(
//                              "You must be logged in or have allowed access to unauthorized users to browse your data")
//                      .setPositiveButton("Back", new DialogInterface.OnClickListener() {
//                          @Override
//                          public void onClick(DialogInterface dialog, int which) {
//                          	Log.w("ListDataset","before on click");
//                              dialog.dismiss();
//                              Intent intent = new Intent(UserPref.this,
//                                      MainActivity.class);
//                              startActivity(intent);
//                          }
//                      })
//                      .setCancelable(false)
//                      .show();
//              Log.w(TAG,"if finish");
          }
      }
  }


protected void addDataset(ArrayAdapter<Planet> p) {
		// TODO Auto-generated method stub
		
	  	client = CognitoSyncClientManager.getInstance();
	 
	
		// Create a record in a dataset and synchronize with the server
		   
	 //	dataset = client.openOrCreateDataset("data"+CognitoSyncClientManager.getProvider().getIdentityId());
	  	dataset=client.openOrCreateDataset("data5");
	 	//dataset.put("Food","1");
	 
    	for(int i=0;i<p.getCount();i++)
    	{
    		Planet pl = listAdapter.getItem(i);
    		System.out.println(pl.getName()+"11");
	     
    	     if(pl.isChecked()){
    	    	 dataset.put(pl.getName(),"1");
    	    	 //synchronize(false);
    	     }
    	     else
    	     {
    	    	 dataset.put(pl.getName(),"0");
    	    	 //synchronize(false);
    	     }
    	}
 
		synchronize(false);	
		
	}

	
	private void synchronize(final boolean finish) {
        final ProgressDialog dialog = ProgressDialog.show(this,
                "Updating...", "Please wait");
        Log.i("update", "synchronize: " + finish);
        dataset.synchronize(new SyncCallback() {
            @Override
            public void onSuccess(Dataset dataset, final List<Record> newRecords) {
                Log.i("update", "success");
                dialog.dismiss();
                Intent in=new Intent(UserPref.this,InitialActivity.class);
                startActivity(in);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                        if (finish) {
//                            finish();
//                        }
//                        refreshListData();
//                        Log.i("Sync", String.format("%d records synced", newRecords.size()));
//                        Toast.makeText(ListRecordsActivity.this,
//                                "Successful!", Toast.LENGTH_LONG).show();
//                    }
//                });
            }

            @Override
            public void onFailure(final DataStorageException dse) {
                Log.i("update", "failure: ", dse);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                        Log.e("Sync", "failed: " + dse);
//                        Toast.makeText(ListRecordsActivity.this,
//                                "Failed due to\n" + dse.getMessage(), Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
            }

            @Override
            public boolean onConflict(final Dataset dataset,
                    final List<SyncConflict> conflicts) {
            	System.out.println("hello conflict");
                Log.i("Update", "conflict: " + conflicts);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Log.i(TAG,
                                String.format("%s records in conflict", conflicts.size()));
                        List<Record> resolvedRecords = new ArrayList<Record>();
                        for (SyncConflict conflict : conflicts) {
                            Log.i(TAG,
                                    String.format("remote: %s; local: %s",
                                            conflict.getRemoteRecord(),
                                            conflict.getLocalRecord()));
                            /* resolve by taking remote records */
                            //resolvedRecords.add(conflict.resolveWithRemoteRecord());

                            /* resolve by taking local records */
                             resolvedRecords.add(conflict.resolveWithLocalRecord());

                            /*
                             * resolve with customized logic, e.g. concatenate
                             * strings
                             */
                            // String newValue =
                            // conflict.getRemoteRecord().getValue()
                            // + conflict.getLocalRecord().getValue();
                            // resolvedRecords.add(conflict.resolveWithValue(newValue));
                        }
                        dataset.resolve(resolvedRecords);
                        
                        Toast.makeText(
                                getApplication(),
                                String.format(
                                        "%s records in conflict. Resolve by taking remote records",
                                        conflicts.size()),
                                Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onDatasetDeleted(Dataset dataset, String datasetName) {
                Log.i("Update", "delete: " + datasetName);
                return true;
            }

            @Override
            public boolean onDatasetsMerged(Dataset dataset, List<String> datasetNames) {
                Log.i("Update", "merge: " + datasetNames);
                return true;
            }
        });
	}




/** Holds planet data. */
  public static class Planet {
    private String name = "" ;
    private boolean checked = false ;
    public Planet() {}
    public Planet( String name ) {
      this.name = name ;
    }
    public Planet( String name, boolean checked ) {
      this.name = name ;
      this.checked = checked ;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public boolean isChecked() {
      return checked;
    }
    public void setChecked(boolean checked) {
      this.checked = checked;
    }
    public String toString() {
      return name ; 
    }
    public void toggleChecked() {
      checked = !checked ;
    }
  }
  
  /** Holds child views for one row. */
  private static class PlanetViewHolder {
    private CheckBox checkBox ;
    private TextView textView ;
    @SuppressWarnings("unused")
	public PlanetViewHolder() {}
    public PlanetViewHolder( TextView textView, CheckBox checkBox ) {
      this.checkBox = checkBox ;
      this.textView = textView ;
    }
    public CheckBox getCheckBox() {
      return checkBox;
    }
    @SuppressWarnings("unused")
	public void setCheckBox(CheckBox checkBox) {
      this.checkBox = checkBox;
    }
    public TextView getTextView() {
      return textView;
    }
    @SuppressWarnings("unused")
	public void setTextView(TextView textView) {
      this.textView = textView;
    }    
  }
  
  /** Custom adapter for displaying an array of Planet objects. */
  private static class PlanetArrayAdapter extends ArrayAdapter<Planet> {
    
    private LayoutInflater inflater;
    
    public PlanetArrayAdapter( Context context, List<Planet> planetList ) {
      super( context, R.layout.simplerow, R.id.rowTextView, planetList );
      // Cache the LayoutInflate to avoid asking for a new one each time.
      inflater = LayoutInflater.from(context) ;
    }

    @SuppressLint("InflateParams")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // Planet to display
      Planet planet = (Planet) this.getItem( position ); 

      // The child views in each row.
      CheckBox checkBox ; 
      TextView textView ; 
      
      // Create a new row view
      if ( convertView == null ) {
        convertView = inflater.inflate(R.layout.simplerow, null);
        
        // Find the child views.
        textView = (TextView) convertView.findViewById( R.id.rowTextView );
        checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );
        
        // Optimization: Tag the row with it's child views, so we don't have to 
        // call findViewById() later when we reuse the row.
        convertView.setTag( new PlanetViewHolder(textView,checkBox) );

        // If CheckBox is toggled, update the planet it is tagged with.
        checkBox.setOnClickListener( new View.OnClickListener() {
          public void onClick(View v) {
            CheckBox cb = (CheckBox) v ;
            Planet planet = (Planet) cb.getTag();
            planet.setChecked( cb.isChecked() );
          }
        });        
      }
      // Reuse existing row view
      else {
        // Because we use a ViewHolder, we avoid having to call findViewById().
        PlanetViewHolder viewHolder = (PlanetViewHolder) convertView.getTag();
        checkBox = viewHolder.getCheckBox() ;
        textView = viewHolder.getTextView() ;
      }

      // Tag the CheckBox with the Planet it is displaying, so that we can
      // access the planet in onClick() when the CheckBox is toggled.
      checkBox.setTag( planet ); 
      
      // Display planet data
      checkBox.setChecked( planet.isChecked() );
      textView.setText( planet.getName() );      
      
      return convertView;
    }
    
  }
  
  public Object onRetainNonConfigurationInstance() {
    return planets ;
  }
  
  /*private void setFacebookSession(Session session) {
		// TODO Auto-generated method stub
		
		Log.i(TAG, "facebook token: " + session.getAccessToken());
      CognitoSyncClientManager.addLogins("graph.facebook.com", session.getAccessToken());
		
	}*/
  
  /*private class getCognito extends AsyncTask<Void, Void, Void> {
     
	
	  
 	 final ProgressDialog dialog = ProgressDialog.show(UserPref.this,
              "Loading...", "Please wait");
      @Override
      protected Void doInBackground(Void... params) {
     	 
     	//CognitoSyncClientManager.init(getApplicationContext());
     	
     	 final Session session = Session.getActiveSession();
   	    
  	    if (session != null) {
       //       setFacebookSession(session);
          }
   
   
     	
			return null;
          
      }

      @Override
      protected void onPostExecute(Void result) {
          
          Log.w(TAG,"inside func onPostExecute");
          dialog.dismiss();
          final Session session = Session.getActiveSession();
  	    
//	     	    if (session != null) {
//	                 setFacebookSession(session);
//	             }
          
          }
      }*/
/*  private class RefreshAsync extends AsyncTask<Void, Void, Dataset> {
	     
		
	  
	 	 final ProgressDialog dialog = ProgressDialog.show(UserPref.this,
	              "Loading...", "Please wait");
	      @Override
	      protected Dataset doInBackground(Void... params) {
	     	 
	    	  client = CognitoSyncClientManager.getInstance();
				return client.openOrCreateDataset("data"+CognitoSyncClientManager.getProvider().getIdentityId());
	          
	      }

	      @Override
	      protected void onPostExecute(Dataset result) {
	          
	    	  
	    	  dataset =result;
	          Log.d("UserPref", "identity"+CognitoSyncClientManager.getProvider().getCachedIdentityId());
	    	  int i,count=0;
	          for(i=0;i<listAdapter.getCount();i++)
	    	  {
	        	  System.out.println("hi"+dataset.get(listAdapter.getItem(i).getName()));
	    		  if(dataset.get(listAdapter.getItem(i).getName()).equals("1"))
	        	  {
	    			  listAdapter.getItem(i).setChecked(true);
	    			  count++;
	        	  }
	        	  else
	        	  {
	        		  listAdapter.getItem(i).setChecked(false);
	        	  }
	    	  }
	          System.out.println(count);
	          listAdapter.notifyDataSetChanged();
	      }
}
  private class DataSyncAsync extends AsyncTask<Void, Void, Dataset> {
	     
	  		@Override
	  		protected void onPreExecute() {
	  			synchronize(false);
	  		}
	  
	      @Override
	      protected Dataset doInBackground(Void... params) {
	     	 
	    	  client = CognitoSyncClientManager.getInstance();
				return client.openOrCreateDataset("data"+CognitoSyncClientManager.getProvider().getIdentityId());
	          
	      }

	      @Override
	      protected void onPostExecute(Dataset result) {
	          
	  	 	dataset=result;
	  	 	int i;
	      	for(i=0;i<p.getCount();i++)
	      	{
	      		Planet pl = listAdapter.getItem(i);
	      		System.out.println(pl.getName()+"11");
	  	     
	      	     if(pl.isChecked()){
	      	    	 dataset.put(pl.getName(),"1");
	      	    	 //synchronize(false);
	      	     }
	      	     else
	      	     {
	      	    	 dataset.put(pl.getName(),"0");
	      	    	 //synchronize(false);
	      	     }
	      	}
	  		synchronize(false);
	      }
}*/
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
      MenuInflater menuInflater = getMenuInflater();
      menuInflater.inflate(R.menu.menu_checklist, menu);
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
      case R.id.next:
          // Single menu item is selected do something
          // Ex: launching new activity/screen or show alert message
    	  
    	for(int i=0;i<listAdapter.getCount();i++)
      	{
      		Planet p = listAdapter.getItem(i);
      	     if(p.isChecked()){
      	    	 System.out.println(p.getName());
      	     }
      	}
      	addDataset(listAdapter);
        return true;


      default:
          return super.onOptionsItemSelected(item);
      }
  } 
}