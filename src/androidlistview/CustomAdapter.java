package androidlistview;

import java.util.zip.Inflater;

import com.androidhive.googleplacesandmaps.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	private static LayoutInflater inflater=null;
	private Activity activity;
	private String names[];
	private String distances[];
	private float[] times;
	 public CustomAdapter(Activity a,String b[],String c[], float[] d) {
		// TODO Auto-generated constructor stub
		 Log.d("Tag", "enter");
		 activity = a;
		 inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 names=b;
		 distances=c;
		 times=d;
		 
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
		
	}
    public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
    	//Log.d("Tag", "enter");
		convertView=inflater.inflate(R.layout.newlist,null);
		TextView name = (TextView)convertView.findViewById(R.id.textView2); 
        TextView distance = (TextView)convertView.findViewById(R.id.textView1);
        TextView time = (TextView)convertView.findViewById(R.id.textView3);
        ImageView arrow=(ImageView)convertView.findViewById(R.id.imageView1);
       // System.out.println(names.length);
          // for(int i=0;i<distances.length;i++)
        	   //System.out.println(distances[1]);
        	name.setText(names[position]);
        	//System.out.println(distances.length);
           
            	if(position<distances.length)
            	{
                distance.setText(distances[position]+"km");
        	        time.setText(String.valueOf(times[position])+"hrs");
            	}
            	else if(position==distances.length)
            	{
            		distance.setVisibility(View.GONE);
            		arrow.setVisibility(View.GONE);
            		time.setVisibility(View.GONE);
            	}
            
            	
		return convertView;
	}

}