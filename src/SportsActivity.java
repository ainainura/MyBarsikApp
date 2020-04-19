package nu.edu.kz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class SportsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sports);

	}

	public void toWalkingActivity (View v) {
		Intent intent = new Intent(SportsActivity.this, WalkingActivity.class);
		startActivity(intent);
		
	}
	
	public void toRunningActivity (View v) {
		Intent intent = new Intent(SportsActivity.this, MainnActivity.class);
		startActivity(intent);			
	}

	public void toPushupsActivity (View v) {
		Intent intent = new Intent(SportsActivity.this, PushupsActivity.class);
		startActivity(intent);			
	}

	public void toShakingActivity (View v) {
		Intent intent = new Intent(SportsActivity.this, ShakingActivity.class);
		startActivity(intent);			
	}
	
	public void useEnergy (View v) {
		SharedPreferences settings = getSharedPreferences(MainActivity.myPrefsFile, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.commit();
		Intent intent = new Intent(SportsActivity.this, PetActivity.class);
		startActivity(intent);	
	}
	
}
