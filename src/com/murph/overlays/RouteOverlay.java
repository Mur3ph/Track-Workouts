package com.murph.overlays;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class RouteOverlay extends Overlay
{
	private GeoPoint m_LocGeoPoint;
    private Paint m_Painter;
    private GeoPoint m_RouteArrayPoints [];
    private int m_RouteArrayGrade[];
    private boolean m_IsTheRouteActive;	
    private Point m_OldPoint, m_NewPoint, m_JustPoint;
    private int m_NumOfRoutePoints;
    
    // Constructor permitting the route array to be passed as an argument.
    public RouteOverlay(GeoPoint[] paraOfRoutePoints, int[] paraOfRouteGrade) 
    {
            m_RouteArrayPoints = paraOfRoutePoints;
            m_RouteArrayGrade = paraOfRouteGrade;
            m_NumOfRoutePoints  = paraOfRoutePoints.length;
            m_IsTheRouteActive = true;
            // If first time, set initial location to start of route
            m_LocGeoPoint = paraOfRoutePoints[0];
            m_OldPoint = new Point(0, 0);
            m_NewPoint = new Point(0,0);
            m_JustPoint = new Point(0,0);
            m_Painter = new Paint();
    }
    
    // Method to turn route display on and off
    public void setRouteView(boolean routeIsActive)
    {
            this.m_IsTheRouteActive = routeIsActive;
    }

    @Override
    public void draw(Canvas canvas, MapView mapview, boolean shadow) 
    {
        super.draw(canvas, mapview, shadow);
        if(! m_IsTheRouteActive) return;
    
        // Converts GeoPoint to screen pixels
        mapview.getProjection().toPixels(m_LocGeoPoint, m_JustPoint);       
        
        int xoff = 0;
        int yoff = 0;
        int oldx = m_JustPoint.x;
        int oldy = m_JustPoint.y;
        int newx = oldx + xoff;
        int newy = oldy + yoff;
        
        m_Painter.setAntiAlias(true);

        // Draw route segment by segment, setting color and width of segment according to the slope
        // information returned from the server for the route.
        
        for(int i=0; i<m_NumOfRoutePoints-1; i++){
            switch(m_RouteArrayGrade[i]){
                case 1:
                        m_Painter.setARGB(100,255,0,0);
                        m_Painter.setStrokeWidth(3);
                        break;
                case 2:
                        m_Painter.setARGB(100, 0, 255, 0);
                        m_Painter.setStrokeWidth(5);
                        break;
                case 3:
                        m_Painter.setARGB(100, 0, 0, 255);
                        m_Painter.setStrokeWidth(7);
                        break;
                case 4:
                        m_Painter.setARGB(90, 153, 102, 153);
                        m_Painter.setStrokeWidth(6);
                        break;
            }
            
            // Find end points of this segment in pixels
            mapview.getProjection().toPixels(m_RouteArrayPoints[i], m_OldPoint);
            oldx = m_OldPoint.x;
            oldy = m_OldPoint.y;
            mapview.getProjection().toPixels(m_RouteArrayPoints[i+1], m_NewPoint);
            newx = m_NewPoint.x;
            newy = m_NewPoint.y;
            
            canvas.drawLine(oldx, oldy, newx, newy, m_Painter);
        }      
    }
	
}
