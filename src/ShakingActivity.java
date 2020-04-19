package nu.edu.kz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ShakingActivity extends Activity implements SensorEventListener {

	private SensorManager manager;
	public static final String PREFS_NAME = "PREF_FILE";
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shaking);
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		txt = (TextView) findViewById(R.id.textView1);
	}
	
	TextView txt;

	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float threshold = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

		if (threshold >= 2)
		{
			Toast.makeText(this, "WELL DONE!", Toast.LENGTH_LONG).show();
			txt.setText("Mood of your pet was increased");
			int prev = settings.getInt("mood", 0);
			if (prev == 16) return;
			editor.putInt("mood", prev + 1);
			editor.commit();
		}
	}

	
	public void runButton(View v) {
		finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			getAccelerometer(event);
		}
	}



}
