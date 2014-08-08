package com.otacm.thefieldpty.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase database;
	private DataBaseHelper helper;

	public DataSource(Context ctx) {
		helper = new DataBaseHelper(ctx);
	}

	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public DataBaseHelper getHelper() {
		return helper;
	}

	public void setHelper(DataBaseHelper helper) {
		this.helper = helper;
	}

}
