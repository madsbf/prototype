package dk.partyroulette.runforyourmoney;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailedStatsFragment extends Fragment
{
	WebView wv = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = (View) inflater.inflate(R.layout.fragment_detailed_stats, container,false);
		wv = (WebView) v.findViewById(R.id.webPage);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new MyWebViewClient());
		//String stats = "<html> <head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script> <script type=\"text/javascript\"> google.load(\"visualization\", \"1\", {packages:[\"corechart\"]}); google.setOnLoadCallback(drawChart); function drawChart() { var data = google.visualization.arrayToDataTable([ ['Year', 'Sales', 'Expenses'], ['2004',  1000, 400], ['2005',  1170, 460],['2006',  660, 1120], ['2007',  1030, 540] ]); var options = { title: 'Company Performance' }; var chart = new google.visualization.LineChart(document.getElementById('chart_div')); chart.draw(data, options); }</script> </head><body> <div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div></body></html>\";";
		//wv.loadData(stats,"text/html" , null);
		wv.loadData("<html><body><p>Select one of the options above.</p></body></html>","text/html",null);
		return v;
	}
	
	public void loadHtml(String html)
	{
		wv.loadData(html, "text/html", null);
	}
	
	private class MyWebViewClient extends WebViewClient {

    }
	
}
