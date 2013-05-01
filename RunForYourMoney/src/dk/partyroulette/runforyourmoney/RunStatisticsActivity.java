package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

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
	private ArrayList<ArrayList<Float>> gpsData;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_run_statistics);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    //debug purposes
	    createFakeData();
	    
	    drawOnMap();
	    
	    updateStatistics();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	private void updateStatistics() 
	{
		RunObject r = new RunObject(gpsData);
		RunStatisticsFragment rsFragment = (RunStatisticsFragment) getFragmentManager().findFragmentById(R.id.stats);
		rsFragment.setText(r);
	}



	private void drawOnMap() 
	{
		rMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();	
		//check map availability
		if(rMap!=null)
		{
			//start map position, start on the first point of the route
			LatLng start = new LatLng(gpsData.get(0).get(0),gpsData.get(0).get(1));
			LatLng end = new LatLng(gpsData.get(gpsData.size()-1).get(0),gpsData.get(gpsData.size()-1).get(1));
			
			rMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,17));
			
			//draw gps points on route
			rMap.addMarker(new MarkerOptions().position(start).title("Start"));
			rMap.addMarker(new MarkerOptions().position(end).title("End"));
			
			//draw route
			PolylineOptions routeOptions = new PolylineOptions();
			for(ArrayList<Float> tmp : gpsData)
			{
				routeOptions.add(new LatLng(tmp.get(0),tmp.get(1)));
			}
			routeOptions.color(0xff00FF00);
			routeOptions.width(6);
			
			
			Polyline polyline = rMap.addPolyline(routeOptions);
			
		}
	}
	public void createFakeData() {
		gpsData = new ArrayList<ArrayList<Float>>();
	    ArrayList<Float> tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784027);
	    tmp.add((float) 12.518684);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615378E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784327);
	    tmp.add((float) 12.518684);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.361538E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784427);
	    tmp.add((float) 12.518684);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615382E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784427);
	    tmp.add((float) 12.518484);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615382E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784427);
	    tmp.add((float) 12.518284);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615382E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784227);
	    tmp.add((float) 12.518384);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615383E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784127);
	    tmp.add((float) 12.518484);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615383E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784027);
	    tmp.add((float) 12.518584);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615384E12);
	    gpsData.add(tmp);
	    tmp = null;
	}

}
