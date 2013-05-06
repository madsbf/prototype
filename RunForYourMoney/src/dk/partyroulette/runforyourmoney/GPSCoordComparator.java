package dk.partyroulette.runforyourmoney;

import java.util.Comparator;
import java.util.List;

public class GPSCoordComparator implements Comparator<List<Float>>{
	//comparator to compare the time of two lists (index 3)
	public int compare(List<Float> list1, List<Float> list2){
		return list1.get(3).compareTo(list2.get(3));
	}

}
