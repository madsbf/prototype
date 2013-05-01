package dk.partyroulette.runforyourmoney.control;


import java.util.List;
import dk.partyroulette.runforyourmoney.datalayer.*;
import com.parse.ParseObject;


public interface RetrievedObjectListener {
	public void onRetrievedContactObject(List<Contact> contacts);
	public void onRetrievedChallengeObjects(List<Challenge> challenges);
	public void onRetrievedObject(List<ParseObject> obj);
}
