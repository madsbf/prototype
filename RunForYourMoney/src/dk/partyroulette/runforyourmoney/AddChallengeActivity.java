package dk.partyroulette.runforyourmoney;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddChallengeActivity extends Activity implements OnClickListener
{
	private Button buttonChallenge;
	private EditText editName;
	private EditText editLength;
	private EditText editDeadline;
	private EditText editCoins;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_challenge);
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		registerViews();
		registerOnClickListener();
	}
	
	private void registerViews()
	{
		buttonChallenge = (Button) findViewById(R.id.buttonChallenge);
		editName = (EditText) findViewById(R.id.editName);
		editLength = (EditText) findViewById(R.id.editLength);
		editDeadline = (EditText) findViewById(R.id.editDeadline);
		editCoins = (EditText) findViewById(R.id.editCoins);
	}
	
	private void registerOnClickListener()
	{
		buttonChallenge.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) 
	{
		Participant[] participants = { 
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", new IntProgress(2)),
				new Participant("Du", "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(3)), 
				new Participant("Noel", "http://graph.facebook.com/noelvang/picture?type=normal", new IntProgress(0)), 
				new Participant("Helge", "http://graph.facebook.com/helgemunkjacobsen/picture?type=normal", new IntProgress(5))};
		
		DummyContent.addItem(new ExerciseChallenge(DummyContent.ITEMS.size(), editName.getText().toString(), "Description", new Repetition(Integer.parseInt(editLength.getText().toString())), participants, true));
		
		this.finish();
	}
}
