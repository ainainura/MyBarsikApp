package nu.edu.kz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class WalkingActivity extends Activity {
	
	private int money = 0;
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walking);
		
		settings = getSharedPreferences(MainActivity.myPrefsFile, 0);
		editor = settings.edit();
		
	}

	public void earnMoneyWalking (View v) {
		EditText moneyEdit = (EditText) findViewById(R.id.editTextWalk);
		String value = moneyEdit.getText().toString();
		
		int intVal = Integer.parseInt(value);
		
		
		money = settings.getInt("money", 0);
		
		
		money += intVal;
		
		editor.putInt("money", money);
		editor.commit();
		
		Intent intent =  new Intent(WalkingActivity.this, FeedActivity.class);
		startActivity(intent);
		finish();
	}

}
