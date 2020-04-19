package nu.edu.kz;

import java.util.Random;

import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;

public class PetActivity extends Activity {

	OurView ourView;
	Bitmap bars;
	float x, y;
	Sprite sprite;
	RelativeLayout fl;
	RelativeLayout rl2;
	LinearLayout ll;
	ImageView imageEnergy;
	boolean angry = false;
	MediaPlayer mp;
	boolean sleeping = false;
		
	int id;
	int value;
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet);
		
			
		//:TODO - set the value for name view from SharedPreferences file
		settings = getSharedPreferences(MainActivity.myPrefsFile, 0);
		String userName = settings.getString("name", "not defined");	
		int petEnergy = settings.getInt("energy", 0);
		sleeping = false;
			
		int resImage = getResource(petEnergy);
		imageEnergy = (ImageView) findViewById(R.id.energyBar);
		imageEnergy.setImageDrawable(getResources().getDrawable(resImage));
		ourView = new OurView(this);
		
		mp = MediaPlayer.create(this, R.raw.music);
		mp.setLooping(true);
		ll = (LinearLayout) findViewById(R.id.LinearLayout1);
		rl2 =(RelativeLayout) findViewById(R.id.relativeLayout2);
		
		
		fl = (RelativeLayout) findViewById(R.id.frameLayout);
		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
		        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relativeParams.addRule(RelativeLayout.BELOW, rl2.getId());
		relativeParams.addRule(RelativeLayout.ABOVE, ll.getId());
		fl.addView(ourView, relativeParams);
				
		value = 12;
		id = R.drawable.waving_barsik;
		
		x = y = 0;
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mp.pause();
		super.onPause();
		ourView.pause();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		sleeping = false;
		mp.start();
		super.onRestart();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		sleeping = false;
		mp.start();
		super.onResume();
		ourView.resume();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mp.pause();
		super.onDestroy();
		
	}

	@SuppressLint("WrongCall")
	public class OurView extends SurfaceView implements Runnable{

		Thread t = null;
		SurfaceHolder holder;
		boolean isItOk = false;
		boolean spriteLoaded = false;
		
		
		public OurView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			setZOrderOnTop(true);
			holder = getHolder();			
			holder.setFormat(PixelFormat.TRANSPARENT);
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Random rand = new Random();
			int imgBars = rand.nextInt(3) + 1;
			if(!sleeping && settings.getInt("energy", 0) > 15){
				fl.setBackgroundResource(R.drawable.mountains_day3);
				value = 6;
				id = R.drawable.smiling_barsik;
			} else if(!sleeping && settings.getInt("energy", 0) > 3 && imgBars == 1){
				fl.setBackgroundResource(R.drawable.mountains_day3);
				value = 8;
				id = R.drawable.barsik_turning_head;
			} else if (!sleeping && settings.getInt("energy", 0) > 3 && imgBars == 2){
				fl.setBackgroundResource(R.drawable.mountains_day3);
				value = 12;
				id = R.drawable.waving_barsik;
			} else if (!sleeping && settings.getInt("energy", 0) > 3 && imgBars == 3){
				fl.setBackgroundResource(R.drawable.mountains_day3);
				value = 8;
				id = R.drawable.blinking_barsik;
			} else if (!sleeping && settings.getInt("energy", 0) < 3 ){
				fl.setBackgroundResource(R.drawable.mountains_day3);
				value = 7;
				id = R.drawable.barsik_getting_angry;
			}
				
				
			bars = BitmapFactory.decodeResource(getResources(), id);
			sprite = new Sprite(OurView.this, bars, value);
			while (isItOk){
				// perform canvas drawing
				if(!holder.getSurface().isValid()){
					continue;
				}
				Canvas c = holder.lockCanvas();
				c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				//c.drawColor(Color.WHITE);
				onDraw(c);
				holder.unlockCanvasAndPost(c);	
			}
		}
		
		@SuppressLint("WrongCall")
		protected void onDraw(Canvas canvas){
			sprite.onDraw(canvas);
			
		}
		
		public void pause(){
			isItOk = false;
			while(true){
				try{
					t.join();
				} catch(InterruptedException e){
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}
		
		public void resume(){
			isItOk = true;
			t = new Thread(this);
			t.start();
		}
		
	}

	
	public void toFeedActivity (View v) {
		Intent intent = new Intent(PetActivity.this, FeedActivity.class);
		startActivity(intent);	
	}
	
	public void toSportsActivity (View v) {
		Intent intent = new Intent(PetActivity.this, SportsActivity.class);
		startActivity(intent);	
	}
	
	public void goToSleep(View v){
		sleeping = true;
		fl.setBackgroundResource(R.drawable.mountains_night3);
		onPause();
		value = 1;
		id = R.drawable.closed_eyes;
		onResume();
	}
	
	public void makeAngry (View v) {
		fl.setBackgroundResource(R.drawable.mountains_day3);
		onPause();
		value = 7;
		id = R.drawable.barsik_getting_angry;
		onResume();
	}
	
	public void makeWave (View v) {
		fl.setBackgroundResource(R.drawable.mountains_day3);
		onPause();
		value = 12;
		id = R.drawable.waving_barsik;
		onResume();
	}
	
	public void makeTurnHead (View v) {
		fl.setBackgroundResource(R.drawable.mountains_day3);
		onPause();
		value = 8;
		id = R.drawable.barsik_turning_head;
		onResume();
	}
	
	public void makeSmile (View v) {
		fl.setBackgroundResource(R.drawable.mountains_day3);
		onPause();
		value = 6;
		id = R.drawable.smiling_barsik;
		onResume();
	}
	
	public void makeBlink (View v) {
		fl.setBackgroundResource(R.drawable.mountains_day3);
		onPause();
		value = 8;
		id = R.drawable.blinking_barsik;
		onResume();
	}
	
	public void makeTalk (View v) {
		fl.setBackgroundResource(R.drawable.mountains_day3);
		onPause();
		value = 2;
		id = R.drawable.barsik_with_dialog;
		onResume();
	}
	
	
	
	public int getResource (int pos) {
		int res;
		if (pos == 17)
			res = R.drawable.energy_bar17;
		else if (pos == 16)
			res = R.drawable.energy_bar16;
		else if (pos == 15)
			res = R.drawable.energy_bar15;
		else if (pos == 14)
			res = R.drawable.energy_bar14;
		else if (pos == 13)
			res = R.drawable.energy_bar13;
		else if (pos == 12)
			res = R.drawable.energy_bar12;
		else if (pos == 11)
			res = R.drawable.energy_bar11;
		else if (pos == 10)
			res = R.drawable.energy_bar10;
		else if (pos == 9)
			res = R.drawable.energy_bar9;
		else if (pos == 8)
			res = R.drawable.energy_bar8;
		else if (pos == 7)
			res = R.drawable.energy_bar7;
		else if (pos == 6)
			res = R.drawable.energy_bar6;
		else if (pos == 5)
			res = R.drawable.energy_bar5;
		else if (pos == 4)
			res = R.drawable.energy_bar4;
		else if (pos == 3)
			res = R.drawable.energy_bar3;
		else if (pos == 2)
			res = R.drawable.energy_bar2;
		else
			res = R.drawable.energy_bar1;
		
		
		return res;
		
	}
	
}
