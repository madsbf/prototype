package dk.partyroulette.runforyourmoney;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class RunStatisticsActivity extends Activity
{
	private GoogleMap rMap;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_run_statistics);
	    drawOnMap();
	}

	private void drawOnMap() 
	{
		rMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();	
		//check map availability
		if(rMap!=null)
		{
			//start map position
			rMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.784027,12.518684),15));
			
			//draw gps points on route
			rMap.addMarker(new MarkerOptions().position(new LatLng(55.784027,12.518684)).title("Start"));
			rMap.addMarker(new MarkerOptions().position(new LatLng(55.784027,12.518584)).title("End"));
			
			//draw route
			PolylineOptions routeOptions = new PolylineOptions()
			.add(new LatLng(55.784027,12.518684))
			.add(new LatLng(55.784327,12.518684))
			.add(new LatLng(55.784427,12.518684))
			.add(new LatLng(55.784427,12.518484))
			.add(new LatLng(55.784427,12.518284))
			.add(new LatLng(55.784227,12.518384))
			.add(new LatLng(55.784127,12.518484))
			.add(new LatLng(55.784027,12.518584));
			routeOptions.color(0xff00FF00);
			routeOptions.width(6);
			
			
			Polyline polyline = rMap.addPolyline(routeOptions);
			
		}
	}

}
