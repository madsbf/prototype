package dk.partyroulette.runforyourmoney;

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
		TextView t = (TextView) getView().findViewById(R.id.statsLength);
		t.setText("5.01" + " KM");
	}
}
