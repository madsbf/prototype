package dk.partyroulette.runforyourmoney.datalayer;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * This class only contains data. When initiated it calculates some additional statistics about the run.
 * @author Du
 *
 */
public class RunObject extends Progress implements Parcelable
{
	ArrayList<ArrayList<Float>> gpsData;
	int hour;
	int minutes;
	int seconds;

	Float length = (float) 0.0; // in meters
	Float avgPaceMinutes; //in minutes/seconds per km
	Float avgPaceSeconds; //in minutes/seconds per km
	Float avgSpeed; // in km pr hour
	
	private Date runAt = null;
	
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
	
	public RunObject (ArrayList<ArrayList<Float>> gpsData, Date createdAt)
	{
		this.gpsData = gpsData;
		runAt = createdAt;
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

	public Date getRunAt() {
		return runAt;
	}

	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{	
		dest.writeList(gpsData);
		dest.writeInt(hour);
		dest.writeInt(minutes);
		dest.writeInt(seconds);
		dest.writeFloat(length);
		dest.writeFloat(avgPaceMinutes);
		dest.writeFloat(avgPaceSeconds);
		dest.writeFloat(avgSpeed);
		dest.writeValue(runAt);
	}
	
	public static final Parcelable.Creator<RunObject> CREATOR = new Parcelable.Creator<RunObject>() {
		public RunObject createFromParcel(Parcel in)
		{
			return new RunObject(in);
		}
		
		public RunObject[] newArray(int size) 
		{
			return new RunObject[size];
		}
	};
	
	private RunObject(Parcel in)
	{
		gpsData = new ArrayList<ArrayList<Float>>();
		in.readList(gpsData, null);
		hour = in.readInt();
		minutes = in.readInt();
		seconds = in.readInt();
		length = in.readFloat();
		avgPaceMinutes = in.readFloat();
		avgPaceSeconds = in.readFloat();
		avgSpeed = in.readFloat();
		runAt = (Date) in.readValue(null);
	}

}
