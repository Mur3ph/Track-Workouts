package com.murph.overlays;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>
{
	private ArrayList<OverlayItem> m_MyOverlays ;
	
	 public MyItemizedOverlay(Drawable defaultMarker) 
	 {
         super(boundCenterBottom(defaultMarker));
		 m_MyOverlays = new ArrayList<OverlayItem>();
         populate();
	}

	 public void addOverlay(OverlayItem overlay)
	 {
         m_MyOverlays.add(overlay);
         populate();
     }
 
     @Override
     protected OverlayItem createItem(int i) 
     {
         return m_MyOverlays.get(i);
     }
         
     // Removes overlay item i
     public void removeItem(int i)
     {
         m_MyOverlays.remove(i);
         populate();
     }
         
     // Handle tap events on overlay icons
     @Override
     protected boolean onTap(int i)
     {
         return(true);
     }
 
     // Returns present number of items in list
     @Override
     public int size() 
     {
         return m_MyOverlays.size();
     }

}
