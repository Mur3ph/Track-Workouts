package com.murph.content_providers;

import com.murph.db.DatabaseHandler;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CommentsDataProvider extends ContentProvider
{
	private static final String AUTHORIZATION = "com.murph.content_providers.CommentsDataProvider";
	public static final Uri COMMENTS_URI = Uri.parse("content://" + AUTHORIZATION + "/" + DatabaseHandler.TABLE_MESSAGES);
	
	final static int COMMENTS = 1;
	
	SQLiteDatabase db;
	DatabaseHandler dbHelper;
	
	private final static UriMatcher uriMatcher;
	
	static
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORIZATION, DatabaseHandler.TABLE_MESSAGES, COMMENTS);
	}
	
	@Override
	public boolean onCreate()
	{
		dbHelper = new DatabaseHandler(getContext());
		return true;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2)
	{
		return 0;
	}

	@Override
	public String getType(Uri arg0) 
	{
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
		db = dbHelper.getWritableDatabase();
		
		if(uriMatcher.match(uri)==COMMENTS)
		{
			db.insert(DatabaseHandler.TABLE_MESSAGES, null, values);
		}
		
		db.close();
		getContext().getContentResolver().notifyChange(uri, null);
		
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) 
	{	
		Cursor c;
		
		db = dbHelper.getReadableDatabase();
		c = db.query(DatabaseHandler.TABLE_MESSAGES, projection, selection, selectionArgs, null, null, sortOrder);
		 
		c.setNotificationUri(getContext().getContentResolver(), uri);
		
		return c;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) 
	{
		return 0;
	}

}
