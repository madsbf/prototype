package dk.partyroulette.runforyourmoney;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dk.partyroulette.runforyourmoney.ChallengeListFragment.Callbacks;
import dk.partyroulette.runforyourmoney.control.*;
import dk.partyroulette.runforyourmoney.R;

public class OverviewFragment extends Fragment implements OnClickListener
{
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public OverviewFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.overview,
				container, false);
		
		rootView.setOnClickListener(this);

		ImageView imageProfile = (ImageView) rootView.findViewById(R.id.imageProfile);
		TextView textName = (TextView) rootView.findViewById(R.id.textName);
		TextView textChallenges = (TextView) rootView.findViewById(R.id.textChallenges);
		TextView textCoins = (TextView) rootView.findViewById(R.id.textCoins);

		//textName.setText(DummyContent.PROFILE.getName());
		SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("runforyourmoney", 0);
		String name = settings.getString("name", "");
		if(!name.equals(""))
		{
			textName.setText(name);
		} else {
			textName.setText(DummyContent.PROFILE.getName()); //probably need a better strategy here.
		}
		textChallenges.setText("Current challenges: " + DummyContent.ITEMS.size());
		
		textCoins.setText("Coins: " + DummyContent.PROFILE.getCoins());

		new ImageLoader(imageProfile).execute();

		return rootView;
	}

	private class ImageLoader extends AsyncTask<Void, Void, Bitmap>
	{
		private ImageView imageProfile;

		public ImageLoader(ImageView imageProfile)
		{
			super();
			this.imageProfile = imageProfile;
		}

		@Override
		protected Bitmap doInBackground(Void... args) 
		{
			return BitmapUtilities.loadBitmap(DummyContent.PROFILE.getImageUrl());
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) 
		{
			Bitmap fixedBitmap = BitmapUtilities.roundCornersBitmap(Bitmap.createScaledBitmap(BitmapUtilities.cropBitmap(bitmap), 100, 100, false));
			imageProfile.setImageBitmap(fixedBitmap);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}
	
	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;
	
	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks 
	{
		/**
		 * Callback for when an item has been selected.
		 */
		public void onProfileSelected();
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() 
	{
		@Override
		public void onProfileSelected() {}
	};

	@Override
	public void onClick(View v) 
	{
		mCallbacks.onProfileSelected();
	}

}
