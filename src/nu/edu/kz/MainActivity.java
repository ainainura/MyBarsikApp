package nu.edu.kz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity {
	
	public static final String myPrefsFile = "PREF_FILE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//:TODO goto LoginActivity or PetActivity
		SharedPreferences settings = getSharedPreferences(myPrefsFile, 0);
		
		/*
		 * Debug mode
		 */
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("TOTAL_MONTH", 3500);
		editor.putInt("TOTAL_CALORIES", 4900);
		editor.putInt("TOTAL_TIME", 122);
		editor.commit();
		if (settings.getString("name", null) != null) {
			// goto LoginActivity
			Intent intent = new Intent(getApplicationContext(), StartActivity.class);
			startActivity(intent);
			finish();
		} else {
			// goto PetActivity
			Intent intent = new Intent(getApplicationContext(), StartActivity.class);
			startActivity(intent);
			finish();
		}
	}

	

	
}
