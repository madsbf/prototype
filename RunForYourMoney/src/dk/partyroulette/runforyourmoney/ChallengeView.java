package dk.partyroulette.runforyourmoney;

import java.text.DecimalFormat;
import java.util.ArrayList;
import dk.partyroulette.runforyourmoney.R;
import com.parse.ParseObject;
import dk.partyroulette.runforyourmoney.datalayer.*;
import dk.partyroulette.runforyourmoney.control.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChallengeView implements OnClickListener
{
	private Challenge challenge;
	private Context context;

	private ArrayList<ArrayList<Float>> gpsData;
	private boolean paused = true;
	private boolean runStarted = false;
	private LocationManager locationManager;

	private Button startButton;
	private Button stopButton;
	private Button debugButton;
	
	private TextView avgPace;
	private TextView length;
	private TextView time;

	private int primaryColor;
	private int secondaryColor;

	private static final String PRIMARY_COLOR_GREY = "#666666";
	private static final String SECONDARY_COLOR_GREY = "#a1a1a1";
	private int imageResourceId;

	public ChallengeView(Challenge challenge, Context context)
	{
		this.challenge = challenge;
		this.context = context;

		if(challenge instanceof ExerciseChallenge)
		{
			primaryColor = Color.parseColor("#669900");
			secondaryColor = Color.parseColor("#99CC00");
			imageResourceId = R.drawable.challenge_running;
		}
		else if(challenge instanceof HealthChallenge)
		{
			primaryColor = Color.parseColor("#CC0000");
			secondaryColor = Color.parseColor("#FF4444");
			imageResourceId = R.drawable.challenge_health;
		}
		else if(challenge instanceof LearningChallenge)
		{
			primaryColor = Color.parseColor("#9933CC");
			secondaryColor = Color.parseColor("#AA66CC");
			imageResourceId = R.drawable.challenge_learn;
		}
		else if(challenge instanceof MiscChallenge)
		{
			primaryColor = Color.parseColor("#0099CC");
			secondaryColor = Color.parseColor("#33B5E5");
			imageResourceId = R.drawable.challenge_misc;
		}
	}

	public void createDetailView(ViewGroup view)
	{
		Activity activity = (Activity) context;
		activity.setTitle(challenge.getName());
		activity.getActionBar().setIcon(imageResourceId);
		// activity.getActionBar().setBackgroundDrawable(new ColorDrawable(primaryColor));

		LayoutInflater inflater = activity.getLayoutInflater();

		LinearLayout layoutChallenge = (LinearLayout) view.findViewById(R.id.layoutChallenge);

		if(challenge instanceof ExerciseChallenge)
		{			
			LinearLayout challengeView = (LinearLayout) inflater.inflate(R.layout.challenge_run_detail,
					view, false);

			avgPace = (TextView) challengeView.findViewById(R.id.textAvgPace);
			time = (TextView) challengeView.findViewById(R.id.textTime);
			length = (TextView) challengeView.findViewById(R.id.textLength);

			avgPace.setText("Text Avg Pace");
			time.setText("Text Time");
			length.setText("Text Length");

			startButton = (Button) challengeView.findViewById(R.id.buttonStart);
			startButton.setOnClickListener(this);
			stopButton = (Button) challengeView.findViewById(R.id.buttonStop);
			stopButton.setOnClickListener(this);
			debugButton = (Button) challengeView.findViewById(R.id.buttonDebug);
			debugButton.setOnClickListener(this);
			
			layoutChallenge.addView(challengeView);
		}

		// Participants view
		LinearLayout layoutProgress = (LinearLayout) view.findViewById(R.id.layoutProgress);
		LinearLayout[] participantViews = new LinearLayout[challenge.getParticipants().length];
		for(int i = 0; i < challenge.getParticipants().length; i++)
		{
			LinearLayout participantView = (LinearLayout) inflater.inflate(R.layout.participant,
					view, false);

			TextView textUpperBound = (TextView) participantView.findViewById(R.id.textUpperBound);
			TextView textLowerBound = (TextView) participantView.findViewById(R.id.textLowerBound);

			textUpperBound.setText(String.valueOf(challenge.getRepetition().getAmount()));
			textLowerBound.setText("0");

			layoutProgress.addView(participantView);
			participantViews[i] = participantView;
		}

		new ImageLoader2(participantViews).execute(challenge.getParticipants());
	}

	public void createListView(View view)
	{		
		TextView textName = (TextView) view.findViewById(R.id.textName);
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		TextView textType = (TextView) view.findViewById(R.id.textChallenges);
		TextView textUpperBound = (TextView) view.findViewById(R.id.textUpperBound);
		TextView textLowerBound = (TextView) view.findViewById(R.id.textLowerBound);
		LinearLayout layoutParticipants = (LinearLayout) view.findViewById(R.id.layoutParticipants);
		ImageView imageChallenge = (ImageView) view.findViewById(R.id.imageChallenge);

		imageChallenge.setImageResource(imageResourceId);

		if(!challenge.isActive())
		{
			ViewUtilities.updateBackgroundResourceWithRetainedPadding(view, R.drawable.selector_grey);
			ColorMatrix matrix = new ColorMatrix();
			matrix.setSaturation(0); //0 means grayscale
			ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
			imageChallenge.setColorFilter(cf);
		}

		// check to see if each individual textview is null.
		// if not, assign some text!
		if (textName != null){
			textName.setText(challenge.getName());
			progressBar.setProgress(challenge.getParticipants()[0].getProgress().getPercentage(challenge.getRepetition().getAmount()));
			textType.setText(challenge.getTypeName());
			textUpperBound.setText(String.valueOf(challenge.getRepetition().getAmount()));
			textLowerBound.setText("0");

			Drawable drawable = progressBar.getProgressDrawable();
			if(challenge.isActive())
			{
				drawable.setColorFilter(new LightingColorFilter(0xFF000000, primaryColor));
			}
			else
			{
				drawable.setColorFilter(new LightingColorFilter(0xFF000000, Color.parseColor(PRIMARY_COLOR_GREY)));
			}
		}

		ImageView[] imageViews = new ImageView[challenge.getParticipants().length];
		for(int i = 0; i < challenge.getParticipants().length; i++)
		{
			ImageView imageView = new ImageView(context);
			imageView.setPadding(5, 5, 0, 5);
			layoutParticipants.addView(imageView);
			imageViews[i] = imageView;
		}

		new ImageLoader(imageViews).execute(challenge.getParticipants());
	}

	private class ImageLoader extends AsyncTask<Participant, Void, Bitmap[]>
	{
		private ImageView[] imageViews;
		private ArrayList<Participant> sortedParticipants;

		public ImageLoader(ImageView[] imageViews)
		{
			super();
			this.imageViews = imageViews;
		}

		@Override
		protected Bitmap[] doInBackground(Participant... participants) {

			if(participants.length > 0)
			{
				sortedParticipants = new ArrayList<Participant>();
				sortedParticipants.add(participants[0]);
				for(int i = 1; i < participants.length; i++)
				{
					int j = 1;
					while(i >= j && participants[i].getProgress().getPercentage(challenge.getRepetition().getAmount()) > sortedParticipants.get(i - j).getProgress().getPercentage(challenge.getRepetition().getAmount()))
					{
						j++;
					}
					sortedParticipants.add(i - j + 1, participants[i]);
				}

				Bitmap[] bitmaps = new Bitmap[participants.length];

				for(int i = 0; i < participants.length; i++)
				{
					bitmaps[i] = BitmapUtilities.loadBitmap(sortedParticipants.get(i).getImageUrl());
				}

				return bitmaps;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap[] bitmaps) 
		{
			for(int i = 0; i < bitmaps.length; i++)
			{
				Bitmap bitmap;

				if(challenge.isActive())
				{
					bitmap = bitmaps[i];
				}
				else
				{
					bitmap = BitmapUtilities.toGrayscale(bitmaps[i]);
				}

				int size = 40;
				switch(i)
				{
				case 0:
					size = 80;
					break;
				case 1:
					size = 60;
					break;
				case 2:
					size = 60;
					break;
				}

				imageViews[i].setImageBitmap(BitmapUtilities.roundCornersBitmap(Bitmap.createScaledBitmap(BitmapUtilities.cropBitmap(bitmap), size, size, true)));
			}
		}
	}

	private class ImageLoader2 extends AsyncTask<Participant, Void, Bitmap[]>
	{
		private LinearLayout[] parents;
		ArrayList<Participant> sortedParticipants;

		public ImageLoader2(LinearLayout[] parents)
		{
			super();
			this.parents = parents;
		}

		@Override
		protected Bitmap[] doInBackground(Participant... participants) {

			if(participants.length > 0)
			{
				sortedParticipants = new ArrayList<Participant>();
				sortedParticipants.add(participants[0]);
				for(int i = 1; i < participants.length; i++)
				{
					int j = 1;
					while(i >= j && participants[i].getProgress().getPercentage(challenge.getRepetition().getAmount()) > sortedParticipants.get(i - j).getProgress().getPercentage(challenge.getRepetition().getAmount()))
					{
						j++;
					}
					sortedParticipants.add(i - j + 1, participants[i]);
				}

				Bitmap[] bitmaps = new Bitmap[participants.length];

				for(int i = 0; i < participants.length; i++)
				{
					bitmaps[i] = BitmapUtilities.loadBitmap(sortedParticipants.get(i).getImageUrl());
				}

				return bitmaps;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap[] bitmaps) 
		{
			for(int i = 0; i < bitmaps.length; i++)
			{
				Bitmap bitmap;

				ProgressBar progressBar = (ProgressBar) parents[i].findViewById(R.id.progressBar);
				ImageView imageParticipant = (ImageView) parents[i].findViewById(R.id.imageParticipant);

				progressBar.setProgress(sortedParticipants.get(i).getProgress().getPercentage(challenge.getRepetition().getAmount()));

				if(challenge.isActive())
				{
					bitmap = bitmaps[i];
				}
				else
				{
					bitmap = BitmapUtilities.toGrayscale(bitmaps[i]);
				}

				int size = 50;
				switch(i)
				{
				case 0:
					size = 100;
					break;
				case 1:
					size = 75;
					break;
				case 2:
					size = 75;
					break;
				}

				imageParticipant.setImageBitmap(BitmapUtilities.roundCornersBitmap(Bitmap.createScaledBitmap(BitmapUtilities.cropBitmap(bitmap), size, size, true)));
			}
		}

	}

	@Override
	public void onClick(View view) 
	{
		if(view.getId() == R.id.buttonStart)
		{
			if(runStarted)
			{
				pause();
			} else 
			{
				startRun();
			}
		} else if (view.getId() == R.id.buttonStop)
		{
			end();
		} else if (view.getId() == R.id.buttonDebug)
		{
			gpsData = DummyContent.getFakeGpsData();
		}
	}

	private void startRun() 
	{
		gpsData = new ArrayList<ArrayList<Float>>();
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		//get initial location
		Location location = locationManager.getLastKnownLocation("network");
		//receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		paused = false;
		runStarted = true;
		startButton.setText("Pause");
		stopButton.setVisibility(View.VISIBLE);
	}

	public void pause() 
	{
		if(paused)
		{
			paused = false;
			startButton.setText("Pause");
		} else {
			paused = true;
			startButton.setText("Un-pause");
		}
	}

	public void end() {
		//stop receiving updates
		locationManager.removeUpdates(locationListener);
		//prepare dataobject
		RunObject ro = new RunObject(gpsData);
		//RunObject ro = new RunObject(createFakeData()); DEBUG
		//get name
		String fullname = Contact.getCurrentUser();
		
		if(ro.getLength()!=0.0)
		{
			//TODO: add challenge id to name
			ParseObject gpsData = new ParseObject("GPSData");
			for(ArrayList<Float> gpsCoord : ro.getGpsData())
			{
				ParseObject gpsObject = new ParseObject("GPS_" + fullname);
				gpsObject.put("lat",gpsCoord.get(0));
				gpsObject.put("ln", gpsCoord.get(1));
				gpsObject.put("acc", gpsCoord.get(2));
				gpsObject.put("time", gpsCoord.get(3));
				gpsData.add("gpsCoord", gpsObject);
				//TODO: add challenge id to GPS data
			}
			gpsData.put("owner", fullname);
			gpsData.saveInBackground();
		}
		stopButton.setVisibility(View.INVISIBLE);
		startButton.setText("Go!");
		paused = true;
		runStarted = false;
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
				
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				RunObject ro = new RunObject(gpsData);
				if(ro.getLength()!=0.0)
				{
					System.out.println(ChallengeView.this.avgPace.toString());
					ChallengeView.this.avgPace.setText("Avg Pace: " + ((int)Math.round(ro.getAvgPaceMinutes())) + ":" + ((int) Math.round(ro.getAvgPaceSeconds())) + " min/km");
					ChallengeView.this.length.setText("Length: " + df.format(ro.getLength()/1000) + " km");
					ChallengeView.this.time.setText("Time: " + ro.getHour() +":"+ro.getMinutes()+":"+ro.getSeconds());
				}
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