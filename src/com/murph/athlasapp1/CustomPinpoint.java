package com.murph.athlasapp1;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomPinpoint extends ItemizedOverlay<OverlayItem> 
{
	// This class used to place/draw points on the google map overlay.
	
	private ArrayList<OverlayItem> m_Pinpoints = new ArrayList<OverlayItem>();
	private Context m_Context;
	
	public CustomPinpoint(Drawable arg0) 
	{
		super(boundCenter(arg0));
	}
	
	//Constructor.
	public CustomPinpoint(Drawable m, Context context) 
	{
		this(m);
		m_Context = context;
	}

	@Override
	protected OverlayItem createItem(int i) 
	{
		return m_Pinpoints.get(i);
	}

	@Override
	public int size() 
	{
		return m_Pinpoints.size();
	}

	public void insertPinpoint(OverlayItem item)
	{
		m_Pinpoints.add(item);
		this.populate();
	}
	
}
