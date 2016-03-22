package com.murph.content_providers;

import com.murph.db.DatabaseHandler;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AthletesNameProvider extends ContentProvider
{

	private static final String AUTHORIZATION = "com.murph.content_providers.AthletesNameProvider";
	public static final Uri COMMENTS_URI = Uri.parse("content://" + AUTHORIZATION + "/" + DatabaseHandler.TABLE_USER);
	
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
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}