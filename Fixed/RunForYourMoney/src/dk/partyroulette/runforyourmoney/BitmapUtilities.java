package dk.partyroulette.runforyourmoney;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

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

	public static Bitmap toGrayscale(Bitmap bmpOriginal)
	{        
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();    

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	public static Bitmap cropBitmap(Bitmap inputBitmap)
	{
		if (inputBitmap.getWidth() >= inputBitmap.getHeight())
		{
			return Bitmap.createBitmap(
					inputBitmap, 
					inputBitmap.getWidth()/2 - inputBitmap.getHeight()/2,
					0,
					inputBitmap.getHeight(), 
					inputBitmap.getHeight()
					);
		}
		else
		{
			return Bitmap.createBitmap(
					inputBitmap,
					0, 
					inputBitmap.getHeight()/2 - inputBitmap.getWidth()/2,
					inputBitmap.getWidth(),
					inputBitmap.getWidth() 
					);
		}
	}

	public static Bitmap roundCornersBitmap(Bitmap inputBitmap)
	{
		Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap.getWidth(),
				inputBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(outputBitmap);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, inputBitmap.getWidth(), inputBitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 12;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(inputBitmap, rect, rect, paint);

		return outputBitmap;
	}
}
