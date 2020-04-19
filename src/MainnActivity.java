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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Build;

public class MainnActivity extends Activity {
  
	public static final String PREFS_NAME = "PREF_FILE";
	TextView totalMonth, totalRuns, totalCalories, totalTime;
	ImageButton home, run;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainn);
	    settings = getSharedPreferences(PREFS_NAME, 0);
	    editor = settings.edit();
	    
	    /*
	     * For debug mode
	     */
	    editor.putInt("TOTAL_MONTH", 6500);
	    editor.putInt("TOTAL_", 6500);
	    
	    // ------------
	    
	    totalMonth = (TextView) findViewById(R.id.textView1);
	    totalRuns = (TextView) findViewById(R.id.textView2);
	    totalCalories = (TextView) findViewById(R.id.textView3);
	    totalTime = (TextView) findViewById(R.id.textView4);
	    
	    long dist = settings.getInt("TOTAL_MONTH", 0);
	    
		String text = Long.toString(dist / 1000) + "." + Long.toString((dist % 1000) / 100) + " km";

	    totalMonth.setText(text);
	    totalRuns.setText(Integer.toString(settings.getInt("TOTAL_RUNS", 0)));
	    
	    int userWeight = settings.getInt("WEIGHT", 75);
	    int calories = userWeight * settings.getInt("TOTAL_MONTH", 0);
	    
	    totalCalories.setText(Integer.toString(calories / 1000) + "k");
	    totalTime.setText(Integer.toString(settings.getInt("TOTAL_TIME", 0)) + " m");
	    
	    home = (ImageButton) findViewById(R.id.imageButton1);
	    run = (ImageButton) findViewById(R.id.imageButton2);
	   
	}
	
	public void goHome(View v) {
		Intent intent = new Intent(this, PetActivity.class);
	    startActivity(intent);  
	}
	
	public void startRunning(View v) {
	    Intent intent = new Intent(this, RunActivity.class);
	    startActivity(intent);  
	}
}
