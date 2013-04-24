package dk.partyroulette.runforyourmoney;

import android.view.View;

public class ViewUtilities 
{
	public static void updateBackgroundResourceWithRetainedPadding(View view, int resourceID)
	{
	    int bottom = view.getPaddingBottom();
	    int top = view.getPaddingTop();
	    int right = view.getPaddingRight();
	    int left = view.getPaddingLeft();
	    view.setBackgroundResource(resourceID);
	    view.setPadding(left, top, right, bottom);
	}

}
