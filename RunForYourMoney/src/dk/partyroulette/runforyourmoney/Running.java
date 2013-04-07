package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Running extends Activity implements VerificationMethod{
	private LocationManager locationManager;
	private ArrayList<ArrayList<Float>> gpsData;
	private boolean paused = false;

	public Running() {
	}

	@Override
	public void start() {
		gpsData = new ArrayList<ArrayList<Float>>();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//get initial location
		Location location = locationManager.getLastKnownLocation("network");
		//receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		paused = false;
	}
	
	public void pause() {
		if(paused)
		{
			paused = false;
		} else {
			paused = true;
		}
	}

	@Override
	public void end() {
		//stop receiving updates
		locationManager.removeUpdates(locationListener);
		//prepare dataobject
		
	}

	@Override
	public boolean isVerified() {
		// TODO Auto-generated method stub
		return false;
	}

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			//add to database
			if(location!=null && !paused)
			{
				float lat = (float) location.getLatitude();
				float lng = (float) location.getLongitude();
				int acc = (int) location.getAccuracy();
				ArrayList<Float> temp = new ArrayList<Float>();
				temp.add(lat);
				temp.add(lng);
				temp.add((float) acc);
				temp.add((float) location.getTime());
				gpsData.add(temp);
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	};

}
