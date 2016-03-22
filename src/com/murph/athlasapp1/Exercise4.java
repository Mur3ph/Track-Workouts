package com.murph.athlasapp1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Exercise4 extends MapActivity implements LocationListener
{

	//This is a pretty basic map view with street views, location information etc.
	
	MapView m_MapView;
	long m_StartLong;
	long m_StopLong;
	MyLocationOverlay m_MyCompassOverlay;
	MapController m_MyMapController;
	int m_XInt, m_YInt;
	GeoPoint m_MyTouchGeoPoint = null;
	Drawable m_MyDrawable;
	List<Overlay> m_MyOverlayList;
	LocationManager m_LocManager;
	String m_TowersStr;
	int m_LatiInt = 0;
	int m_LongiInt = 0;
	String m_MyMessage = "Sorry, couldn't get a provider!";
	
	
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar for more space
		setContentView(R.layout.exercise2_map);
	    m_MapView = (MapView) findViewById(R.id.mapViewMain);
		m_MapView.setSatellite(false);
        m_MapView.setTraffic(false);
        m_MapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
        
//		mapOverlays = map.getOverlays();
//		projection = map.getProjection();
//		mapOverlays.add(new MyOverlay());
        
        MyTocuhMapScreenClass t = new MyTocuhMapScreenClass();
		m_MyOverlayList = m_MapView.getOverlays();
		m_MyOverlayList.add(t);
		
		m_MyCompassOverlay = new MyLocationOverlay(Exercise4.this, m_MapView);
		m_MyOverlayList.add(m_MyCompassOverlay);
		
		m_MyMapController = m_MapView.getController();
		
		m_MyDrawable = getResources().getDrawable(R.drawable.marker2);
		
		// Getting the users location
		m_LocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria crit = new Criteria();
		
		m_TowersStr = m_LocManager.getBestProvider(crit, false);
		Location location = m_LocManager.getLastKnownLocation(m_TowersStr);
		
		if(location != null)
		{
			m_LatiInt = (int) (location.getLatitude() * 1E6);
			m_LongiInt = (int) (location.getLongitude() * 1E6);
			
			GeoPoint getUsersLocation = new GeoPoint(m_LatiInt, m_LongiInt);
			m_MyMapController.animateTo(getUsersLocation);
			m_MyMapController.setZoom(20);
			OverlayItem overlayItem = new OverlayItem(getUsersLocation, "What's up!", "2nd String");
			CustomPinpoint custom = new CustomPinpoint(m_MyDrawable, Exercise4.this);
			custom.insertPinpoint(overlayItem);
			m_MyOverlayList.add(custom);
		}
		else
		{
			Toast.makeText(Exercise4.this, m_MyMessage,
					Toast.LENGTH_SHORT).show();
		}
        
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		m_MyCompassOverlay.disableCompass();
		m_LocManager.removeUpdates(this);
	}


	@Override
	protected void onResume() 
	{
		super.onResume();
		m_MyCompassOverlay.enableCompass();
		m_LocManager.requestLocationUpdates(m_TowersStr, 500, 1, this);
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	
	// Inner class for the different layers - placing things on the map.
		public class MyTocuhMapScreenClass extends Overlay
		{
			
			@SuppressWarnings("deprecation")
			public boolean onTouchEvent(MotionEvent e, MapView m)
			{
				if(e.getAction() == MotionEvent.ACTION_DOWN)
				{
					m_StartLong = e.getEventTime();
					m_XInt = (int) e.getX();
					m_YInt = (int) e.getY();
				    m_MyTouchGeoPoint = m_MapView.getProjection().fromPixels(m_XInt, m_YInt);
				}
				if(e.getAction() == MotionEvent.ACTION_UP)
				{
					m_StopLong = e.getEventTime();
				}
				if(m_StopLong-m_StartLong > 1500)
				{
					AlertDialog alert = new AlertDialog.Builder(Exercise4.this).create();
					alert.setTitle("Pick an option!");
					alert.setMessage("Please pick an option from below!");
					alert.setButton("Place a pin ", new DialogInterface.OnClickListener() 
					{
						
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							OverlayItem overlayItem = new OverlayItem(m_MyTouchGeoPoint, "What's up!", "2nd String");
							CustomPinpoint custom = new CustomPinpoint(m_MyDrawable, Exercise4.this);
							custom.insertPinpoint(overlayItem);
							m_MyOverlayList.add(custom);
							
						}
					});
					alert.setButton2("Get address ", new DialogInterface.OnClickListener() 
					{
						
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
							try
							{
								List<Address> address = geocoder.getFromLocation(m_MyTouchGeoPoint.getLatitudeE6()/1E6, m_MyTouchGeoPoint.getLongitudeE6()/1E6, 1);
								if(address.size()>0 )
								{
									String display = "";
									for(int i = 0; i<address.get(0).getMaxAddressLineIndex(); i++)
									{
										display += address.get(0).getAddressLine(i) + "\n";
									}
									Toast.makeText(Exercise4.this, display,
											Toast.LENGTH_LONG).show();
								}
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
							finally
							{
								
							}
						}
					});
					alert.setButton3("Toggle View", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							if(m_MapView.isSatellite())
							{
								m_MapView.setSatellite(false);
								m_MapView.setStreetView(true);
							}
							else
							{
								m_MapView.setStreetView(false);
								m_MapView.setSatellite(true);
							}
						}
					});
					alert.show();
					return true;
				}
				
				return false;
			}
			
		}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
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
	
}
