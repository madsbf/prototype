package dk.partyroulette.runforyourmoney;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dk.partyroulette.runforyourmoney.datalayer.Challenge;
import dk.partyroulette.runforyourmoney.datalayer.RunObject;

public class RunListAdapter extends ArrayAdapter<RunObject>
{
	private List<RunObject> runObjects;

	/** here we must override the constructor for ArrayAdapter
	 * the only variable we care about now is ArrayList<Item> objects,
	 * because it is the list of objects we want to display.
	 */
	public RunListAdapter(Context context, int textViewResourceId, List<RunObject> objects) 
	{
		super(context, textViewResourceId, objects);
		runObjects = objects;
	}
	

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.run, null);
			
			RunObject ro = runObjects.get(position);
			if(ro!=null)
			{
				//fill in stats
				TextView runDistance = (TextView) v.findViewById(R.id.runDistance);
				TextView runAt = (TextView) v.findViewById(R.id.runAt);
				TextView runAvgPace = (TextView) v.findViewById(R.id.runAvgPace);
				TextView runTime = (TextView) v.findViewById(R.id.runTime);
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				
				runDistance.setText("Distance: " + df.format(ro.getLength()/1000) + " km");
				runTime.setText("Time: " + ro.getHour() + ":" + ro.getMinutes() + ":" + ro.getSeconds());
				runAvgPace.setText("Avg Pace: " + ((int) Math.round(ro.getAvgPaceMinutes())) + ":" + ((int) Math.round(ro.getAvgPaceSeconds())) + " min/km");
				runAt.setText("Date run: " + ro.getRunAt().toString());
			}


		}

		// the view must be returned to our activity
		return v;
	}

}
