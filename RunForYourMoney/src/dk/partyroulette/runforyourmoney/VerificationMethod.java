package dk.partyroulette.runforyourmoney;

public interface VerificationMethod 
{
	public void start();
	
	public void end();

	boolean isVerified();

}
