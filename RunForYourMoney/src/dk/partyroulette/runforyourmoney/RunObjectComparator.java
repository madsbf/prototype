package dk.partyroulette.runforyourmoney;

import java.util.Comparator;

import dk.partyroulette.runforyourmoney.datalayer.RunObject;

public class RunObjectComparator implements Comparator<RunObject>{
	//comparator to compare the time of runobjects
	public int compare(RunObject r1, RunObject r2){
		return r1.getRunAt().compareTo(r2.getRunAt());
	}
}