package nu.edu.kz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Build;

public class PushupsActivity extends Activity {
	
	ImageButton pushButton;
	TextView counter;
	TextView money;
	TextView instructions;
	int counterValue = 0;
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	int earnedMoney = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pushups);
		
		settings = getSharedPreferences(MainActivity.myPrefsFile, 0);
		editor = settings.edit();
		
		pushButton = (ImageButton) findViewById(R.id.pushButton);
		counter = (TextView) findViewById(R.id.counter);
		money = (TextView) findViewById(R.id.money);
		instructions = (TextView) findViewById(R.id.instructions);
		instructions.setText("Push the button with your nose while doing push-ups!");
		
		money.setText(String.valueOf(0));
		counter.setText(String.valueOf(0));		

	}
	
	
	public void countPushUps(View view){
		counterValue++;
		counter.setText(String.valueOf(counterValue));
		earnedMoney = counterValue * 10;
		money.setText(""+earnedMoney + " tenge");
		
		int mon = settings.getInt("money", 0) + earnedMoney;
		editor.putInt("money", mon);
		editor.commit();
	}
}
