package dk.partyroulette.runforyourmoney;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.control.RetrievedObjectListener;
import dk.partyroulette.runforyourmoney.datalayer.Challenge;
import dk.partyroulette.runforyourmoney.datalayer.Contact;
import dk.partyroulette.runforyourmoney.datalayer.ExerciseChallenge;
import dk.partyroulette.runforyourmoney.datalayer.HealthChallenge;
import dk.partyroulette.runforyourmoney.datalayer.IntProgress;
import dk.partyroulette.runforyourmoney.datalayer.LearningChallenge;
import dk.partyroulette.runforyourmoney.datalayer.Participant;
import dk.partyroulette.runforyourmoney.datalayer.Repetition;
import dk.partyroulette.runforyourmoney.datalayer.RunObject;

/**
 * A list fragment representing a list of Challenges. This fragment also
 * supports tablet devices by allowing list items to be given an 'activated'
 * state upon selection. This helps indicate which item is currently being
 * viewed in a {@link ChallengeDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ChallengeListFragment extends ListFragment implements RetrievedObjectListener
{
	
	private ChallengeListAdapter adapter;

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(Long id);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(Long id) {}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ChallengeListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		// TODO: replace with a real list adapter.
		adapter = new ChallengeListAdapter(getActivity(),
				android.R.id.text1, DummyContent.ITEMS);
		setListAdapter(adapter);
	}
	

	@Override
	  public void onResume() 
	{
	     super.onResume();
   		 Challenge.retrieveChallengesFromDBForUser(this, Contact.getCurrentUser());
	     adapter.notifyDataSetChanged();
	  }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
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

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).getId());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}
	
	

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	@Override
	public void onRetrievedContactObject(List<Contact> contacts) {
		// TODO Auto-generated method stub
		
	}

	public void retrieveChallengeObjects(){
  		 Challenge.retrieveChallengesFromDBForUser(this, Contact.getCurrentUser());

	}
	@Override
	public void onRetrievedChallengeObjects(List<Challenge> challenges) {
		System.out.println("LENGTH OF CHALLENGES: "+challenges.size());
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");  

		DummyContent.reload();
		
		for(Challenge c: challenges){
			for(Participant p: c.getParticipants()){
				if(p.getName().equals(Contact.getCurrentUser())){
					
					
					for(Participant par: c.getParticipants()){
						if(par.getProgress().getInteger() >= c.getLength()){
							if(par.getName().equals(Contact.getCurrentUser())){
								showMessageDialog("Congratulations", "You are the winner of challenge "+c.getName()+".");
							}else{
								showMessageDialog("Lost", "You are the loser of challenge "+c.getName()+".");
							}
							
							Challenge.declineChallenge(c.getIdentifier());
							
						}
					}
					
					Date today = Calendar.getInstance().getTime();
					
					if(c.getDeadline().compareTo(today)<0){
						showMessageDialog("Outdated Challenge", "Challenge "+c.getName()+" is outdated with no winner and will be deleted from your challenges.");
						Challenge.declineChallenge(c.getIdentifier());
						return;
					}
						
					

					if(!p.getAccepted()){
						showAcceptChallengeAlert(c.getChallengeOwner()+" has challenged you to run "+c.getLength()+" km. before "+df.format(c.getDeadline())+".",c.getIdentifier());
					}
				}
				
			}
			
			ExerciseChallenge exerciseChallenge = new ExerciseChallenge(DummyContent.ITEMS.size() + 1, c.getName(), c.getDescription(), new Repetition(c.getLength()), c.getParticipants(), true);
			DummyContent.addItem(exerciseChallenge);
		}
		
		for(Challenge c: DummyContent.ITEMS){
			System.out.println(c.getName());

		}
		adapter = new ChallengeListAdapter(getActivity(), android.R.id.text1, DummyContent.ITEMS);
		setListAdapter(adapter);
		/*
		Participant[] participants1 = { 
				new Participant("Du", "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(3)), 
				new Participant("Noel", "http://graph.facebook.com/noelvang/picture?type=normal", new IntProgress(0)), 
				new Participant("Helge", "http://graph.facebook.com/helgemunkjacobsen/picture?type=normal", new IntProgress(5))};
		
		ITEMS.add(new ExerciseChallenge(1l, "5km run", "Run 5km before the 23rd of april.", new Repetition(5), participants1, true));
		
		
		adapter = new ChallengeListAdapter(getActivity(),
				android.R.id.text1, ITEMS);
		setListAdapter(adapter);
		*/
	}

	@Override
	public void onRetrievedObject(List<ParseObject> obj) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void showMessageDialog(String title,String msg){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(msg)
		.setCancelable(false)

		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				retrieveChallengeObjects();

			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

		
	}

	private void showAcceptChallengeAlert(String msg, final String identifier){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		alertDialogBuilder.setTitle("Accept Challenge?");

		// set dialog message
		alertDialogBuilder
		.setMessage(msg)
		.setCancelable(false)
		.setNegativeButton("Decline",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				Challenge.declineChallenge(identifier);
			}
		})

		.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				Challenge.acceptChallenge(identifier);
				retrieveChallengeObjects();

			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}
}