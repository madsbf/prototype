package dk.partyroulette.runforyourmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.R.id;
import dk.partyroulette.runforyourmoney.R.layout;
import dk.partyroulette.runforyourmoney.DummyContent;

/**
 * A fragment representing a single Challenge detail screen. This fragment is
 * either contained in a {@link ChallengeListActivity} in two-pane mode (on
 * tablets) or a {@link ChallengeDetailActivity} on handsets.
 */
public class ChallengeDetailFragment extends Fragment implements OnClickListener
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
	
	private Button startButton;

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
		View rootView = inflater.inflate(R.layout.fragment_challenge_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.textName))
					.setText(mItem.getName());
			((ProgressBar) rootView.findViewById(R.id.myProgressBar))
			.setProgress(mItem.getProgress());
			((ProgressBar) rootView.findViewById(R.id.friendProgressBar))
			.setProgress(mItem.getFriendProgress());
		}
		
		startButton = (Button) rootView.findViewById(R.id.buttonStart);
		startButton.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View view) 
	{
		if(view.getId() == R.id.buttonStart)
		{
			Intent intent = new Intent(getActivity(), RunStatisticsActivity.class);
		    startActivity(intent);
		}
		
	}
}
