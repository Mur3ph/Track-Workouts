package com.murph.athlasapp1;

import java.net.URL;
import java.util.List;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.murph.overlays.MyItemizedOverlay;
import com.murph.overlays.RouteOverlay;

public class Exercise3 extends MapActivity{

	// This class allows the user to choose a few options;
	// a) Local cafe b) local wheel chair access areas
	// c) A planned route set out d) button to weather section.
	
	 private static double m_LatiDouble;
     private static double m_LoniDouble;
     private int m_LatiInt, m_LoniInt;
     private MapController m_MyMapController;
     private GeoPoint m_MyGeoPoint;
     private MapView m_MyMapView;
     
     private Button m_BtnForCafeIcon, m_BtnForAccessIcon, m_BtnWeather, m_BtnRoute;
     
     private List<Overlay> m_MyMapOverlaysList;
     private Drawable m_Drawable1, m_Drawable2;
     private MyItemizedOverlay m_MyItemizedOverlay1, m_MyItemizedOverlay2;
     private boolean m_IsFoodIconDisplayed = false;
     
     String m_MyTAG = "GPStest";
     int m_MyNumberRoutePointsInt;	
     GeoPoint m_MyRoutePointsList [];   
     int m_MyRouteGradeList [];              
     RouteOverlay m_MyRouteOverlay;   
     boolean m_IsMyRouteDisplayed = false;
     
     // Array used for containing the food overlay icons coordinates.
     private OverlayItem [] m_MyFoodItemsAndCoordinates = {
             new OverlayItem( new GeoPoint(53655300,-6416405), "Food Title 1", "Food snippet 1"), 
             new OverlayItem( new GeoPoint(53657970,-6410840), "Food Title 2", "Food snippet 2"),
             new OverlayItem( new GeoPoint(53657300,-6412250), "Food Title 3", "Food snippet 3") 
         };
     
     // Array used for containing the access overlay icons coordinates.
     private OverlayItem [] m_MyAccessItemsAndCoordinates = {
         new OverlayItem( new GeoPoint(53654700,-6420620), "Access Title 1", "Access snippet 1"),
         new OverlayItem( new GeoPoint(53654890,-6419570), "Access Title 2", "Access snippet 2"),
         new OverlayItem( new GeoPoint(53654370,-6418990), "Access Title 3", "Access snippet 3")
     };
	
    private List<Overlay> m_MyMapOverlaysList2;
 	private Projection m_MyProjection;
     
	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar for more space on the screen!
		setContentView(R.layout.exercise3_map);
		m_MyMapView = (MapView) findViewById(R.id.mapViewMain2);
		m_MyMapView.setSatellite(false);
        m_MyMapView.setTraffic(false);
        m_MyMapView.setBuiltInZoomControls(true);   
        
        int maxZoom = m_MyMapView.getMaxZoomLevel();
        int initZoom = maxZoom-2;

        m_MyMapController = m_MyMapView.getController();
        m_MyMapController.setZoom(initZoom);
        m_LatiInt =  (int) (m_LatiDouble*1e6);
        m_LoniInt = (int) (m_LoniDouble*1e6);
        m_MyGeoPoint = new GeoPoint(m_LatiInt, m_LoniInt);
        m_MyMapController.animateTo(m_MyGeoPoint);    
        
        // Buttons cafe icons..................................
        m_BtnForCafeIcon = (Button)findViewById(R.id.doOverlay);
        m_BtnForCafeIcon.setOnClickListener(new OnClickListener()
        {      
            public void onClick(View v) 
            {	
            	setMyOverlayDisplayingTheFoodIcon();
            }
        });
        
        // Button for access icons overlay.
        m_BtnForAccessIcon = (Button)findViewById(R.id.doAccess);
        m_BtnForAccessIcon.setOnClickListener(new OnClickListener()
        {      
            public void onClick(View vi)
            {	
            	setMyOverlayDisplayingTheAccessIcon();
            }
        });
        
