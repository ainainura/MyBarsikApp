package nu.edu.kz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedActivity extends Activity {
	private List<Meal> myMeals = new ArrayList<Meal>();
	private int money;
	TextView moneyText;
	MediaPlayer mp;

	SharedPreferences settings;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		settings = getSharedPreferences(MainActivity.myPrefsFile, 0);
		editor = settings.edit();
		money = settings.getInt("money", 0);
		
		moneyText = (TextView) findViewById(R.id.txtMoney);
		moneyText.setText(money + " tenge");
		
		mp = MediaPlayer.create(this, R.raw.sound1);
		
		populateMealList();
		populateListView();
		registerClickCallback();
	}

	private void populateMealList() {
		// TODO Auto-generated method stub
		myMeals.add(new Meal("Water ", 100, R.drawable.water));
		myMeals.add(new Meal("Ryegrass ", 300, R.drawable.grass15));
		myMeals.add(new Meal("Tall Fescue ", 350, R.drawable.grass12));
		myMeals.add(new Meal("Sausages ", 500, R.drawable.meat1));
		myMeals.add(new Meal("Fish ", 600, R.drawable.meat2));
		myMeals.add(new Meal("Cutlets ", 650, R.drawable.meat3));
		myMeals.add(new Meal("Sausage ", 600, R.drawable.meat4));
		myMeals.add(new Meal("Beef ", 800, R.drawable.meat5));
		myMeals.add(new Meal("Shrimps ", 900, R.drawable.meat6));
		myMeals.add(new Meal("Beef ", 1000, R.drawable.meat7));
		myMeals.add(new Meal("Crab Sticks ", 600, R.drawable.meat8));
		myMeals.add(new Meal("Chicken legs ", 850, R.drawable.meat9));
		myMeals.add(new Meal("Burger ", 700, R.drawable.burger1));
	}
	
	private void populateListView() {
		// TODO Auto-generated method stub
		ArrayAdapter<Meal> adapter = new MyListAdapter();
		
		ListView list = (ListView) findViewById(R.id.carsListView);
		list.setAdapter(adapter);
	}
	
	private void registerClickCallback() {
		// TODO Auto-generated method stub
		final ListView list = (ListView) findViewById(R.id.carsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				Meal clickedMeal = myMeals.get(position);
				String message = "You clicked position " + position
						+ " Which is car make " + clickedMeal.getName();
				Toast.makeText(FeedActivity.this, message, Toast.LENGTH_LONG).show();
			}
		});
	}


	
	private class MyListAdapter extends ArrayAdapter<Meal> {

		public MyListAdapter() {
			super(FeedActivity.this, R.layout.item_view, myMeals);
			
		}
		
		

		@SuppressLint("ShowToast")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if(itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
				
			}
			
			// find the car to work with
			final Meal currentMeal = myMeals.get(position);
			
			// Fill the view
			ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
			imageView.setImageResource(currentMeal.getIconID());
			
			// Make
			TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
			makeText.setText(currentMeal.getName());

			// Year
			TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
			yearText.setText(" "+currentMeal.getPrice()+" tenge");
			
			ImageButton buyButton = (ImageButton) itemView.findViewById(R.id.item_btnBuy);
			buyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(currentMeal.getPrice() > money){
						Toast.makeText(FeedActivity.this, "Please do sport to earn money!", Toast.LENGTH_SHORT).show();
					} else {
						mp.start();
						money -= currentMeal.getPrice();
						moneyText.setText(money + " tenge");
						editor.putInt("money", money);
						
						int energy = settings.getInt("energy", 0) + 1;
						if (energy > 17)
							energy = 17;
						
						editor.putInt("energy", energy);
						editor.commit();
					}
				}
			});

			// Condition

			return itemView;
			//return super.getView(position, convertView, parent);
		}	
	}
	
	
	public void toSportsActivity (View v){
		Intent intent = new Intent(getApplicationContext(), SportsActivity.class);
		startActivity(intent);
		finish();
	}

}
