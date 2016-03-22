package com.murph.db;

import java.util.ArrayList;
import java.util.List;
import com.murph.objects.Message;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseMessageController
{
		// Database fields........................
		private SQLiteDatabase database;
		private DatabaseHandler dbHelper;
		
		public static String[] allColumns = { DatabaseHandler.COLUMN_MESSAGEID,
				DatabaseHandler.COLUMN_SENDER, DatabaseHandler.COLUMN_RECEIVER, 
				DatabaseHandler.COLUMN_MESSAGE};
		
		public DatabaseMessageController(Context context) 
		{
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
		
		// Used to put the details of messages sent to other users
		public Message createMessage(String sender, String receiver, String message) 
		{

			ContentValues values = new ContentValues();

			values.put(DatabaseHandler.COLUMN_SENDER, sender);
			values.put(DatabaseHandler.COLUMN_RECEIVER, receiver);
			values.put(DatabaseHandler.COLUMN_MESSAGE, message);
			  
//			ContentResolver content = null;
//			content.insert(CommentsDataProvider.COMMENTS_URI, values);
			
			long insertId = database.insert(DatabaseHandler.TABLE_MESSAGES, null,
					values);
			
			Cursor cursor = database.query(DatabaseHandler.TABLE_MESSAGES, allColumns,
					DatabaseHandler.COLUMN_MESSAGEID + " = " + insertId, null, null, null,
					null);

			cursor.moveToFirst();
			Message newMessage = cursorToMessage(cursor);
			cursor.close();

			return newMessage;
		}
		
		// Used to retrieve users
		// details..................................................................
		public List<Message> getAllMessages() 
		{

			List<Message> messages = new ArrayList<Message>();
			
			Cursor cursor = database.query(DatabaseHandler.TABLE_MESSAGES, allColumns,
					null, null, null, null, null);

			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				Message m = cursorToMessage(cursor);
				messages.add(m);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();

			return messages;

		}
		
		public List<Message> findMessages(String receiver) 
		{
			List<Message> messages = new ArrayList<Message>();
			
			String[] columns = new String[] { receiver };
			Message m = null;
			Cursor c = null;

			database = dbHelper.getReadableDatabase();

			final String SELECT_MESSAGE_SQL_STATEMENT = "SELECT * FROM messages WHERE "
					+ "receiver=?";

			c = database.rawQuery(SELECT_MESSAGE_SQL_STATEMENT, columns);
			
			c.moveToFirst();
				
			while (!c.isAfterLast()) {
				m = cursorToMessage(c);
				messages.add(m);
				c.moveToNext();
			}
		
			if (database != null) 
			{
				database.close();
			}
			return messages;
		}
		
		public Message findMessage(String receiver) 
		{

			String[] columns = new String[] { receiver };
			Message m = null;
			Cursor c = null;

			database = dbHelper.getReadableDatabase();

			final String SELECT_MESSAGE_SQL_STATEMENT = "SELECT * FROM messages WHERE "
					+ "receiver=?";

			c = database.rawQuery(SELECT_MESSAGE_SQL_STATEMENT, columns);
			
			if (c != null && c.getCount() > 0) 
			{
				c.moveToFirst();
				m = new Message(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
				
				
			}

			if (database != null) 
			{
				database.close();
			}
			return m;
		}
		
		// Used to permantly delete a users message details from a
		// database.......................................
		public void deleteMessage(Message message) {
			int id = message.getMessageId();
			System.out.println("Message deleted with id: " + id);
			database.delete(DatabaseHandler.TABLE_MESSAGES, DatabaseHandler.COLUMN_MESSAGEID
					+ " = " + id, null);
		}
		
		private Message cursorToMessage(Cursor cursor) 
		{
			Message message = new Message();

			message.setMessageId(cursor.getInt(0));
			message.setSender(cursor.getString(1));
			message.setReceiver(cursor.getString(2));
			message.setMessage(cursor.getString(3));
			
			return message;
		}

}
