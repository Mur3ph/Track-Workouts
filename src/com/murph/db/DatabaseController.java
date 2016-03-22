package com.murph.db;

import java.util.ArrayList;
import java.util.List;
import com.murph.objects.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseController {

	// Database fields........................
	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;
	
//	private String[] partColumns = { DatabaseHandler.COLUMN_ID,
//			DatabaseHandler.COLUMN_FULLNAME, DatabaseHandler.COLUMN_USERNAME, DatabaseHandler.COLUMN_PASSWORD};
	
	private String[] allColumns = { DatabaseHandler.COLUMN_ID,
			DatabaseHandler.COLUMN_FULLNAME, DatabaseHandler.COLUMN_USERNAME, DatabaseHandler.COLUMN_PASSWORD, 
			DatabaseHandler.COLUMN_ABOUT, DatabaseHandler.COLUMN_GENDER, DatabaseHandler.COLUMN_AGE, 
			DatabaseHandler.COLUMN_CITY, DatabaseHandler.COLUMN_COUNTRY };
	
	private String[] someColumns = { DatabaseHandler.COLUMN_ID,
			DatabaseHandler.COLUMN_USERNAME};
	
	final String LOGIN_SQL_STATEMENT = "SELECT * FROM users WHERE DatabaseHandler.COLUMN_USERNAME=? AND DatabaseHandler.COLUMN_PASSWORD=?";

	// Constructor............................
	public DatabaseController(Context context) {
		dbHelper = new DatabaseHandler(context);
	}

	// Open database..........................
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	// Close database.........................
	public void close() {
		dbHelper.close();
	}

	// Used to register a new user.................................
	public User createUser(String fullname, String username, String password) {

		ContentValues values = new ContentValues();

		values.put(DatabaseHandler.COLUMN_FULLNAME, fullname);
		values.put(DatabaseHandler.COLUMN_USERNAME, username);
		values.put(DatabaseHandler.COLUMN_PASSWORD, password);
		values.put(DatabaseHandler.COLUMN_ABOUT, "ooo");
		values.put(DatabaseHandler.COLUMN_AGE, "ooo");
		values.put(DatabaseHandler.COLUMN_GENDER, "ooo");
		values.put(DatabaseHandler.COLUMN_CITY, "ooo");
		values.put(DatabaseHandler.COLUMN_COUNTRY, "ooo");
		  
		long insertId = database.insert(DatabaseHandler.TABLE_USER, null,
				values);

		Cursor cursor = database.query(DatabaseHandler.TABLE_USER, allColumns,
				DatabaseHandler.COLUMN_ID + " = " + insertId, null, null, null,
				null);

		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();

		return newUser;
	}

	// Used to change the users
	// details.................................................................
	public void updateUser(String name, String about, String gender, String age, String city, String country, String uname, int id, String pass) {

		ContentValues values = new ContentValues();

		values.put(DatabaseHandler.COLUMN_FULLNAME, name);
		values.put(DatabaseHandler.COLUMN_USERNAME, uname);
		values.put(DatabaseHandler.COLUMN_PASSWORD, pass);
		values.put(DatabaseHandler.COLUMN_ABOUT, about);
		values.put(DatabaseHandler.COLUMN_AGE, age);
		values.put(DatabaseHandler.COLUMN_GENDER, gender);
		values.put(DatabaseHandler.COLUMN_CITY, city);
		values.put(DatabaseHandler.COLUMN_COUNTRY, country);
		
		//long id = 1;
//		String sqlUpdate = "update users set fname= " + name + " and about= " + about + " and gender= " + gender + " and age= " + age + " and city= " + city + " and country= " + country + " where uname= " + uname +"'";

		database.update(DatabaseHandler.TABLE_USER, values, DatabaseHandler.COLUMN_ID + "=" + id, null);
//		database.execSQL(sqlUpdate);
		
	}

	// Used to permantly delete a users details from a
	// database.......................................
	public void deleteUser(User user) {
		int id = user.getUserId();
		System.out.println("User deleted with id: " + id);
		database.delete(DatabaseHandler.TABLE_USER, DatabaseHandler.COLUMN_ID
				+ " = " + id, null);
	}

	// Used to retrieve users
	// details..................................................................
	public List<User> getAllUsers() {

		List<User> users = new ArrayList<User>();
				
		Cursor cursor = database.query(DatabaseHandler.TABLE_USER, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			User u = cursorToUser(cursor);
			users.add(u);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();

		return users;

	}

	public User findUser(String uname, String pass) {

		String[] columns = new String[] { uname, pass };
		User u = null;
		Cursor c = null;

		database = dbHelper.getReadableDatabase();

		final String LOGIN_SQL_STATEMENT = "SELECT * FROM users WHERE "
				+ "uname=? AND pword=?";

		c = database.rawQuery(LOGIN_SQL_STATEMENT, columns);
		
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			u = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
					c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8));
			
			Log.v("WHAT THE HELL MAN, USER!", pass);
			Log.v("WHAT THE HELL MAN, DB USERNAME!", uname);
			Log.v("WHAT THE HELL MAN, DB CURSOR!", c.toString());
			Log.v("WHAT THE HELL MAN, DB USER!", u.toString());
		}

		if (database != null) {
			database.close();
		}

		//
		return u;
	}

	// Used to retrieve user details by
	// name...........................................................
	public User findUser1(String name) {
		String[] columns = new String[] { name };
		User u = null;
		Cursor c = null;

		database = dbHelper.getReadableDatabase();

		final String LOGIN_SQL_STATEMENT = "SELECT * FROM users WHERE "
				+ "uname=?";

		c = database.rawQuery(LOGIN_SQL_STATEMENT, columns);
		
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			u = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
			c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8));
		}

		if (database != null) {
			database.close();
		}

		//
		return u;
	}

	private User cursorToUser(Cursor cursor) {

		User user = new User();

		user.setUserId(cursor.getInt(0));
		user.setName(cursor.getString(1));
		user.setUsername(cursor.getString(2));
		user.setPassword(cursor.getString(3));
		user.setAbout(cursor.getString(4));
		user.setAge(cursor.getString(5));
		user.setSex(cursor.getString(6));
		user.setCounty(cursor.getString(7));
		user.setCountry(cursor.getString(8));

		return user;
	}

}
