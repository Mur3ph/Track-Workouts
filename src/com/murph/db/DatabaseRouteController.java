package com.murph.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.murph.objects.Route66;

public class DatabaseRouteController
{

	// Database fields........................
			private SQLiteDatabase database;
			private DatabaseHandler dbHelper;
			
			public static String[] allColumns = { DatabaseHandler.COLUMN_ROUTEID,
					DatabaseHandler.COLUMN_ATHLETE, DatabaseHandler.COLUMN_TIME, 
					DatabaseHandler.COLUMN_DISTANCE};
			
			public DatabaseRouteController(Context context) 
			{
				dbHelper = new DatabaseHandler(context);
			}
			
			// Open database..........................
			public void open() throws SQLException 
			{
				database = dbHelper.getWritableDatabase();
			}

			// Close database.........................
			public void close() 
			{
				dbHelper.close();
			}
			
			
			// Used to put the details of ROUTE 
			public Route66 insertRoute(String athlete, String time, String distance) 
			{

				ContentValues values = new ContentValues();

				values.put(DatabaseHandler.COLUMN_ATHLETE, athlete);
				values.put(DatabaseHandler.COLUMN_TIME, time);
				values.put(DatabaseHandler.COLUMN_DISTANCE, distance);
				
				long insertId = database.insert(DatabaseHandler.TABLE_ROUTES, null,
						values);
				
				Cursor cursor = database.query(DatabaseHandler.TABLE_ROUTES, allColumns,
						DatabaseHandler.COLUMN_ROUTEID + " = " + insertId, null, null, null,
						null);

				cursor.moveToFirst();
				Route66 newroute = cursorToRoute(cursor);
				cursor.close();

				return newroute;
			}
			
			
			public List<Route66> getAllRoutes() 
			{

				List<Route66> routes = new ArrayList<Route66>();
				
				Cursor cursor = database.query(DatabaseHandler.TABLE_ROUTES, allColumns,
						null, null, null, null, null);

				cursor.moveToFirst();

				while (!cursor.isAfterLast()) {
					Route66 r = cursorToRoute(cursor);
					routes.add(r);
					cursor.moveToNext();
				}
				// Make sure to close the cursor
				cursor.close();

				return routes;

			}
			
			
			private Route66 cursorToRoute(Cursor cursor) 
			{
				Route66 route = new Route66();

				route.setRouteId(cursor.getInt(0));
				route.setName(cursor.getString(1));
				route.setTime(cursor.getString(2));
				route.setDistance(cursor.getString(3));
				
				return route;
			}
	
}
