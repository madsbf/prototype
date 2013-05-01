package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dk.partyroulette.runforyourmoney.datalayer.*;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Challenge> ITEMS = new ArrayList<Challenge>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<Long, Challenge> ITEM_MAP = new HashMap<Long, Challenge>();

	static {
		// Add 2 sample items.	
		Participant[] participants1 = { 
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", new RunObject(getFakeGpsData())),
				new Participant("Du", "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(3)), 
				new Participant("Noel", "http://graph.facebook.com/noelvang/picture?type=normal", new IntProgress(0)), 
				new Participant("Helge", "http://graph.facebook.com/helgemunkjacobsen/picture?type=normal", new IntProgress(5))};
		Participant[] participants2 = {
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", new IntProgress(6))};
		Participant[] participants3 = {
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", new IntProgress(3)),
				new Participant("Du", "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(6)),  
				new Participant("Noel", "http://graph.facebook.com/noelvang/picture?type=normal", new IntProgress(13)), 
				new Participant("Helge", "http://graph.facebook.com/helgemunkjacobsen/picture?type=normal", new IntProgress(11))};
		Participant[] participants4 = {
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", new IntProgress(10))};

		addItem(new ExerciseChallenge(1l, "5km run", "Run 5km before the 23rd of april.", new Repetition(5), participants1, true));
		addItem(new HealthChallenge(2l, "Stop smoking", "Stop smoking", new Repetition(12), participants2, true));
		addItem(new LearningChallenge(3l, "Get to class on time", "Get to class on time.", new Repetition(13), participants3, true));
		addItem(new ExerciseChallenge(4l, "20km run", "Run 20km bla bla bla.", new Repetition(20), participants4, false));
	}

	public static Profile PROFILE = new Profile("Mads", "http://graph.facebook.com/madsbf/picture?type=normal");

	public static void addItem(Challenge item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.getId(), item);
	}

	private static ArrayList<ArrayList<Float>> getFakeGpsData() 
	{
		ArrayList<ArrayList<Float>> gpsData = new ArrayList<ArrayList<Float>>();
	    ArrayList<Float> tmp = new ArrayList<Float>();
	    tmp.add((float) 55.780027);
	    tmp.add((float) 12.517684);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615378E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784327);
	    tmp.add((float) 12.518684);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.361538E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784427);
	    tmp.add((float) 12.518684);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615382E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784427);
	    tmp.add((float) 12.518484);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615382E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784427);
	    tmp.add((float) 12.518284);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615382E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784227);
	    tmp.add((float) 12.518384);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615383E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784127);
	    tmp.add((float) 12.518484);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615383E12);
	    gpsData.add(tmp);
	    tmp = null;
	    tmp = new ArrayList<Float>();
	    tmp.add((float) 55.784027);
	    tmp.add((float) 12.518584);
	    tmp.add((float) 20.0);
	    tmp.add((float) 1.3615384E12);
	    gpsData.add(tmp);
	    tmp = null;

	    return gpsData;
	}
}