package com.otacm.thefieldpty.database.daos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.otacm.thefieldpty.database.DataBaseHelper;
import com.otacm.thefieldpty.database.DataSource;
import com.otacm.thefieldpty.database.beans.Remainder;
import com.otacm.thefieldpty.utils.Reporter;

public class RemaindersDAO extends DataSource{
	private Reporter log = Reporter.getInstance();
	private String [] allColumns = {
			DataBaseHelper.COLUMN_TABLE_REMAINDERS_ID,
			DataBaseHelper.COLUMN_TABLE_REMAINDERS_TITLE,
			DataBaseHelper.COLUMN_TABLE_REMAINDERS_DESC,
			DataBaseHelper.COLUMN_TABLE_REMAINDERS_DATE,
			DataBaseHelper.COLUMN_TABLE_REMAINDERS_STATUS,
			DataBaseHelper.COLUMN_TABLE_REMAINDERS_FAVID
	};  
	
	public RemaindersDAO(Context ctx) {
		super(ctx);
	}
	
	public int insertRemainder(Remainder r) {
		try {
			open();
			ContentValues v = new ContentValues();
			v.put(DataBaseHelper.COLUMN_TABLE_REMAINDERS_TITLE, r.getTitle());
			v.put(DataBaseHelper.COLUMN_TABLE_REMAINDERS_DESC, r.getDescription());
			v.put(DataBaseHelper.COLUMN_TABLE_REMAINDERS_DATE, r.getDate());
			v.put(DataBaseHelper.COLUMN_TABLE_REMAINDERS_STATUS, r.getStatus());
			v.put(DataBaseHelper.COLUMN_TABLE_REMAINDERS_FAVID, r.getFavid());
			getDatabase().insert(DataBaseHelper.TABLE_REMAINDERS_NAME, null, v);
			close();
			System.out.println("Remainder guardado");
			return 1;
		}catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
			close();
			return -1;
		}
	}
	
	public int deleteRemainder(int id) {
		try {
			open();
			getDatabase().delete(DataBaseHelper.TABLE_REMAINDERS_NAME, DataBaseHelper.COLUMN_TABLE_REMAINDERS_ID + " = " + id, null);
			close();
			return 1;
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
			return -1;
		}
	}
	
	private Remainder cursorToRemainder(Cursor c) {
		Remainder r = new Remainder();
		r.setId(c.getInt(0));
		r.setTitle(c.getString(1));
		r.setDescription(c.getString(2));
		r.setDate(c.getString(3)); 
		r.setStatus(c.getInt(4));
		r.setFavid(c.getInt(5)); 
		return r;
	}
	
	public List<Remainder> getAll() {
		try {
			open();
			List<Remainder> l = new ArrayList<Remainder>();
			Cursor c = getDatabase().query(DataBaseHelper.TABLE_REMAINDERS_NAME, allColumns, null, null, null, null, DataBaseHelper.COLUMN_TABLE_REMAINDERS_ID + " desc");
			c.moveToFirst();
			
			while(!c.isAfterLast()) {
				Remainder r = cursorToRemainder(c);
				System.out.println("Remainder : " + r.getTitle());
				l.add(r);
				c.moveToNext();
			}
			c.close();
			close();
			return l;
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
			close();
			return null;
		}
	}
	
	public void deleteRemaindersByFavId(int favid) {
		open();
		getDatabase().delete(DataBaseHelper.TABLE_REMAINDERS_NAME, DataBaseHelper.COLUMN_TABLE_REMAINDERS_FAVID + " = " + favid, null);
		close();
	}
	
	/**
	 * Lista los remainders que estan activos, es decir que el usuario
	 * no ha desactivado estos remainders y deben seguir apareciendo
	 * @return lista de remainders activos (status = 0)
	 */
	public List<Remainder> getActiveRemainders() {
		try {
			open();
			List<Remainder> l = new ArrayList<Remainder>();
			Cursor c = getDatabase().query(DataBaseHelper.TABLE_REMAINDERS_NAME, allColumns, " status = ?", 
					new String[] { String.valueOf(0) }, null, null, DataBaseHelper.COLUMN_TABLE_REMAINDERS_ID + " desc");
			c.moveToFirst();
			
			while(!c.isAfterLast()) {
				Remainder r = cursorToRemainder(c);
				System.out.println("Remainder : " + r.getTitle());
				l.add(r);
				c.moveToNext();
			}
			c.close();
			close();
			return l;
		}catch(Exception e) {
			log.write(Reporter.stringStackTrace(e));
			return null;
		}
	}
}
