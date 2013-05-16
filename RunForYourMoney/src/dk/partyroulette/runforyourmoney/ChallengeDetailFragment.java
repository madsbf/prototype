package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import dk.partyroulette.runforyourmoney.datalayer.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.R.id;
import dk.partyroulette.runforyourmoney.R.layout;


/**
 * A fragment representing a single Challenge detail screen. This fragment is
 * either contained in a {@link ChallengeListActivity} in two-pane mode (on
 * tablets) or a {@link ChallengeDetailActivity} on handsets.
 */
public class ChallengeDetailFragment extends Fragment
{
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Challenge mItem;

	private ChallengeView challengeView = null;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ChallengeDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getLong(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_challenge_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) 
		{
			challengeView = new ChallengeView(mItem, getActivity());
			challengeView.createDetailView(rootView);
		}

		return rootView;
	}

	@Override
	public void onPause()
	{
		//end run
		if(challengeView != null)
		{
			challengeView.end();
		}
	}
	
	@Override
	public void onStop()
	{
		//end run
		if(challengeView != null)
		{
			challengeView.end();
		}
	}


}