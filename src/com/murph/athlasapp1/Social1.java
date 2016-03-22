package com.murph.athlasapp1;

import java.util.List;
import com.murph.content_providers.CommentsDataProvider;
import com.murph.db.DatabaseHandler;
import com.murph.db.DatabaseMessageController;
import com.murph.objects.Message;
import com.murph.objects.User;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager;

//LoaderManager.LoaderCallbacks<Cursor>

public class Social1 extends Activity implements OnClickListener, LoaderManager.LoaderCallbacks<Cursor>
{
	//Class for: Loading the data from messages db into content provider.
	
	private static final int m_LOADER = 0x01;
	DatabaseMessageController m_MydbController;
	
	View m_View;
	SQLiteDatabase m_SQLiteDb;
	DatabaseHandler m_MyDbHelperHandler;
	ListView m_ListV1;
	SimpleCursorAdapter m_SimpleCursorAdapter;
	
	
	ArrayAdapter<Message> m_ArrayAdpter;
	ListView m_ListV2;
	
	EditText m_MessageTo;
	EditText m_Message;
	
	Button m_Send;
	Button m_Display;
	
	String m_MessageFrom;
	
	Message m;
	User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social1_home);
		m_MyDbHelperHandler = new DatabaseHandler(this);
		m_SQLiteDb = m_MyDbHelperHandler.getWritableDatabase();
		m_ListV2 = (ListView) findViewById(R.id.listOfComments);
		
		String[] from = {DatabaseHandler.COLUMN_SENDER, DatabaseHandler.COLUMN_RECEIVER, DatabaseHandler.COLUMN_MESSAGE};
		
		int[] to = {R.id.edtMessagerName, R.id.edtTxtSendMessage};
		
		m_SimpleCursorAdapter = new SimpleCursorAdapter(this.getApplicationContext(), R.layout.social1_home, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		m_ListV2.setAdapter(m_SimpleCursorAdapter);
		
//		getLoaderManager().restartLoader(LOADER, null, null);
		
		m_MessageTo = (EditText) findViewById(R.id.edtMessagerName);
		m_MessageTo.setOnClickListener(this);
		m_Message = (EditText) findViewById(R.id.edtTxtSendMessage);
		
		m_Send = (Button) findViewById(R.id.btnSendMessage);
		m_Send.setOnClickListener(this);
		m_Display = (Button) findViewById(R.id.btnDisplayMessages);
		m_Display.setOnClickListener(this);
		
		Intent i = getIntent();
		m_MessageFrom = i.getExtras().getString("username");

		m_MydbController = new DatabaseMessageController(this);
		m_MydbController.open(); 
		
	}


	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.btnSendMessage:
		
		String nameTo, message;
		
		nameTo = m_MessageTo.getText().toString();
		message = m_Message.getText().toString();
		
		ContentValues values = new ContentValues();
		

		values.put(DatabaseHandler.COLUMN_SENDER, m_MessageFrom);
		values.put(DatabaseHandler.COLUMN_RECEIVER, nameTo);
		values.put(DatabaseHandler.COLUMN_MESSAGE, message);
		
		// While inserting into local db, load data to all applications db.
		ContentResolver content = this.getContentResolver();
		content.insert(CommentsDataProvider.COMMENTS_URI, values);
		
		Toast.makeText(Social1.this, "Message was sent to " + nameTo, Toast.LENGTH_LONG).show();
		
		m_MessageTo.setText("");
		m_Message.setText("");
		
		break;
		case R.id.btnDisplayMessages:
			
			m_MydbController.open();
			List<Message> messages = m_MydbController.getAllMessages();
			
			m_ArrayAdpter = new ArrayAdapter<Message>(this,
					android.R.layout.simple_list_item_1, messages);
			
			m_ListV2.setAdapter(m_ArrayAdpter);
			m_ArrayAdpter.notifyDataSetChanged();
			m_MydbController.close();
			
		break;	
		case R.id.edtMessagerName:
			Intent i = new Intent(getBaseContext(), ListAllAthletes.class);
			startActivity(i);
		break;	
		}
		
	}


	

	// For the cursor loader interface.
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) 
	{
		String[] columns = {DatabaseHandler.COLUMN_MESSAGEID, DatabaseHandler.COLUMN_SENDER, DatabaseHandler.COLUMN_RECEIVER, DatabaseHandler.COLUMN_MESSAGE};
		
		CursorLoader cursorLoader = new CursorLoader(this, CommentsDataProvider.COMMENTS_URI, columns, null, null, null);
		return cursorLoader;
	}


	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) 
	{
		m_SimpleCursorAdapter.swapCursor(arg1);
	}


	@Override
	public void onLoaderReset(Loader<Cursor> arg0) 
	{
		m_SimpleCursorAdapter.swapCursor(null);
	}
	
}
