package dk.partyroulette.runforyourmoney;

import java.text.DecimalFormat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RunStatisticsFragment extends Fragment 
{
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_run_statistics, container);
	}

	public void onStart() {
		super.onStart();
	}
	
	public void setText(RunObject r) 
	{
		TextView length = (TextView) getView().findViewById(R.id.statsLength);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		length.setText("Length: " + df.format(r.length/1000) + " km");
		
		TextView time = (TextView) getView().findViewById(R.id.statsTime);
		time.setText("Time: " + r.hour + ":" + r.minutes + ":" + r.seconds);
		
		TextView avgSpeed = (TextView) getView().findViewById(R.id.statsAvgSpeed);
		avgSpeed.setText("Avg Speed: "+ df.format(r.avgSpeed) + " km/h");
		
		TextView avgPace = (TextView) getView().findViewById(R.id.statsAvgPace);
		avgPace.setText("Avg Pace: " + r.avgPaceMinutes + ":" + r.avgPaceSeconds + "min/km");
	}
}
