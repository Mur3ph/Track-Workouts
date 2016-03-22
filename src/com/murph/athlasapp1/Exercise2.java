package com.murph.athlasapp1;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class Exercise2 extends MapActivity implements LocationListener, OnClickListener
{

	// This class is used for drawing the users movements on a map
	// Also a timer & distance calculation done!
	
	MapView m_MapView;
	long m_StartLong, m_StopLong;
	MyLocationOverlay m_MyCompassOverlay;
	MapController m_MyMapController;
	int m_XInt, m_YInt;
	GeoPoint m_TouchGeoPoint = null;
	Drawable m_MyDrawable;
	List<Overlay> m_MyOverlayList;
	List<Location> m_MyCoordinatesList;
	LocationManager m_MyLocManager;
	String m_MyTowersStr;
	int m_LatInt = 0;
	int m_LongiInt = 0;
	String m_MessageStr = "Sorry, couldn't get a provider!";
	Button m_BtnStart, m_BtnStop;
	
	private long m_StartTime = 0L;
	private Handler m_Handler = new Handler();
	static final int m_UPDATE_INTERVAL = 1000;
	TextView m_BtnLabel, m_TimeLabel, m_TimerLabel, m_DistTravelled;
	String m_TimerStop1Str, m_DistStop1Str;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise4_map);
		m_MapView = (MapView) findViewById(R.id.mapViewMain3);
		m_MapView.setBuiltInZoomControls(true);
		m_MyCoordinatesList = new ArrayList<Location>();
		
		m_DistTravelled = (TextView) findViewById(R.id.txtMeasureDistanceMap); 
		m_TimerLabel = (TextView) findViewById(R.id.txtTimerMap);
		
		m_BtnStart = (Button) findViewById(R.id.btnStartMapRoute);
		m_BtnStart.setOnClickListener(this);
		m_BtnStop = (Button) findViewById(R.id.btnStopMapRoute);
		m_BtnStop.setOnClickListener(this);
		
		// Inner class for user touching the device screen.
		Touchy myTouchingObj = new Touchy();
		m_MyOverlayList = m_MapView.getOverlays();
		m_MyOverlayList.add(myTouchingObj);
		
		m_MyMapController = m_MapView.getController();
		
		m_MyDrawable = getResources().getDrawable(R.drawable.marker2);
		
		// Getting the users location
		m_MyLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria crit = new Criteria();
		
		m_MyTowersStr = m_MyLocManager.getBestProvider(crit, false);
		Location location = m_MyLocManager.getLastKnownLocation(m_MyTowersStr);
		
		if(location != null)
		{
			m_LatInt = (int) (location.getLatitude() * 1E6);
			m_LongiInt = (int) (location.getLongitude() * 1E6);
			
			GeoPoint getUsersLocation = new GeoPoint(m_LatInt, m_LongiInt);
			m_MyMapController.animateTo(getUsersLocation);
			m_MyMapController.setZoom(20);
			OverlayItem overlayItem = new OverlayItem(getUsersLocation, "What's up!", "2nd String");
			CustomPinpoint custom = new CustomPinpoint(m_MyDrawable, Exercise2.this);
			custom.insertPinpoint(overlayItem);
			m_MyOverlayList.add(custom);
		}
		else
		{
			Toast.makeText(Exercise2.this, m_MessageStr,
					Toast.LENGTH_SHORT).show();
		}
		
	}


	// For drawing the users movement while exercising...
	public class RouteOverlay extends Overlay 
	{

		private GeoPoint myGeoPointOne;
		private GeoPoint myGeoPointTwo;
		private int myMode = 1;

		//Constructor for inner class.
		public RouteOverlay(GeoPoint contructorGeoPointOne, GeoPoint contructorGeoPointTwo,int contructorMode) 
		{
		this.myGeoPointOne = contructorGeoPointOne;
		this.myGeoPointTwo = contructorGeoPointTwo;
		this.myMode = contructorMode;
		}
		
		//Draw the line to overlay on map. Now to set up how I want the draw line to look.
		public void draw(Canvas myCanvas, MapView myMapView,
		 boolean paramShadow) 
		 {

		  super.draw(myCanvas, myMapView, paramShadow);

		   Projection projection = myMapView.getProjection();
		   Paint myHandyPaintWork = new Paint();
		   myHandyPaintWork.setDither(true);
		   
		   myHandyPaintWork.setAntiAlias(true);
		   myHandyPaintWork.setColor(Color.RED);
		   myHandyPaintWork.setStyle(Paint.Style.FILL_AND_STROKE);
		   myHandyPaintWork.setStrokeJoin(Paint.Join.ROUND);
		   myHandyPaintWork.setStrokeCap(Paint.Cap.ROUND);
		   myHandyPaintWork.setStrokeWidth(3);
		   myHandyPaintWork.setAlpha(120);

		   Point myPoint1 = new Point();
		   Point myPoint2 = new Point();
		   Path userPath = new Path();

		   projection.toPixels(myGeoPointOne, myPoint1);
		   projection.toPixels(myGeoPointTwo, myPoint2);

		   userPath.moveTo(myPoint2.x,myPoint2.y);
		   userPath.lineTo(myPoint1.x,myPoint1.y);

		   myCanvas.drawPath(userPath, myHandyPaintWork);
		 }
	}
	
	
	// Called by my onLocation changed method.
	 private GeoPoint getGeoPointsByLocation(Location myLocation)
	 {
		  GeoPoint myGeoPoints = null;

		  try 
		  {
		     if (myLocation != null) 
		  {
			  double myGeoPointLatitude = myLocation.getLatitude() * 1E6;
			  double myGeoPointLongitude = myLocation.getLongitude() * 1E6;
			  myGeoPoints = new GeoPoint((int) myGeoPointLatitude, (int) myGeoPointLongitude);
		   }

		   } 
		  	catch (Exception e) 
		   {
		    e.printStackTrace(); 
		   }
		    return myGeoPoints;
	}
	 
	// Called by my onLocation changed method.
	 private void drawRoute(List<Location> myLocationArrayList, MapView myMapView)        
	 {
		   List<Overlay> myOverlaysList = myMapView.getOverlays();
	       myOverlaysList.clear();
	       
	       for (int i = 1; i < myLocationArrayList.size(); i++) 
	       {
	    	   myOverlaysList.add(new RouteOverlay(getGeoPointsByLocation(myLocationArrayList.get(i - 1)),     getGeoPointsByLocation(myLocationArrayList.get(i)),2));
	       }
	 }
	
	 
	 @Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
		
		// Inner class for the different layers - placing things on the map.
		public class Touchy extends Overlay
		{
			
			
			
		}

		
		public void onLocationChanged(Location myLocation) 
		{
			m_MyCoordinatesList.add(myLocation);
			m_MapView.getController().animateTo(getGeoPointsByLocation (myLocation));
			drawRoute(m_MyCoordinatesList, m_MapView);
		}

		@Override
		protected void onPause() 
		{
			super.onPause();
			m_MyLocManager.removeUpdates(this);
		}


		@Override
		protected void onResume() 
		{
			super.onResume();
			m_MyLocManager.requestLocationUpdates(m_MyTowersStr, 500, 1, this);
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onClick(View v) 
		{
			
			switch (v.getId()) 
			{
				case R.id.btnStartMapRoute:
					m_MyCoordinatesList = new ArrayList<Location>();
					
					if(m_StartTime==0L)
					{
						m_StartTime = SystemClock.uptimeMillis();
						m_Handler.removeCallbacks(TimeRunner);
						m_Handler.postDelayed(TimeRunner, 100);
					}
					
				break;
				
				case R.id.btnStopMapRoute:
					
					m_Handler.removeCallbacks(TimeRunner);
					m_TimerLabel.setText(m_TimerStop1Str);
					m_StartTime = 0L;
					
				break;
			}
			
		}
		
		// The thread for running the timer and distance in sync with Android threads.
		private Runnable TimeRunner = new Runnable()
		{

			@Override
			public void run() 
			{
				final long start = m_StartTime;
				long millis = SystemClock.uptimeMillis() - start;
				
				int seconds = (int) (millis / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;
//				minutes/0.04, seconds*0.3
				int kilometres = minutes/4;
				int metres =  (int) (minutes/0.04);
				metres = metres % 100;
				
				m_DistTravelled.setText("" + kilometres + ":" + String.format("%02d", metres));
				m_TimerLabel.setText("" + minutes + ":" + String.format("%02d", seconds));
				m_TimerStop1Str =  minutes + ":" + String.format("%02d", seconds); 
				m_Handler.postDelayed(this, 200);
				
			}
		};	
	 
}

