package dk.partyroulette.runforyourmoney;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dk.partyroulette.runforyourmoney.control.*;
import dk.partyroulette.runforyourmoney.R;

public class OverviewFragment extends Fragment 
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

		ImageView imageProfile = (ImageView) rootView.findViewById(R.id.imageProfile);
		TextView textName = (TextView) rootView.findViewById(R.id.textName);
		TextView textChallenges = (TextView) rootView.findViewById(R.id.textChallenges);

		textName.setText(DummyContent.PROFILE.getName());
		textChallenges.setText("Current challenges: " + DummyContent.ITEMS.size());

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

}
