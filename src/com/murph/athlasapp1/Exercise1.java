package com.murph.athlasapp1;

import com.google.android.maps.GeoPoint;
import com.murph.db.DatabaseRouteController;
import com.murph.objects.Route66;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Exercise1 extends Activity implements OnClickListener
{

	// This is the first exercise class without the map
	// Calculating time & distance traveled.
	
	private long m_StartTime = 0L;
	private Handler m_Handler = new Handler();
	static final int m_UPDATE_INTERVAL = 1000;
	
	private LocationManager m_LocManager;
	private LocationListener m_locListener;
	private double m_Distance;
	
	GeoPoint m_GetUsersLocationA, m_GetUsersLocationB;
	Location m_LocationA, m_LocationB;
	
	Button m_MapDisplay, m_StartCountDown, m_StartTimer, m_StopTimer, m_MapDisplay2, m_MapDisplay3, m_SaveRun;
	TextView m_BtnLabel, m_TimeLabel, m_TimerLabel, m_DistTravelledLbl;
	String m_TimerStop1Str, m_DistStop1Str, m_TowerStr;
    int m_LatiInt = 0;
	int m_LongiInt = 0;
	int m_LatBInt = 0;
	int m_LongiBInt = 0;
	
	String m_TimeStr, m_DistanceStr, m_AthleteStr;
	Route66 m_RouteObj;
	
	@Override
	protected void onCreate(Bundle myBundle) 
	{
		super.onCreate(myBundle);
		setContentView(R.layout.exercise1_home);

		m_TimeLabel = (TextView) findViewById(R.id.countDownTv);
		m_BtnLabel = (TextView) findViewById(R.id.countDown);
		m_TimerLabel = (TextView) findViewById(R.id.textTimer);
		m_BtnLabel = (TextView) findViewById(R.id.countDown);
		m_DistTravelledLbl = (TextView) findViewById(R.id.txtMeasureDistance);
		
		// My Buttons.......................................
		m_MapDisplay = (Button) findViewById(R.id.btnMap);
		m_MapDisplay.setOnClickListener(this);
		m_StartCountDown = (Button) findViewById(R.id.countDown);
		m_StartCountDown.setOnClickListener(this);
		m_StartTimer = (Button) findViewById(R.id.btnTimer);
		m_StartTimer.setOnClickListener(this);
		m_StopTimer = (Button) findViewById(R.id.btnTimerStop);
		m_StopTimer.setOnClickListener(this);
		m_MapDisplay2 = (Button) findViewById(R.id.btnMap2);
		m_MapDisplay2.setOnClickListener(this);
		m_MapDisplay3 = (Button) findViewById(R.id.btnMap3);
		m_MapDisplay3.setOnClickListener(this);
		m_SaveRun = (Button) findViewById(R.id.btnTimeDistSaveEx1);
		m_SaveRun.setOnClickListener(this);
		
		// Getting the users location using GPS.
		m_LocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		m_locListener = new MyLocationListener();
		
		m_LocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*1000, 1, m_locListener);
//		+ String.format("%02d")
		m_DistTravelledLbl.setText("0.00");
		m_Distance = 0.00;
		
		//Checking to see its not empty.
		if(myBundle != null)
		{
			if(myBundle.containsKey("m_DistTravelled"))
			{
				m_Distance = myBundle.getDouble("m_DistTravelled");
			}
		}
		//Retrieving information from another class.
		Intent i = getIntent();
		m_AthleteStr = i.getExtras().getString("username");
		
	}
	
	// If the user tilts the device data is not lost.
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		outState.putDouble("m_DistTravelled", m_Distance);
		m_LocManager = null;
		m_locListener = null;
	}




	@Override
	public void onClick(View v)
	{
		double lat = 53.6551;
		double longg = -6.4220;
		
		switch (v.getId()) {
		case R.id.btnMap:
			Intent i = new Intent(getApplicationContext(), Exercise4.class);
			Exercise3.putLatLong(lat, longg);
            startActivity(i); 
		break;
		case R.id.btnMap2:
			Intent ii = new Intent(getApplicationContext(), Exercise3.class);
            startActivity(ii);
		break;
		case R.id.btnMap3:
			Intent iii = new Intent(getApplicationContext(), Exercise2.class);
			Exercise3.putLatLong(lat, longg);
            startActivity(iii);
		break;
		case R.id.btnTimeDistSaveEx1:
			
			 m_TimeStr = m_TimerLabel.getText().toString();
			 m_DistanceStr = m_DistTravelledLbl.getText().toString();
			
			DatabaseRouteController dbC = new DatabaseRouteController(this);
			
			dbC.open();
			
			m_RouteObj = dbC.insertRoute(m_AthleteStr, m_TimeStr, m_DistanceStr);
			
			Log.v("Athlete name: ", m_AthleteStr);
			Log.v("Athlete name: ", m_DistanceStr);
			Log.v("Athlete name: ", m_TimeStr);

			Toast.makeText(Exercise1.this, "Route added, yahoo!",
					Toast.LENGTH_LONG).show();
			
			dbC.close();
			
		break;
		case R.id.countDown:

			CountDownTimer timer1 = new CountDownTimer(90000, 1000){

				@Override 
				public void onFinish() 
				{
					m_TimeLabel.setText("Done!");
				}

				@Override
				public void onTick(long millisuntilFinished) 
				{
					int seconds = (int) (millisuntilFinished / 1000);
					int minutes = seconds / 60;
					seconds = seconds%60;
					m_TimeLabel.setText("" + minutes + ":" + String.format("%02d", seconds));
				}
				
			}.start();
			
		break;	
		case R.id.btnTimer:
			
			if(m_StartTime==0L)
			{
				m_StartTime = SystemClock.uptimeMillis();
				m_Handler.removeCallbacks(m_UpdateTimeTask);
				m_Handler.postDelayed(m_UpdateTimeTask, 100);
			}
			
			break;
			case R.id.btnTimerStop:
			
//				distance = getDistance(lati, longi, latB, longiB);
//				String myDouble = String.valueOf(distance);
				
				m_Handler.removeCallbacks(m_UpdateTimeTask);
				m_TimerLabel.setText(m_TimerStop1Str);
				m_StartTime = 0L;
				
//				m_DistTravelled.setText(myDouble);
			
			break;
		}
	}
	
	
		private Runnable m_UpdateTimeTask = new Runnable(){

			@Override
			public void run() 
			{
				final long start = m_StartTime;
				long millis = SystemClock.uptimeMillis() - start;
				
				int seconds = (int) (millis / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;
				
				m_TimerLabel.setText("" + minutes + ":" + String.format("%02d", seconds));
				m_TimerStop1Str =  minutes + ":" + String.format("%02d", seconds); 
				m_Handler.postDelayed(this, 200);
			}
			
		};
		
		// My inner class for calculating the distance to each new location.
		public class MyLocationListener implements LocationListener
		{
			private Location prevLoc;
			
			@Override
			public void onLocationChanged(Location arg0) 
			{
				if(prevLoc != null)
				{
					if(prevLoc == arg0)
					{
						return;
					}
					else
					{
						m_Distance += Math.abs(arg0.distanceTo(prevLoc));
						int kilometer = (int) m_Distance / 1000;
						m_Distance = m_Distance % 1000;
						prevLoc = new Location(arg0);
						m_DistTravelledLbl.setText("" + kilometer + ":" + Double.toString(((int) m_Distance)));
					}
				}
				else
				{
					prevLoc = new Location(arg0);
				}
			}

			@Override
			public void onProviderDisabled(String provider) 
			{
				Toast.makeText(getApplicationContext(), "GPS Gone AWOL!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onProviderEnabled(String provider) 
			{
				Toast.makeText(getApplicationContext(), "GPS Back, Yippee!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) 
			{
				
			}
			
		}
	
}
