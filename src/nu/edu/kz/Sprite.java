package nu.edu.kz;

import nu.edu.kz.PetActivity.OurView;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

@SuppressLint("DrawAllocation")
public class Sprite {

	int height, width;
	Bitmap ba;
	OurView ov;
	int currentFrame = 0;
	int value;
	
	public Sprite(OurView ourView, Bitmap bars, int val) {
		// TODO Auto-generated constructor stub
		ba = bars;
		ov = ourView;
		// 4 columns
		height = ba.getHeight();
		value = val;
		width = ba.getWidth()/value;

	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		update();
		int srcX = currentFrame*width;
		Rect src = new Rect(srcX, 0, srcX + width, height);
		Rect dst = new Rect(canvas.getWidth()/2 - width/2, canvas.getHeight() - height, canvas.getWidth()/2 + width/2, canvas.getHeight());		
		canvas.drawBitmap(ba, src, dst, null);
	}

	private void update() {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(230);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentFrame = ++currentFrame % value;

	}

}
