package com.murph.athlasapp1;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListAllAthletes extends Activity implements OnClickListener
{
	// Class .is grabing the contacts from the phones contacts
	
	//TODO Set up a content provider interface to abstract contacts from phone!
	//TODO Abstract contacts from application database.
	
	public static final String TAG = "Athletic App Debugging";
	ListView m_ListView;
    ArrayAdapter<String> m_MyArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listathletes);
		this.m_ListView = (ListView) findViewById(R.id.listAthletes);
		this.m_MyArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, 0);
		this.m_ListView.setAdapter(m_MyArrayAdapter);

		Uri myContactsUri = Contacts.CONTENT_URI;
		
		String[] MyProjectionArray = {"lookup", "display_name", "has_phone_number"};  // What columns I want to grab.
		String mySelectionStr = "has_phone_number = ?";		// Change this to group = ?;
		String[] MySelectionArray = {"1"};					// and this to athletic.
		String mySortOrderStr = null;
		
		ContentResolver myContentResolver = getContentResolver();
		Cursor myCursor = myContentResolver.query(myContactsUri, MyProjectionArray, mySelectionStr, MySelectionArray, mySortOrderStr);
		
		Log.i(TAG, Integer.toString(myCursor.getColumnCount()));
		
		if(myCursor.getCount() > 0)
		{
			while(myCursor.moveToNext())
			{
				String name = myCursor.getString(myCursor.getColumnIndex("display_name"));
				this.m_MyArrayAdapter.add(name);
				this.m_MyArrayAdapter.notifyDataSetChanged();
			}
		}
	}
	
	@Override
	public void onClick(View arg0) 
	{
		
	}

}


// Code for when I set up the content provider for my own users in the application

// private DatabaseController m_MyDatabaseController;
// private User u = null;
// private ArrayAdapter<User> m_MyArrayAdapter;

// Inside onCreate()

//ListView list = (ListView) findViewById(R.id.listAthletes);
//
//m_MyDatabaseController = new DatabaseController(this);
//
//m_MyDatabaseController.open();
//
//List<User> users = m_MyDatabaseController.getAllUsers();
//
//m_MyArrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
//
//list.setAdapter(m_MyArrayAdapter);
//
//m_MyArrayAdapter.notifyDataSetChanged();

// Next is for getting the phone contacts using SQL statements:

//myCursor.moveToFirst(); // Tell the cursor to move to the first column.
//for(int x = 0; x < myCursor.getColumnCount(); x++)
//{
//	String y = myCursor.getColumnName(x);
//	if(y == null)
//	{
//		y = "null";
//	}
//	Log.i(TAG, Integer.toString(x) + " " + y);
//	Log.i(TAG, myCursor.getString(x));
//}