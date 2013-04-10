package dk.partyroulette.runforyourmoney;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapUtilities {

	public static Bitmap loadBitmap(String url) {
		try {
			  return BitmapFactory.decodeStream((InputStream)new URL(url).getContent()); 
			} catch (MalformedURLException e) {
			  e.printStackTrace();
			} catch (IOException e) {
			  e.printStackTrace();
			}

	    return null;
	}
}
