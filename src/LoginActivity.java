package nu.edu.kz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	TextView playerInfo;
	TextView height;
	TextView weight;
	TextView prefTime;
	Button createPet; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		playerInfo = (TextView) findViewById(R.id.playerInfo);
		height = (TextView) findViewById(R.id.height);
		weight = (TextView) findViewById(R.id.weight);
		prefTime = (TextView) findViewById(R.id.prefTime);
		createPet = (Button) findViewById(R.id.createPet);	
		
		Typeface font1 = Typeface.createFromAsset(getAssets(), "Antonio-Bold.ttf");
		//playerInfo.setTypeface(font1);
		playerInfo.setTextColor(Color.WHITE);
		//name.setTypeface(font1);
		//height.setTypeface(font1);
		height.setTextColor(Color.WHITE);
		//weight.setTypeface(font1);
		weight.setTextColor(Color.WHITE);
		//prefTime.setTypeface(font1);
		prefTime.setTextColor(Color.WHITE);
		//createPet.setTypeface(font1); 
		createPet.setTextColor(Color.WHITE);
		
	}

	
	public void toPetActivity (View v) {
		String name = "HERO"; // debug mode
		String weight = ((EditText) findViewById(R.id.weight1)).getText().toString();
		Log.i(null, name);
		int initialEnergy = 2;
		int initialMoney = 2000;
		int initialMood = 16;
		
		//:TODO - put data to SharedPreferences file
		SharedPreferences settings = getSharedPreferences(MainActivity.myPrefsFile, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("WEIGHT", Integer.parseInt(weight));
		editor.putString("name", name);
		editor.putInt("energy", initialEnergy);
		editor.putInt("money", initialMoney);
		editor.putInt("mood", initialMood);
		
		editor.commit();
		
		Intent intent = new Intent(getApplicationContext(), PetActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);		
	}
	
	

}
