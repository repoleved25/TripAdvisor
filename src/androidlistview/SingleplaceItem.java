package androidlistview;



import com.androidhive.googleplacesandmaps.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class SingleplaceItem extends Activity {
	private sender obj2;
	 
     @Override
 	protected void onCreate(Bundle savedInstanceState) {
 		// TODO Auto-generated method stub
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.single_place1);
 		Intent i = getIntent();
 	     // getting attached intent dat
 	     @SuppressWarnings("unused")
 	    
		int packagenumber=i.getIntExtra("packagenumber", 0);
 	    // long index=i.getLongExtra("position", 5);
 	     int index=i.getIntExtra("position", 90);
 	    obj2=(sender) i.getSerializableExtra("object");
 	     System.out.println(index);
 	    System.out.println(packagenumber);
 	     /*Fetch the details of the given place name*/
 	     /*hardcoded*/
 	    String name = obj2.getG().getPlace(obj2.getG().getIndexToPid(obj2.getP().getPaths()[packagenumber][index])).getName();
		//String address = "sdvdvs";
		String phone = "9096736771";
		Double latitude = obj2.getG().getPlace(obj2.getG().getIndexToPid(obj2.getP().getPaths()[packagenumber][index])).getLat();
		Double longitude = obj2.getG().getPlace(obj2.getG().getIndexToPid(obj2.getP().getPaths()[packagenumber][index])).getLon();
		TextView lbl_name = (TextView) findViewById(R.id.name);
		//TextView lbl_address = (TextView) findViewById(R.id.address);
		TextView lbl_phone = (TextView) findViewById(R.id.phone);
		TextView lbl_location = (TextView) findViewById(R.id.location);
		lbl_name.setText(name);
		//lbl_address.setText(address);
		lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
		lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));
 		
 	}

     
}
