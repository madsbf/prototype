package dk.partyroulette.runforyourmoney.control;

import android.content.Context;

public interface VerificationMethod 
{
	public void start(Context con);
	
	public void end();

	boolean isVerified();

}
