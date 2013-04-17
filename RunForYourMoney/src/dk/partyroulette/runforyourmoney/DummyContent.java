package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Participant[] participants1 = {new Participant("Du", "http://graph.facebook.com/dunguyen90/picture?type=normal", 50), 
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", 0), 
				new Participant("Noel", "http://graph.facebook.com/noelvang/picture?type=normal", 100), 
				new Participant("Helge", "http://graph.facebook.com/helgemunkjacobsen/picture?type=normal", 75)};
		Participant[] participants2 = {new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", 33)};
		
		addItem(new Challenge(1l, "5km run", "Run 5km before the 23rd of april.", new Repetition(5), participants1));
		addItem(new Challenge(2l, "20km run", "Run 20km bla bla bla.", new Repetition(20), participants2));
	}

	private static void addItem(Challenge item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.getId(), item);
	}
}
