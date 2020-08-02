package my.pack.qualification;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{
	public static int version = 1;

	public static final String DB_NAME = "tester.db";

	public DatabaseHelper(Context c) {
		super(c, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE users(" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT," +
				"username TEXT," +
				"email TEXT," +
				"password TEXT" +
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS users");
		db.execSQL("CREATE TABLE users(" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT," +
				"username TEXT," +
				"email TEXT," +
				"password TEXT" +
				")");
	}
}
