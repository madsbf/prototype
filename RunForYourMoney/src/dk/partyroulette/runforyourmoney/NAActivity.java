package dk.partyroulette.runforyourmoney;

import android.app.Activity;
import android.os.Bundle;

public class NAActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_na);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(this.getIntent().getStringExtra("title"));
	}
		
}
