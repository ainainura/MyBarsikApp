package nu.edu.kz;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class RunningService extends Service  implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {


	private LocationClient locationClient;

	private LocationRequest mLocationRequest;
	boolean mUpdatesRequested;

	private static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;

	public static double distance = 0;
	
	@Override
	public void onCreate() {

		locationClient = new LocationClient(this, this, this);

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		

		mPrefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();

		mUpdatesRequested = true;
		mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
		mEditor.commit();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "STARTED SERVICE", Toast.LENGTH_SHORT).show();
		locationClient.connect();
		Log.i("service", "started");
		distance = 0;
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
		if (mUpdatesRequested) {
			locationClient.requestLocationUpdates(mLocationRequest, this);
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG).show();
	}

	Location currentBestLocation = null;
	Location prevAverage = null;
	long lastUpdatedTime = 0;
	
	ArrayList <Location> allLocations = new ArrayList<Location> ();
	@Override
	public void onLocationChanged(Location loc) {
		
		if (LinearSquareMethod.isBetterLocation(loc, currentBestLocation)) {
			currentBestLocation = loc;
			allLocations.add(loc);
		}
		
		long now = System.currentTimeMillis();
		
		if (now - lastUpdatedTime >= UPDATE_TIME) {
				Log.i(Integer.toString(allLocations.size()), " = size of locations array");
				
				Location newLocation = LinearSquareMethod.average(allLocations);
				allLocations.clear();
				
				double dt = now - lastUpdatedTime;
				
				if (prevAverage != null) {
					double delta = prevAverage.distanceTo(newLocation);
					Log.i(Double.toString(newLocation.getAccuracy()), Double.toString(delta));
					if (delta >= 3) {
						if (distance == 0) {
							distance += delta * 1.5;
						} else {
							distance += delta;
						}
					}
				}
				prevAverage = newLocation;
				
				lastUpdatedTime = now;
		}
		
	}
	
	private static final long UPDATE_TIME = 1000 * 10;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
