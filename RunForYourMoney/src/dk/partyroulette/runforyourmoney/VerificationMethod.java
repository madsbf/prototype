package dk.partyroulette.runforyourmoney;

import android.content.Context;

public interface VerificationMethod 
{
	public void start(Context con);
	
	public void end();

	boolean isVerified();

}
