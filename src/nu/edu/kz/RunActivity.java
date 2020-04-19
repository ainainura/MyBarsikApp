package nu.edu.kz;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;


import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class RunActivity extends Activity {

	public static final String PREFS_NAME = "PREF_FILE";

	TextView totalTime, totalDistance, totalMoney, recording;
	Button ok;
	int totalMoneyInt = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);
		totalTime = (TextView) findViewById(R.id.textView2);
		totalDistance = (TextView) findViewById(R.id.textView4);
		totalMoney = (TextView) findViewById(R.id.textView5);
		ok = (Button) findViewById(R.id.Button01);

		h = new Handler();

		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();


	}

	SharedPreferences settings;
	SharedPreferences.Editor editor;

	public void runButton(View v) {
		String text = ok.getText().toString();
		if (text.equalsIgnoreCase("GO BACK")) {
			long now = System.currentTimeMillis() - time;  
			editor.putInt("TOTAL_RUNS", settings.getInt("TOTAL_RUNS", 0) + 1);
			editor.putInt("TOTAL_MONTH", settings.getInt("TOTAL_MONTH", 0) + (int) dist);
			editor.putInt("TOTAL_TIME", settings.getInt("TOTAL_TIME", 0) + (int) now / (1000 * 60));
			editor.putInt("money", settings.getInt("money", 0) + totalMoneyInt);
			editor.commit();
			
			Intent intent = new Intent(this, MainnActivity.class);
			startActivity(intent); 
			return;
		}
		if (!running) {
			ok.setText("FINISH RUN");
			running = true;
			t.start();
			startService(new Intent(this, RunningService.class));
		} else {
			ok.setText("GO BACK");
			running = false;
			stopService(new Intent(this, RunningService.class));;
		}
	}

	public boolean working = false;

	Thread t = new Thread(new Runnable() {
		public void run() {
			time = System.currentTimeMillis();
			while (running) {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
					h.post(updateProgress);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});

	private long time;
	Handler h;

	private Runnable updateProgress = new Runnable() {

		@Override
		public void run() {
			long now = System.currentTimeMillis() - time;      

			String hh = "";
			String mm = "";
			String ss = "";
			now /= 1000;
			int h = (int) (now / 3600);
			now -= h * 3600;
			int m = (int) (now / 60);
			now -= m * 60;

			int s = (int) now;

			if (h < 10) hh = "0";
			if (m < 10) mm = "0";
			if (s < 10) ss = "0";
			hh += Integer.toString(h);
			mm += Integer.toString(m);
			ss += Integer.toString(s);

			String formattedDate = hh + ":" + mm + ":" + ss;

			totalTime.setText(formattedDate);

			dist = (long) RunningService.distance;
			String text = Long.toString(dist / 1000) + "." + Long.toString((dist % 1000) / 100) + " km";
			totalDistance.setText(text);

			totalMoneyInt = (int) (dist * 1.1);
			totalMoney.setText(Integer.toString((int)(dist * 1.1)));
		}
	};

	long dist = 0;

	protected void onDestroy() {
		super.onDestroy();
		stopService(new Intent(this, RunningService.class));
	};

	private SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
	private boolean running = false;
}