     // Button for route overlay
        m_BtnRoute = (Button)findViewById(R.id.doRoute);
        m_BtnRoute.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) 
            {	
            	m_MyMapOverlaysList2 = m_MyMapView.getOverlays();
        		m_MyProjection = m_MyMapView.getProjection();
        		m_MyMapOverlaysList2.add(new MyOverlay()); 				
            }
        });
        
        // Button to get the weather.................................
        m_BtnWeather = (Button) findViewById(R.id.btnWeatherForecat);
        m_BtnWeather.setOnClickListener(new OnClickListener(){
        	 public void onClick(View v) 
             {	
        		Intent i = new Intent(getBaseContext(), WeatherForecast.class);
 				startActivity(i); 				
             }
         });
	} // The end of onCreate method...
    
    public void setMyOverlayDisplayingTheFoodIcon()
    {	
        int foodLength = m_MyFoodItemsAndCoordinates.length;
        
        // Create itemizedOverlay2 if it doesn't exist and display all three items
        if(! m_IsFoodIconDisplayed)
        {
        m_MyMapOverlaysList = m_MyMapView.getOverlays();	
        m_Drawable1 = this.getResources().getDrawable(R.drawable.marker); 
        m_MyItemizedOverlay1 = new MyItemizedOverlay(m_Drawable1); 
        
        // Display all three items at once
        for(int i=0; i<foodLength; i++)
        {
            m_MyItemizedOverlay1.addOverlay(m_MyFoodItemsAndCoordinates[i]);
        }
       
	        m_MyMapOverlaysList.add(m_MyItemizedOverlay1);
	        m_IsFoodIconDisplayed = !m_IsFoodIconDisplayed;
        // Remove each item successively with button clicks
        } 
        else 
        {			
            m_MyItemizedOverlay1.removeItem(m_MyItemizedOverlay1.size()-1);
            if((m_MyItemizedOverlay1.size() < 1))  m_IsFoodIconDisplayed = false;
        }    
        m_MyMapView.postInvalidate(); 
    }
    
    
    public void setMyOverlayDisplayingTheAccessIcon()
    {	
        int accessLength = m_MyAccessItemsAndCoordinates.length;
        
        // If it doesn't already exist, create one!
        if(m_MyItemizedOverlay2 == null )
        {
	        m_MyMapOverlaysList = m_MyMapView.getOverlays();	
	        m_Drawable2 = this.getResources().getDrawable(R.drawable.marker2);
	        m_MyItemizedOverlay2 = new MyItemizedOverlay(m_Drawable2);
        }     
        
        // Add icons with by each user click!
        if(m_MyItemizedOverlay2.size() < accessLength)
        {
                m_MyItemizedOverlay2.addOverlay(m_MyAccessItemsAndCoordinates[m_MyItemizedOverlay2.size()]); 	
                m_MyMapOverlaysList.add(m_MyItemizedOverlay2);      
        // Remove all items with by clicking once.
        } 
        else 
        {
            for(int i=0; i<accessLength; i++)
            {
                m_MyItemizedOverlay2.removeItem(accessLength-1-i);
            }
        }     
        m_MyMapView.postInvalidate();
    }
	
    public static void putLatLong(double latitude, double longitude)
    {
        m_LatiDouble = latitude;
        m_LoniDouble =longitude;
    }
    
    
    public boolean onKeyDown(int keyCode, KeyEvent e)
    {
        if(keyCode == KeyEvent.KEYCODE_S)
        {
            m_MyMapView.setSatellite(!m_MyMapView.isSatellite());
            return true;
        } 
        else if(keyCode == KeyEvent.KEYCODE_T)
        {
            m_MyMapView.setTraffic(!m_MyMapView.isTraffic());
            m_MyMapController.animateTo(m_MyGeoPoint);  // To ensure change displays start away!
        }
            return(super.onKeyDown(keyCode, e));
    }
                        
    @Override
    protected boolean isRouteDisplayed() 
    {
            return false;  // Don't display a route
    }
    
    
    public void overlayRoute() 
    {
    	if(m_MyRouteOverlay != null) 
    		return;  
    	
    	m_MyRouteOverlay = new RouteOverlay(m_MyRoutePointsList, m_MyRouteGradeList); // My class defining route overlay
    	m_MyMapOverlaysList = m_MyMapView.getOverlays();
    	m_MyMapOverlaysList.add(m_MyRouteOverlay);
    	
        m_MyMapView.postInvalidate(); 
    }
	
    private class RouteLoader extends AsyncTask<URL, String, String> 
    {

        protected void onCancelled() 
        {
            Log.i("RouteLoader", "GetRoute task Cancelled");
        }

        protected void onPostExecute(String result) 
        {
                Log.i(m_MyTAG, "Route data transfer is complete!");
                overlayRoute();
        }

        protected void onPreExecute() 
        {
            Log.i(m_MyTAG,"Ready to load the URL");
        }

        protected void onProgressUpdate(String... values) 
        {
            super.onProgressUpdate(values);
        }

		@Override
		protected String doInBackground(URL... arg0) 
		{
			// TODO Auto-generated method stub
			return null;
		}

    }// End of inner class asych.....
    
    
    class MyOverlay extends Overlay
	{
		
		public MyOverlay()
		{
			
		}
		
		// TODO A lot of work went into this, should be put in array list.
		// Planned route for user. Shouldn't be developed like this!
		public void draw(Canvas canvas, MapView mapv, boolean shadow)
		{
			super.draw(canvas, mapv, shadow);
			
			Paint paint = new Paint();
			paint.setDither(true);
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeWidth(4);
			
			GeoPoint gp1 = new GeoPoint(53654970, -6414530);
			GeoPoint gp2 = new GeoPoint(53654950, -6413510);
			
			GeoPoint gp2B = new GeoPoint(53654950, -6413510);
			GeoPoint gp3B = new GeoPoint(53654520, -6413550);
			
			GeoPoint gp3 = new GeoPoint(53654520, -6413550);
			GeoPoint gp4 = new GeoPoint(53654430, -6413750);
			
			GeoPoint gp4B = new GeoPoint(53654430, -6413750);
			GeoPoint gp5B = new GeoPoint(53654440, -6414350);
			
			GeoPoint gp5 = new GeoPoint(53654440, -6414350);
			GeoPoint gp6 = new GeoPoint(53654550, -6414510);
			
			GeoPoint gp1B = new GeoPoint(53654970, -6414530);
			GeoPoint gp6B = new GeoPoint(53654550, -6414510);
			
			Point p1 = new Point();
			Point p2 = new Point();
			Path path1 = new Path();
			
			m_MyProjection.toPixels(gp1, p1);
			m_MyProjection.toPixels(gp2, p2);
			
			Point p3 = new Point();
			Point p4 = new Point();
			Path path2 = new Path();
			
			m_MyProjection.toPixels(gp3, p3);
			m_MyProjection.toPixels(gp4, p4);
			
			Point p5 = new Point();
			Point p6 = new Point();
			Path path3 = new Path();
			
			m_MyProjection.toPixels(gp5, p5);
			m_MyProjection.toPixels(gp6, p6);
			
			//Extras.......................
			Point p7 = new Point();
			Point p8 = new Point();
			Path path4 = new Path();
			
			m_MyProjection.toPixels(gp2B, p7);
			m_MyProjection.toPixels(gp3B, p8);
			
			Point p9 = new Point();
			Point p10 = new Point();
			Path path5 = new Path();
			
			m_MyProjection.toPixels(gp4B, p9);
			m_MyProjection.toPixels(gp5B, p10);
			
			Point p11 = new Point();
			Point p12 = new Point();
			Path path6 = new Path();
			
			m_MyProjection.toPixels(gp1B, p11);
			m_MyProjection.toPixels(gp6B, p12);
			
			path1.moveTo(p2.x, p2.y);
			path1.lineTo(p1.x, p1.y);
			
			path2.moveTo(p4.x, p4.y);
			path2.lineTo(p3.x, p3.y);
			
			path3.moveTo(p6.x, p6.y);
			path3.lineTo(p5.x, p5.y);
			
			//Extrsa.......................
			path4.moveTo(p8.x, p8.y);
			path4.lineTo(p7.x, p7.y);
			
			path5.moveTo(p10.x, p10.y);
			path5.lineTo(p9.x, p9.y);
			
			path6.moveTo(p12.x, p12.y);
			path6.lineTo(p11.x, p11.y);
			
			canvas.drawPath(path1, paint);
			canvas.drawPath(path2, paint);
			canvas.drawPath(path3, paint);
			
			canvas.drawPath(path4, paint);
			canvas.drawPath(path5, paint);
			canvas.drawPath(path6, paint);
			
		}
		
	}
    

} // End of class

