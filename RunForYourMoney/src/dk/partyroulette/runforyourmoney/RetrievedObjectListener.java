package dk.partyroulette.runforyourmoney;

import java.util.List;

import com.parse.ParseObject;


public interface RetrievedObjectListener {
	public void onRetrievedContactObject(List<Contact> contacts);
	public void onRetrievedObject(List<ParseObject> obj);
}
