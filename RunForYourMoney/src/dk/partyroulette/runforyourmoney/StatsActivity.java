package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import dk.partyroulette.runforyourmoney.datalayer.RunObject;

public class StatsActivity extends FragmentActivity
{
	List<RunObject> runObjects = new ArrayList<RunObject>();
	String stats = "";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//a little hack? why isn't this automatically set to the activity title?
		getActionBar().setTitle("Detailed stats");
		
		Intent intent = getIntent();
		runObjects = intent.getParcelableArrayListExtra("runObjects");
		
		stats = createVisualization(runObjects,1);
		
		DetailedStatsFragment f = new DetailedStatsFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.detailed_stats,f, "DetailedStats").commit();
		getSupportFragmentManager().executePendingTransactions();
		
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.visualizationmenu, menu);
	    return true;
	}
	
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
		case R.id.type1:
		
			stats = createVisualization(runObjects, 1);
			DetailedStatsFragment curFrag1 = (DetailedStatsFragment) getSupportFragmentManager().findFragmentByTag("DetailedStats");
			if(curFrag1 != null)
			{
				curFrag1.loadHtml(stats);
			}
			break;
		case R.id.type2:
			stats = createVisualization(runObjects, 2);
			DetailedStatsFragment curFrag2 = (DetailedStatsFragment) getSupportFragmentManager().findFragmentByTag("DetailedStats");
			if(curFrag2 != null)
			{
				curFrag2.loadHtml(stats);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private String createVisualization(List<RunObject> runObjects, int type)
	{
		// type 1: avg speed line chart
		// type 2: cumulative length line chart
		// header for line charts
		String visualization = "";
		visualization += "<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\"> google.load(\"visualization\", \"1\", {packages:[\"corechart\"]}); google.setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization.arrayToDataTable([";
		
		//adding data
		if(type==1)
		{
			visualization +="['Date','Avg Speed'],";
			for(RunObject ro:runObjects)
			{
				Date d = ro.getRunAt();
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				visualization +="['"+ cal.get(Calendar.DAY_OF_MONTH)+"/" + cal.get(Calendar.MONTH)+"', " + ro.getAvgSpeed()+ "],"; 
			}
		} else if(type ==2)
		{
			visualization +="['Date','length'],";
			float cumLength = 0.0f;
			for(RunObject ro:runObjects)
			{
				Date d = ro.getRunAt();
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				cumLength+=ro.getLength()/1000;
				visualization +="['"+ cal.get(Calendar.DAY_OF_MONTH)+"/" + cal.get(Calendar.MONTH)+"', " + cumLength + "],"; 
			}
		}
		
		// end of visualization
		if(type==1)
		{
			visualization += "]); var options = {title: 'Avg Speed', vAxis: {title: 'km pr. hour'}};";
		} else if(type == 2)
		{
			visualization += "]); var options = {title: 'Cumulative length', vAxis: {title: 'Kilometers'}};";
		}
		visualization += "var chart = new google.visualization.LineChart(document.getElementById('chart_div'));chart.draw(data, options);}</script></head><body><div id=\"chart_div\" style=\"width: 380px; height: 380px;\"></div></body></html>";
		
		
		return visualization;
	}

}
