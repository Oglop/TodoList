package nosafespot.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	
	private String [] allListColumns = { 
			SQLiteHelper.COL_0_LISTS_ID, 
			SQLiteHelper.COL_1_LISTS_NAME};
	
	private String [] allEntryColumns = { 
			SQLiteHelper.COL_0_ENTRYS_ID, 
			SQLiteHelper.COL_1_ENTRYS_LIST_ID,
			SQLiteHelper.COL_2_ENTRYS_NAME,
			SQLiteHelper.COL_3_ENTRYS_CHECKED};
	
	public DataSource(Context context){
		dbHelper = new SQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
	    dbHelper.close();
	}
	
	/**
	 * insert a new list item to the lists table
	 * @param name
	 */
	public void insertList(String name){
		ContentValues values = new ContentValues();
	    values.put(SQLiteHelper.COL_1_LISTS_NAME, name);
	    database.insertOrThrow(SQLiteHelper.TABLE_LISTS, null, values);
	}
	
	/**
	 * insert a new entry to the entry table
	 * @param name
	 * @param listId
	 */
	public void insertEntry(String name, int listId){
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COL_1_ENTRYS_LIST_ID, listId);
		values.put(SQLiteHelper.COL_2_ENTRYS_NAME, name);
		values.put(SQLiteHelper.COL_3_ENTRYS_CHECKED, "0");
		database.insertOrThrow(SQLiteHelper.TABLE_ENTRYS, null, values);
	}
	
}
