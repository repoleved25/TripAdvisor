package androidlistview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SingleListItem extends ListActivity{
	private sender obj;
	int number;
	int[] distance=new int[9];
	CustomAdapter adapter;
	 float[] time=new float[9];
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        obj=(sender) i.getSerializableExtra("object");
        number=i.getIntExtra("packagenumber", 0);
        String[] places=new String[obj.getP().getSize_places()];
       // System.out.println(obj.getG().getPlace(obj.getP().getPaths()[number][0]).getName());
       // System.out.println(obj.getP().getPaths()[number][0]);
        for(int j=0;j<obj.getP().getSize_places();j++)
        {
        	places[j]=obj.getG().getPlace(obj.getG().getIndexToPid(obj.getP().getPaths()[number][j])).getName();
        	//System.out.println(places[j]);
        }
       
        	
       for(int k=1;k<obj.getP().getSize_places();k++)
       {
    	    distance[k-1]=(int)obj.getG().getGraphDist(obj.getP().getPaths()[number][k], obj.getP().getPaths()[number][k-1]);
    	 //  distance[k-1]=(int) obj.getG().getGraphDist(k, k-1);
    	   System.out.println(distance[k-1]);
      }
       
       	String a[]= new String[9];
       	for(int p=0;p<9;p++)
       	{
       		a[p]=String.valueOf(distance[p]);
       		
       	}
       	
       	for(int p=0;p<9;p++)
       	{
       		time[p]=(float)distance[p]/40; 
       		System.out.println(time[p]);
       	}
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(SingleListItem.this,
          //      R.layout.single_list_item_view, R.id.label, places);
       //this.setListAdapter(new ArrayAdapter<String>(getApplication(), R.layout.newlist,R.id.textView2, places));
        ListView lv = getListView();
       	adapter=new CustomAdapter(this,places,a,time);
    	 lv.setAdapter(adapter);
     
        
        
         lv.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	 
            	 //String selected = ((TextView) view.findViewById(R.id.product_label)).getText().toString();
            	// String selected = ((TextView) view.findViewById(R.id.textView1)).getText().toString();
            	 
             	Intent i = new Intent(getApplicationContext(), MainActivity5.class);
           	  // sending data to new activity
           	  i.putExtra("packagenumber", number);
           	  //i.putExtra("position", id);
           	  i.putExtra("position", position);
           	   i.putExtra("object", obj);
           	  startActivity(i);
             }
           });
	}
	
	 

}