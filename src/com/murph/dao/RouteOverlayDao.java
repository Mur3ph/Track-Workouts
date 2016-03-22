package com.murph.dao;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RouteOverlayDao extends DefaultHandler
{
	
	//Class not being used, delete!
	
	private boolean m_IsGeometryInCollection = false;
	private boolean m_IsCoordinatesInAlready = false;
	private String m_MyPathCoordinatesStr;
	
	public String getPathCoordinates() 
	{ 
		return m_MyPathCoordinatesStr; 
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		if(m_IsGeometryInCollection && m_IsCoordinatesInAlready)
			m_MyPathCoordinatesStr = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException 
	{
		if(localName.equals("GeometryCollection")) m_IsGeometryInCollection = false;
		else if (localName.equals("coordinates")) m_IsCoordinatesInAlready = false;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException 
	{
		if(localName.equals("Geometry Collection")) m_IsGeometryInCollection = true;
		else if (localName.equals("coordinates")) m_IsCoordinatesInAlready = true;
	}
	
}
