package com.murph.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper 
{
	//My core database class were all the tables are created.
	
	//Database name, table name and database version
		private static final String DATABASE_NAME = "athletetes.db";
		public static final String TABLE_USER = "users";
		public static final String TABLE_MESSAGES = "messages";
		public static final String TABLE_ROUTES = "routes";
		private static final int DATABASE_VERSION = 11;
	  
	    //Field names for user
		public static final String COLUMN_ID = "_id";  
		public static final String COLUMN_FULLNAME = "fname";  
		public static final String COLUMN_USERNAME = "uname";  
		public static final String COLUMN_PASSWORD = "pword";
		public static final String COLUMN_ABOUT = "about";  
		public static final String COLUMN_GENDER = "gender"; 
		public static final String COLUMN_AGE = "age";  
		public static final String COLUMN_CITY = "city"; 
		public static final String COLUMN_COUNTRY = "country"; 
		
		// Field names for messages
		public static final String COLUMN_MESSAGEID = "_messageId";  
		public static final String COLUMN_SENDER = "sender";  
		public static final String COLUMN_RECEIVER = "receiver";  
		public static final String COLUMN_MESSAGE = "message";
		
		// Field names for messages
		public static final String COLUMN_ROUTEID = "_routeId";  
		public static final String COLUMN_ATHLETE = "athlete";  
		public static final String COLUMN_TIME = "time";  
		public static final String COLUMN_DISTANCE = "distance";
		
	  // Database creation sql statement for user table
	  private static final String DATABASE_CREATE1 = "create table " 
	          + TABLE_USER + "(" 
		      + COLUMN_ID         + " integer primary key autoincrement, " 
			  + COLUMN_FULLNAME   + " text not null, "
			  + COLUMN_USERNAME   + " text not null, "
			  + COLUMN_PASSWORD   + " text not null, "
			  + COLUMN_ABOUT   	  + " text not null, "
			  + COLUMN_AGE        + " text not null, "
			  + COLUMN_GENDER     + " text not null, "
			  + COLUMN_CITY       + " text not null, "
			  + COLUMN_COUNTRY    + " text)";
	  
	  private static final String DATABASE_CREATE2 = "create table " 
	          + TABLE_MESSAGES + "(" 
		      + COLUMN_MESSAGEID         + " integer primary key autoincrement, " 
			  + COLUMN_SENDER   		 + " text not null, "
			  + COLUMN_RECEIVER    		 + " text not null, "
			  + COLUMN_MESSAGE   		 + " text)";
	  
	  private static final String DATABASE_CREATE3 = "create table " 
	          + TABLE_ROUTES + "(" 
		      + COLUMN_ROUTEID           + " integer primary key autoincrement, " 
			  + COLUMN_ATHLETE   		 + " text not null, "
			  + COLUMN_TIME    		 	 + " text not null, "
			  + COLUMN_DISTANCE   		 + " text)";

	  public DatabaseHandler(Context context) 
	  {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) 
	  {
	    database.execSQL(DATABASE_CREATE1);
	    database.execSQL(DATABASE_CREATE2);
	    database.execSQL(DATABASE_CREATE3);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	  {
	    Log.w(DatabaseHandler.class.getName(),"Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
	    onCreate(db);
	  }

	
	
}
