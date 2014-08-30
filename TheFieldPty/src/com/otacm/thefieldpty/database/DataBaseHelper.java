package com.otacm.thefieldpty.database;

import com.otacm.thefieldpty.utils.Reporter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "internal.db";
	private static final int DATABASE_VERSION = 3;
	public static final String TABLE_FAVORITOS_NAME = "favoritos";
	public static final String COLUMN_TABLE_FAVORITOS_ID = "id";
	public static final String COLUMN_TABLE_FAVORITOS_NOMBRE = "nombre";
	public static final String COLUMN_TABLE_FAVORITOS_CATEGORIA = "categoria";
	private Reporter log = Reporter.getInstance();
	
	private static final String CREATE_FAVORITOS = "create table " 
			+ TABLE_FAVORITOS_NAME
			+ "("
			+ COLUMN_TABLE_FAVORITOS_ID + " integer primary key autoincrement, "
			+ COLUMN_TABLE_FAVORITOS_NOMBRE + " text not null unique, "
			+ COLUMN_TABLE_FAVORITOS_CATEGORIA + " text not null "
			+ ")";
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(CREATE_FAVORITOS);
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITOS_NAME);
			onCreate(db); 
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
		}
	}	
}
