package com.murph.athlasapp1;

import java.util.List;
import com.murph.db.DatabaseRouteController;
import com.murph.objects.Route66;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListAllRoutes extends Activity implements OnClickListener
{

	// At the moment its just displaying work outs for one athlete.
	//TODO Set up so user can delete work outs and show other users work outs.
	
	private DatabaseRouteController m_MyDatabaseController;
	Route66 r = null;
	ArrayAdapter<Route66> m_MyArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listroutes);

		ListView list = (ListView) findViewById(R.id.listRoutes);
		
		m_MyDatabaseController = new DatabaseRouteController(this);
		
		m_MyDatabaseController.open();

		List<Route66> routes = m_MyDatabaseController.getAllRoutes();
		
		m_MyArrayAdapter = new ArrayAdapter<Route66>(this, android.R.layout.simple_list_item_1, routes);

		list.setAdapter(m_MyArrayAdapter);
		
		m_MyArrayAdapter.notifyDataSetChanged();
		
	}
	
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
	}

}
