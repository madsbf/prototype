package dk.partyroulette.runforyourmoney.datalayer;

import java.util.ArrayList;

import android.app.Activity;
import android.location.Location;
/**
 * This class only contains data. When initiated it calculates some additional statistics about the run.
 * @author Du
 *
 */
public class RunObject extends Progress
{
	ArrayList<ArrayList<Float>> gpsData;
	int hour;
	int minutes;
	int seconds;

	Float length = (float) 0.0; // in meters
	Float avgPaceMinutes; //in minutes/seconds per km
	Float avgPaceSeconds; //in minutes/seconds per km
	Float avgSpeed; // in km pr hour 
	
	public RunObject (ArrayList<ArrayList<Float>> gpsData)
	{
		this.gpsData = gpsData;
		if(gpsData == null || gpsData.isEmpty())
		{
			//do not calculate statistics
			return;
		} else 
		{
			calcStatistics();
		}
	}
	
	public ArrayList<ArrayList<Float>> getGpsData() {
		return gpsData;
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public Float getLength() {
		return length;
	}

	public Float getAvgPaceMinutes() {
		return avgPaceMinutes;
	}

	public Float getAvgPaceSeconds() {
		return avgPaceSeconds;
	}

	public Float getAvgSpeed() {
		return avgSpeed;
	}

	private void calcStatistics() {
		//time
		Float milliseconds = (gpsData.get(gpsData.size()-1).get(3)-gpsData.get(0).get(3));
		Float tmp_hour = milliseconds/(1000*60*60);
		hour = (int) Math.floor(tmp_hour);
		
		milliseconds -= hour*60*60*1000;
		Float tmp_minutes = milliseconds/(1000*60);
		minutes = (int) Math.floor(tmp_minutes);
		
		milliseconds -= minutes*60*1000;
		Float tmp_seconds = milliseconds/(1000);
		seconds = (int) Math.floor(tmp_seconds);
		
		
		//length
		for(int i = 1; i<gpsData.size(); i++)
		{
			float[] results = {0};
			Location.distanceBetween(gpsData.get(i-1).get(0), gpsData.get(i-1).get(1), gpsData.get(i).get(0), gpsData.get(i).get(1), results);
			length += results[0];
		}
		
		//avgSpeed
		int totalSeconds = hour*60*60+minutes*60+seconds;
		avgSpeed = (float) (((double)length/totalSeconds)*3.6);
		
		//avgPace
		double pace = totalSeconds/(length/1000.0);
		avgPaceMinutes = (float) Math.floor(pace/60.0);
		avgPaceSeconds = (float) Math.floor(pace-avgPaceMinutes*60.0);
		
	}

	@Override
	protected int getProgressInt() 
	{
		return Math.round(length / 1000);
	}


}