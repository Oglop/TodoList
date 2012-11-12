package nosafespot.todo;

import java.util.ArrayList;
import java.util.List;

import nosafespot.todo.containers.Entry;
import nosafespot.todo.containers.TodoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	 * Returns all the todo lists
	 * @return List<TodoList>
	 */
	public List<TodoList> getLists(){
		List<TodoList> todoList = new ArrayList<TodoList>();
		Cursor cursor = database.query(SQLiteHelper.TABLE_LISTS,
				allListColumns, null, null,
		        null, null, null);
	    cursor.moveToFirst();
	    while (cursor.isAfterLast() == false) 
	    {
	    	int id = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_0_LISTS_ID));
	    	String name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_1_LISTS_NAME));
	    	TodoList item = new TodoList(id, name);
	    	todoList.add(item);
	        cursor.moveToNext();
	    }
		return todoList;
	}
	/**
	 * Returns the name of a specific list
	 * @param id list id
	 * @return name of list
	 */
	public String getListName(int id){
		String name;
		Cursor cursor = database.query(SQLiteHelper.TABLE_LISTS,
				allListColumns, SQLiteHelper.COL_0_LISTS_ID + "=?", new String [] { String.valueOf(id) },
		        null, null, null);
		cursor.moveToFirst();
		name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_1_LISTS_NAME));
		return name;
	}
	/**
	 * Deletes a list and all of the entrys associated with that list
	 * @param id list id
	 */
	public void deleteList(int id){
		database.delete(SQLiteHelper.TABLE_LISTS, SQLiteHelper.COL_0_LISTS_ID+"=?", new String [] {String.valueOf(id)});
		database.delete(SQLiteHelper.TABLE_ENTRYS, SQLiteHelper.COL_1_ENTRYS_LIST_ID+"=?", new String [] {String.valueOf(id)});
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
	/**
	 * Return all entrys asociated with a list
	 * @param listid id of the list
	 * @return List<Entry>
	 */
	public List<Entry> getEntrys(int listid){
		List<Entry> entrys = new ArrayList<Entry>();
		Cursor cursor = database.query(SQLiteHelper.TABLE_ENTRYS,
				allEntryColumns, SQLiteHelper.COL_1_ENTRYS_LIST_ID + "=?", new String [] { String.valueOf(listid) },
		        null, null, null);
	    cursor.moveToFirst();
	    while (cursor.isAfterLast() == false) 
	    {
	    	int id = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_0_ENTRYS_ID));
	    	int listId = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_1_ENTRYS_LIST_ID));
	    	String name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_2_ENTRYS_NAME));
	    	int checked = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_3_ENTRYS_CHECKED));
	    	Entry e = new Entry(id, listId, name, checked);
	    	entrys.add(e);
	        cursor.moveToNext();
	    }
		return entrys;
	}
	
	
	/**
	 * Set checked value of an entry
	 * @param checked
	 */
	public int updateEntryChecked(boolean checked, int id){
		int i = (checked) ? 1 : 0;
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COL_3_ENTRYS_CHECKED, i);
		return database.update(SQLiteHelper.TABLE_ENTRYS, values, 
				SQLiteHelper.COL_1_ENTRYS_LIST_ID + "=?", 
				new String []{String.valueOf(id)});
	}
	
}
