package nosafespot.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "tododdb";
	
	public static final String TABLE_LISTS = "lists";
	public static final String COL_0_LISTS_ID = "id";
	public static final String COL_1_LISTS_NAME = "name";
	
	public static final String TABLE_ENTRYS = "entrys";
	public static final String COL_0_ENTRYS_ID = "id";
	public static final String COL_1_ENTRYS_LIST_ID = "listid";
	public static final String COL_2_ENTRYS_NAME = "name";
	public static final String COL_3_ENTRYS_CHECKED = "checked";
	
	private static final String DB_CREATE_LISTS = "create table " +
			TABLE_LISTS + "(" + 
			COL_0_LISTS_ID	+ " integer primary key autoincrement, " + 
			COL_1_LISTS_NAME	+ " text);";
	
	private static final String DB_CREATE_ENTRYS = "create table " +
			TABLE_ENTRYS + "(" + 
			COL_0_ENTRYS_ID	+ " integer primary key autoincrement, " + 
			COL_1_ENTRYS_LIST_ID	+ " integer, " +
			COL_2_ENTRYS_NAME	+ " text, " +
			COL_3_ENTRYS_CHECKED + " integer);";
	
	
	public SQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE_LISTS);
		db.execSQL(DB_CREATE_ENTRYS);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRYS);
		onCreate(db);
	}
	

	

}
